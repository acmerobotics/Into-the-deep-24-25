package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.components.IntoTheDeepChassis;
import org.firstinspires.ftc.teamcode.subsystem.IntoTheDeepIntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.IntoTheDeepOutakeSubsystem;

@TeleOp
public class IntoTheDeepSmManualOp extends LinearOpMode {

    private IntoTheDeepChassis intoTheDeepChassis;
    private IntoTheDeepIntakeSubsystem intakeSubsystem;
    private IntoTheDeepOutakeSubsystem outtakeSubsystem;

    public void runOpMode(){

        intoTheDeepChassis = new IntoTheDeepChassis(hardwareMap, gamepad1, telemetry);
        intakeSubsystem = new IntoTheDeepIntakeSubsystem(hardwareMap, telemetry);
        outtakeSubsystem = new IntoTheDeepOutakeSubsystem(hardwareMap, intoTheDeepChassis, telemetry);

        IntoTheDeepIntakeSubsystem.IntakeSubsystemState intakeSubsystemState = IntoTheDeepIntakeSubsystem.IntakeSubsystemState.TRANSFER_POSITION;
        IntoTheDeepOutakeSubsystem.OuttakeSubsystemState outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.TRANSFER_POSITION;
        outtakeSubsystem.init();
        intakeSubsystem.init();
        waitForStart();

        while (opModeIsActive()) {

            intoTheDeepChassis.drive();

            switch (intakeSubsystemState) {
                case FULL_EXTENSION:
                    if (gamepad1.dpad_down) {
                        intakeSubsystem.pickUpSampleFromFarSubmersible();
                        intakeSubsystemState = IntoTheDeepIntakeSubsystem.IntakeSubsystemState.TRANSFER_POSITION;
                    }
                    activeIntakeControls();
                    break;
                case HALF_EXTENSION:
                    if (gamepad1.dpad_down) {
                        intakeSubsystem.pickupSampleFromNearSubmersible();
                        intakeSubsystemState = IntoTheDeepIntakeSubsystem.IntakeSubsystemState.TRANSFER_POSITION;
                    }
                    activeIntakeControls();
                    break;
                case TRANSFER_POSITION:
                    if (gamepad1.dpad_right) {
                        intakeSubsystem.findSampleFromFarSubmersible();
                        intakeSubsystemState = IntoTheDeepIntakeSubsystem.IntakeSubsystemState.FULL_EXTENSION;
                    }
                    if (gamepad1.dpad_left) {
                        intakeSubsystem.findSampleFromNearSubmersible();
                        intakeSubsystemState = IntoTheDeepIntakeSubsystem.IntakeSubsystemState.HALF_EXTENSION;
                    }
                    activeIntakeControls();
                    break;
            }

            switch (outtakeSubsystemState) {
                case TRANSFER_POSITION:
                    if (gamepad2.y) {
                        outtakeSubsystem.toTransfer();
                        intakeSubsystem.toTransfer();
                        outtakeSubsystem.positionHighBasket();
                        outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.POSITION_HIGHER_BASKET;
                    }
                    if (gamepad2.x) {
                        outtakeSubsystem.toTransfer();
                        intakeSubsystem.toTransfer();
                        outtakeSubsystem.positionLowBasket();
                        outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.POSITION_LOWER_BASKET;
                    }
                    if(gamepad2.a){
                        intakeSubsystem.findSampleFromFarSubmersible();
                        outtakeSubsystem.pickupSpecimen();
                        outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.SPECIMEN_PICKUP;
                    }
                    break;
                case POSITION_ABOVE_HIGHER_CHAMBER:
                    if (gamepad2.dpad_down) {
                        outtakeSubsystem.highChamberScore();
                        outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.POSITION_HIGHER_CHAMBER;
                    }
                    break;
                case POSITION_HIGHER_CHAMBER:
                    if(gamepad2.dpad_down){
                        outtakeSubsystem.highChamberToTransfer();
                        outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.TRANSFER_POSITION;
                    }
                    break;
                case POSITION_ABOVE_LOWER_CHAMBER:
                    if (gamepad2.dpad_down) {
                       outtakeSubsystem.lowChamberScore();
                        outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.POSITION_LOWER_CHAMBER;
                    }
                    break;
                case POSITION_LOWER_CHAMBER:
                    if(gamepad2.dpad_down){
                        outtakeSubsystem.lowChamberToTransfer();
                        outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.SPECIMEN_PICKUP;
                    }
                    break;
                case POSITION_HIGHER_BASKET:
                    if (gamepad2.dpad_down) {
                        outtakeSubsystem.highBasketToTransfer();
                        outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.TRANSFER_POSITION;
                    }
                    break;
                case POSITION_LOWER_BASKET:
                    if (gamepad2.dpad_down) {
                        outtakeSubsystem.lowBasketToTransfer();
                        outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.TRANSFER_POSITION;
                    }
                    break;
                case SPECIMEN_PICKUP:
                    if (gamepad2.dpad_up) {
                        outtakeSubsystem.toTransfer();
                        intakeSubsystem.toTransfer();
                        outtakeSubsystem.positionHighChamber();
                        outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.POSITION_ABOVE_HIGHER_CHAMBER;
                    }
                    if (gamepad2.dpad_left) {
                        outtakeSubsystem.toTransfer();
                        intakeSubsystem.toTransfer();
                        outtakeSubsystem.positionLowChamber();
                        outtakeSubsystemState = IntoTheDeepOutakeSubsystem.OuttakeSubsystemState.POSITION_ABOVE_LOWER_CHAMBER;
                    }
                    break;

            }
            telemetry.update();
        }

    }

    private void activeIntakeControls() {
        if(gamepad1.y){
            intakeSubsystem.findSample();
        }
        if(gamepad1.x){
            intakeSubsystem.pickupSample();
        }
        if(gamepad1.a){
            intakeSubsystem.ejectSample();
        }
    }
}
