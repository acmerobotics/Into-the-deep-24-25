package org.firstinspires.ftc.teamcode.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.Servo;

public class IntoTheDeepIntakeClaw implements IntoTheDeepComponent{

    Servo claw;

    private IntakeClawState intakeClawState;

    @Override
    public void init() {
        this.closeIntake();
    }

    public enum IntakeClawState {
        INTAKE_CLAW_CLOSED,
        INTAKE_CLAW_OPENED,
        INTAKE_CLAW_TRANSFER_OPENED
    }

    public IntoTheDeepIntakeClaw(Servo claw, Servo.Direction direction) {
        this.claw = claw;
        this.claw.setDirection(direction);
    }

    public void openIntake() {
        //this.claw.setPosition(IntoTheDeepConstants.INTAKE_CLAW_OPEN);
        Actions.runBlocking(openIntakeClaw());
        sleep(200);
        intakeClawState = IntakeClawState.INTAKE_CLAW_OPENED;
    }

    public void intakeClawTransfer() {
        //this.claw.setPosition(IntoTheDeepConstants.INTAKE_CLAW_TRANSFER_OPEN);
        Actions.runBlocking(intakeClawTransferAction());
        sleep(200);
        intakeClawState = IntakeClawState.INTAKE_CLAW_TRANSFER_OPENED;
    }

    public void closeIntake() {
        //this.claw.setPosition(IntoTheDeepConstants.INTAKE_CLAW_CLOSE);
        Actions.runBlocking(closeIntakeClaw());
        sleep(200);
        intakeClawState = IntakeClawState.INTAKE_CLAW_CLOSED;
    }

    public class OpenIntakeClawAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //claw.setPosition(IntoTheDeepConstants.INTAKE_CLAW_OPEN);
            return false;
        }
    }

    public Action openIntakeClaw() {
        return new OpenIntakeClawAction();
    }


    public class IntakeClawTransferAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //claw.setPosition(IntoTheDeepConstants.INTAKE_CLAW_TRANSFER_OPEN);
            return false;
        }
    }

    public Action intakeClawTransferAction() {
        return new IntakeClawTransferAction();
    }


    public class CloseIntakeClawAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //claw.setPosition(IntoTheDeepConstants.INTAKE_CLAW_CLOSE);
            return false;
        }
    }

    public Action closeIntakeClaw() {
        return new CloseIntakeClawAction();
    }

}