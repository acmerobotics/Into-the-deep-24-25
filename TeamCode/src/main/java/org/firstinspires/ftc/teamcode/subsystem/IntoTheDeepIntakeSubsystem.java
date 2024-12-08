package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepActiveIntake;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepComponent;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepIntakeSlideV2;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepIntakeWrist;

public class IntoTheDeepIntakeSubsystem implements IntoTheDeepComponent {

    private IntoTheDeepIntakeSlideV2 intakeSlide;
    private IntoTheDeepIntakeWrist intakeWrist;
    private IntoTheDeepActiveIntake intakeClaw;

    public enum IntakeSubsystemState {
        FULL_EXTENSION,
        HALF_EXTENSION,
        TRANSFER_POSITION,
    }

    public IntoTheDeepIntakeSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {

        this.intakeSlide = new IntoTheDeepIntakeSlideV2(
                hardwareMap.get(Servo.class, "intakeSlideLeft"), Servo.Direction.REVERSE,
                hardwareMap.get(Servo.class, "intakeSlideRight"), Servo.Direction.FORWARD
        );
        this.intakeWrist = new IntoTheDeepIntakeWrist(
                hardwareMap.get(Servo.class, "intakeWristLeft"), Servo.Direction.FORWARD,
                hardwareMap.get(Servo.class, "intakeWristRight"), Servo.Direction.FORWARD);

        this.intakeClaw = new IntoTheDeepActiveIntake(
                hardwareMap.get(DcMotor.class, "activeIntake"), DcMotorSimple.Direction.FORWARD);
        ;
    }

    @Override
    public void init() {
        Actions.runBlocking(new ParallelAction(intakeWrist.toIntakeTransferAction(),
                intakeSlide.retractFarFromSubmersibleActionV2()));
    }

    public void toTransfer() {
        Actions.runBlocking(new ParallelAction(intakeWrist.toIntakeTransferAction(),
                intakeSlide.retractFarFromSubmersibleActionV2()));
    }

    public void findSampleFromFarSubmersible() {
        Actions.runBlocking(new SequentialAction(intakeSlide.extendFarFromSubmersibleActionV2(), new SleepAction(.2), intakeWrist.toPickupAction()));
    }

    public void findSample() {
        Actions.runBlocking(intakeClaw.intakeSampleAction());
    }

    public void pickupSample() {
        Actions.runBlocking(intakeClaw.stopIntakeAction());
    }

    public void ejectSample(){
        Actions.runBlocking(intakeClaw.ejectSampleAction());
    }

    public void pickUpSampleFromFarSubmersible() {
        Actions.runBlocking(new SequentialAction(intakeWrist.toIntakeTransferAction(), new SleepAction(.5), intakeSlide.retractFarFromSubmersibleActionV2()));
    }

    public void findSampleFromNearSubmersible() {
        Actions.runBlocking(new SequentialAction(intakeSlide.extendCloseToSubmersibleActionV2(), new SleepAction(.2), intakeWrist.toPickupAction()));
    }

    public void pickupSampleFromNearSubmersible() {
        Actions.runBlocking(new SequentialAction(intakeWrist.toIntakeTransferAction(), new SleepAction(.5), intakeSlide.retractCloseToSubmersibleActionV2()));
    }
}
