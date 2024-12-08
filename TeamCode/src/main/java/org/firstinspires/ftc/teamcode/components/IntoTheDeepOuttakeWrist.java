package org.firstinspires.ftc.teamcode.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;

public class IntoTheDeepOuttakeWrist implements IntoTheDeepComponent {

    Servo outtakeWristLeft;
    Servo outtakeWristRight;

    private OuttakeWristState outtakeWristState;

    @Override
    public void init() {

    }

    public enum OuttakeWristState {
        SCORE_SAMPLE,
        TRANSFER,
        SCORE_SPECIMEN,
        PICKUP_SPECIMEN
    }

    public IntoTheDeepOuttakeWrist(Servo outtakeWristLeft, Servo.Direction outtakeWristLeftDirection, Servo outtakeWristRight, Servo.Direction outtakeWristRightDirection) {
        this.outtakeWristLeft = outtakeWristLeft;
        this.outtakeWristLeft.setDirection(outtakeWristLeftDirection);
        this.outtakeWristRight = outtakeWristRight;
        this.outtakeWristRight.setDirection(outtakeWristRightDirection);
        outtakeWristState = OuttakeWristState.TRANSFER;
    }

    public Action toTransfer() {
        return new ToTransfer();
    }

    public class ToTransfer implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtakeWristLeft.setPosition(IntoTheDeepConstants.OUTTAKE_WRIST_TRANSFER_POSITION);
            outtakeWristRight.setPosition(IntoTheDeepConstants.OUTTAKE_WRIST_TRANSFER_POSITION);
            outtakeWristState = OuttakeWristState.TRANSFER;
            return false;
        }
    }

    public Action toScoreChamberAction() {
        return new ToScoreChamberAction();
    }

    public class ToScoreChamberAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtakeWristLeft.setPosition(IntoTheDeepConstants.OUTTAKE_WRIST_SPECIMEN_SCORE_POSITION);
            outtakeWristRight.setPosition(IntoTheDeepConstants.OUTTAKE_WRIST_SPECIMEN_SCORE_POSITION);
            outtakeWristState = OuttakeWristState.SCORE_SPECIMEN;
            return false;
        }
    }

    public Action toScoreBasketAction() {
        return new ToScoreBasketAction();
    }

    public class ToScoreBasketAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtakeWristLeft.setPosition(IntoTheDeepConstants.OUTTAKE_WRIST_SAMPLE_SCORE_POSITION);
            outtakeWristRight.setPosition(IntoTheDeepConstants.OUTTAKE_WRIST_SAMPLE_SCORE_POSITION);
            outtakeWristState = OuttakeWristState.SCORE_SAMPLE;
            return false;
        }
    }

    public Action wristSpecimenPickupAction() {
        return new WristSpecimenPickup();
    }

    public class WristSpecimenPickup implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtakeWristLeft.setPosition(IntoTheDeepConstants.OUTTAKE_WRIST_SPECIMEN_PICKUP_POSITION);
            outtakeWristRight.setPosition(IntoTheDeepConstants.OUTTAKE_WRIST_SPECIMEN_PICKUP_POSITION);
            outtakeWristState = OuttakeWristState.PICKUP_SPECIMEN;
            return false;
        }
    }
}