package org.firstinspires.ftc.teamcode.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;

public class IntoTheDeepOuttakeClaw implements IntoTheDeepComponent {

    Servo claw;

    private OuttakeClawState outtakeClawState;

    @Override
    public void init() {
        this.openOuttakeClaw();
    }

    public enum OuttakeClawState {
        OUTTAKE_CLAW_CLOSED,
        OUTTAKE_CLAW_OPENED
    }

    public IntoTheDeepOuttakeClaw(Servo claw, Servo.Direction direction) {
        this.claw = claw;
        this.claw.setDirection(direction);
    }

    public Action openOuttakeClaw() {
        return new OpenOuttakeClawAction();
    }

    public class OpenOuttakeClawAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            claw.setPosition(IntoTheDeepConstants.OUTTAKE_CLAW_OPEN);
            outtakeClawState = OuttakeClawState.OUTTAKE_CLAW_OPENED;
            return false;
        }
    }

    public Action closeOuttakeClaw() {
        return new CloseOuttakeClawAction();
    }

    public class CloseOuttakeClawAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            claw.setPosition(IntoTheDeepConstants.OUTTAKE_CLAW_CLOSE);
            outtakeClawState = OuttakeClawState.OUTTAKE_CLAW_CLOSED;
            return false;
        }
    }
}
