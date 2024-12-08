package org.firstinspires.ftc.teamcode.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntoTheDeepActiveIntake implements IntoTheDeepComponent{

    private DcMotor activeIntake;
    private DcMotorSimple.Direction activeIntakeDirection;
    private Telemetry telemetry;

    private ActiveIntakeState activeIntakeState;

    @Override
    public void init() {

    }

    public enum ActiveIntakeState {
        INTAKE_POSITION,
        EJECT_POSITION,
        STOP_POSITION
    }

    public IntoTheDeepActiveIntake(DcMotor activeIntake, DcMotorSimple.Direction direction) {
        this.activeIntake = activeIntake;
        activeIntakeDirection = direction;
    }

    public Action intakeSampleAction() {
        return new IntakeSampleAction();
    }

    public class IntakeSampleAction implements Action {
 @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            activeIntake.setPower(IntoTheDeepConstants.POWER);
            activeIntake.setDirection(activeIntakeDirection);
            activeIntakeState = ActiveIntakeState.INTAKE_POSITION;
            return false;
        }

    }

    public Action ejectSampleAction() {
        return new EjectSampleAction();
    }

    public class EjectSampleAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            activeIntake.setPower(IntoTheDeepConstants.POWER);
            activeIntake.setDirection(activeIntakeDirection.inverted());
            activeIntakeState = ActiveIntakeState.EJECT_POSITION;
            return false;
        }
    }

    public Action stopIntakeAction() {
        return new StopIntakeAction();
    }

    public class StopIntakeAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            activeIntake.setPower(IntoTheDeepConstants.POWER_ZERO);
            activeIntake.setDirection(activeIntakeDirection);
            activeIntakeState = ActiveIntakeState.STOP_POSITION;
            return false;
        }
    }
}