package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepChassis;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepComponent;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepOuttakeClaw;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepOuttakeSlide;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepOuttakeWrist;

public class IntoTheDeepOutakeSubsystem implements IntoTheDeepComponent {
    private IntoTheDeepOuttakeSlide outtakeSlide;
    private IntoTheDeepOuttakeWrist outtakeWrist;
    private IntoTheDeepOuttakeClaw outtakeClaw;
    private IntoTheDeepChassis intoTheDeepChassis;

    public enum OuttakeSubsystemState {
        POSITION_LOWER_BASKET,
        POSITION_HIGHER_BASKET,
        POSITION_ABOVE_HIGHER_CHAMBER,
        POSITION_ABOVE_LOWER_CHAMBER,
        POSITION_LOWER_CHAMBER,
        POSITION_HIGHER_CHAMBER,
        TRANSFER_POSITION,
        SPECIMEN_PICKUP,
    }

    public IntoTheDeepOutakeSubsystem(HardwareMap hardwareMap, IntoTheDeepChassis intoTheDeepChassis, Telemetry telemetry) {
        this.outtakeSlide = new IntoTheDeepOuttakeSlide(intoTheDeepChassis, hardwareMap.get(DcMotor.class, "outtakeSlideMotor"), DcMotor.Direction.REVERSE, telemetry);
        this.outtakeWrist = new IntoTheDeepOuttakeWrist(hardwareMap.get(Servo.class, "outtakeWristLeft"), Servo.Direction.REVERSE,
                hardwareMap.get(Servo.class, "outtakeWristRight"), Servo.Direction.FORWARD);
        this.outtakeClaw = new IntoTheDeepOuttakeClaw(hardwareMap.get(Servo.class, "outtakeClaw"), Servo.Direction.FORWARD);;
    }

    @Override
    public void init() {
        Actions.runBlocking(new ParallelAction(
                new SequentialAction(outtakeWrist.toTransfer(), new SleepAction(0.2)),
                new SequentialAction(outtakeClaw.openOuttakeClaw(), new SleepAction(0.2))
        ));
    }

    public void toTransfer(){
        Actions.runBlocking(
                new SequentialAction(outtakeClaw.closeOuttakeClaw(), new SleepAction(0.2))
        );
    }

    public void positionHighBasket(){
        Actions.runBlocking(new SequentialAction(
                outtakeSlide.scoreHighBasketAction(),
                outtakeWrist.toScoreBasketAction()
        ));
    }

    public void highBasketToTransfer(){
        Actions.runBlocking(new SequentialAction(
                new ParallelAction(outtakeClaw.openOuttakeClaw(), new SleepAction(0.2)),
                new ParallelAction(outtakeClaw.closeOuttakeClaw(), outtakeWrist.toTransfer(), new SleepAction(0.2)),
                outtakeSlide.retractFromHighBasketAction(),
                outtakeClaw.openOuttakeClaw()
        ));
    }

    public void positionLowBasket(){
        Actions.runBlocking(new SequentialAction(
                outtakeSlide.scoreLowBasketAction(),
                outtakeWrist.toScoreBasketAction())
        );
    }

    public void lowBasketToTransfer(){
        Actions.runBlocking(new SequentialAction(
                new ParallelAction(outtakeClaw.openOuttakeClaw(), new SleepAction(0.2)),
                new ParallelAction(outtakeClaw.closeOuttakeClaw(), outtakeWrist.toTransfer(), new SleepAction(0.2)),
                outtakeSlide.retractFromLowBasketAction(),
                outtakeClaw.openOuttakeClaw()
        ));
    }

    public void pickupSpecimen(){
        Actions.runBlocking(new SequentialAction(
                outtakeWrist.wristSpecimenPickupAction(), new SleepAction(0.2)
        ));
    }

    public void positionHighChamber(){
        Actions.runBlocking(new SequentialAction(
                outtakeClaw.closeOuttakeClaw(),
                new SleepAction(.2),
                outtakeSlide.travelAboveHighChamberAction()
        ));
    }

    public void highChamberScore(){
        Actions.runBlocking(new SequentialAction(
                outtakeSlide.scoreHighChamberAction(),
                new SleepAction(.2),
                outtakeClaw.openOuttakeClaw()
                ));
    }

    public void highChamberToTransfer(){
        Actions.runBlocking(new SequentialAction(
                outtakeClaw.openOuttakeClaw(),
                new SleepAction(0.2),
                new ParallelAction(outtakeClaw.closeOuttakeClaw(), outtakeWrist.toTransfer()),
                new SleepAction(0.2),
                outtakeSlide.retractFromHighChamberAction(),
                outtakeClaw.openOuttakeClaw()
        ));
    }

    public void positionLowChamber(){
        Actions.runBlocking(new SequentialAction(
                outtakeSlide.travelAboveLowChamberAction()
        ));

    }

    public void lowChamberScore(){
        Actions.runBlocking(new SequentialAction(
                outtakeSlide.scoreLowChamberAction()));
    }

    public void lowChamberToTransfer() {
        Actions.runBlocking(new SequentialAction(
                outtakeClaw.openOuttakeClaw(),
                new SleepAction(0.2),
                new ParallelAction(outtakeClaw.closeOuttakeClaw(), outtakeWrist.toTransfer()),
                new SleepAction(0.2),
                outtakeSlide.retractFromLowChamberAction(),
                outtakeClaw.openOuttakeClaw()
        ));
    }



    public void scoreHighRung(){

    }

    public void scoreLowRung(){

    }

}