package org.firstinspires.ftc.teamcode.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;

public class IntoTheDeepIntakeWrist implements IntoTheDeepComponent{

    Servo intakeWristLeft;
    Servo intakeWristRight;

    private IntakeWristState intakeWristState;

    public enum IntakeWristState {
        TRANSFER,
        INTAKE_PICKUP
    }

    public IntoTheDeepIntakeWrist(Servo wristLeft, Servo.Direction directionLeft, Servo wristRight, Servo.Direction directionRight) {
        this.intakeWristLeft = wristLeft;
        this.intakeWristLeft.setDirection(Servo.Direction.FORWARD);
        this.intakeWristRight = wristRight;
        this.intakeWristRight.setDirection(Servo.Direction.FORWARD);
    }

    @Override
    public void init() {

    }

    public Action toPickupAction() {
        return new ToPickupAction();
    }

    public class ToPickupAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeWristLeft.setPosition(IntoTheDeepConstants.INTAKE_WRIST_LEFT_PICKUP_POSITION);
            intakeWristRight.setPosition(IntoTheDeepConstants.INTAKE_WRIST_RIGHT_PICKUP_POSITION);
            intakeWristState = IntakeWristState.INTAKE_PICKUP;
            return false;
        }
    }

    public Action toIntakeTransferAction() {
        return new ToIntakeTransferAction();
    }

    public class ToIntakeTransferAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeWristLeft.setPosition(IntoTheDeepConstants.INTAKE_WRIST_LEFT_TRANSFER_POSITION);
            intakeWristRight.setPosition(IntoTheDeepConstants.INTAKE_WRIST_RIGHT_TRANSFER_POSITION);
            intakeWristState = IntakeWristState.TRANSFER;
            return false;
        }
    }
}