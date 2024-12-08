package org.firstinspires.ftc.teamcode.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntoTheDeepIntakeSlideV2 implements IntoTheDeepComponent {

    Servo intakeExtendoLeft;
    Servo intakeExtendoRight;
    private Telemetry telemetry;

    private IntakeSlideState intakeSlideState;

    public enum IntakeSlideState {
        EXTENDED_HALF,
        EXTENDED_FULL,
        NO_EXTENSION,
    }

    public IntoTheDeepIntakeSlideV2(Servo extendoLeft, Servo.Direction directionExtendoLeft, Servo extendoRight, Servo.Direction directionExtendoRight) {
        this.intakeExtendoLeft = extendoLeft;
        this.intakeExtendoLeft.setDirection(directionExtendoLeft);
        this.intakeExtendoRight = extendoRight;
        this.intakeExtendoRight.setDirection(directionExtendoRight);

        this.telemetry = telemetry;
        //ticks per inch =32.53 ~ 33
        this.intakeSlideState = IntakeSlideState.NO_EXTENSION;
    }

    @Override
    public void init() {

    }

    public Action extendFarFromSubmersibleActionV2() {
        return new ExtendFarFromSubmersibleActionV2();
    }

    public class ExtendFarFromSubmersibleActionV2 implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeExtendoLeft.setPosition(IntoTheDeepConstants.INTAKE_EXTENDO_FAR_EXTEND_POSITION);
            intakeExtendoRight.setPosition(IntoTheDeepConstants.INTAKE_EXTENDO_FAR_EXTEND_POSITION);
            intakeSlideState = IntakeSlideState.EXTENDED_FULL;
            return false;
        }
    }

    public Action retractFarFromSubmersibleActionV2() {
        return new RetractFarFromSubmersibleActionV2();
    }

    public class RetractFarFromSubmersibleActionV2 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeExtendoLeft.setPosition(IntoTheDeepConstants.INTAKE_EXTENDO_RETRACTED_POSITION);
            intakeExtendoRight.setPosition(IntoTheDeepConstants.INTAKE_EXTENDO_RETRACTED_POSITION);
            intakeSlideState = IntakeSlideState.NO_EXTENSION;
            return false;
        }

    }

    public Action extendCloseToSubmersibleActionV2() {
        return new ExtendCloseFromSubmersibleActionV2();
    }

    public class ExtendCloseFromSubmersibleActionV2 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeExtendoLeft.setPosition(IntoTheDeepConstants.INTAKE_EXTENDO_NEAR_EXTEND_POSITION);
            intakeExtendoRight.setPosition(IntoTheDeepConstants.INTAKE_EXTENDO_NEAR_EXTEND_POSITION);
            intakeSlideState = IntakeSlideState.EXTENDED_HALF;
            return false;
        }

    }

    public Action retractCloseToSubmersibleActionV2() {
        return new RetractCloseFromSubmersibleActionV2();
    }

    public class RetractCloseFromSubmersibleActionV2 implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeExtendoLeft.setPosition(IntoTheDeepConstants.INTAKE_EXTENDO_RETRACTED_POSITION);
            intakeExtendoRight.setPosition(IntoTheDeepConstants.INTAKE_EXTENDO_RETRACTED_POSITION);
            intakeSlideState = IntakeSlideState.NO_EXTENSION;
            return false;
        }
    }
}


