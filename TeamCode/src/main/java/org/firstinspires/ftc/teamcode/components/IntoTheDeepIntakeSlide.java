package org.firstinspires.ftc.teamcode.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Timer;

public class IntoTheDeepIntakeSlide implements IntoTheDeepComponent {

    private DcMotor extendo;
    private DcMotorSimple.Direction extendoDirection;
    private TouchSensor slideSensor;
    private Telemetry telemetry;

    private IntakeSlideState intakeSlideState;

    public enum IntakeSlideState {
        EXTENDED_HALF,
        EXTENDED_FULL,
        NO_EXTENSION,
    }

    public IntoTheDeepIntakeSlide(DcMotor motor, DcMotorSimple.Direction direction, Telemetry telemetry, TouchSensor slideSensor) {
        this.extendo = motor;
        this.extendoDirection = direction;
        this.extendo.setPower(IntoTheDeepConstants.POWER_ZERO);
        this.extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.slideSensor = slideSensor;

        this.telemetry = telemetry;
        //ticks per inch =32.53 ~ 33
        this.intakeSlideState = IntakeSlideState.NO_EXTENSION;
    }

    @Override
    public void init() {
        this.moveSlide(IntoTheDeepConstants.TICKS_EXTEND_INITIALIZE, this.extendoDirection);
    }

    public void extendFarFromSubmersible() {
        //moveSlide(IntoTheDeepConstants.TICKS_EXTEND_FAR_SUBMERSIBLE, extendoDirection);
        Actions.runBlocking(extendFarFromSubmersibleAction());
        intakeSlideState = IntakeSlideState.EXTENDED_FULL;
    }

    public void retractExtendoFarFromSubmersible() {
        //moveSlide(IntoTheDeepConstants.TICKS_EXTEND_FAR_SUBMERSIBLE, extendoDirection.inverted());
        Actions.runBlocking(retractFarFromSubmersibleAction());
        intakeSlideState = IntakeSlideState.NO_EXTENSION;
    }

    public void extendNearFromSubmersible() {
        //moveSlide(IntoTheDeepConstants.TICKS_EXTEND_NEAR_SUBMERSIBLE, extendoDirection);
        Actions.runBlocking(extendCloseToSubmersibleAction());
        intakeSlideState = IntakeSlideState.EXTENDED_HALF;
    }

    public void retractExtendoNearFromSubmersible() {
        //moveSlide(IntoTheDeepConstants.TICKS_EXTEND_NEAR_SUBMERSIBLE, extendoDirection.inverted());
        Actions.runBlocking(retractCloseToSubmersibleAction());
        intakeSlideState = IntakeSlideState.NO_EXTENSION;
    }

    public void moveSlide(int encoderTicks, DcMotorSimple.Direction direction) {
        if(direction == DcMotorSimple.Direction.REVERSE){
            moveSlideToTouchSensor(direction);
            return;
        }
        this.extendo.setDirection(direction);
        this.extendo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.extendo.setPower(IntoTheDeepConstants.POWER);
        this.extendo.setTargetPosition(encoderTicks);
        this.extendo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (this.extendo.isBusy()) {
            sleep(10);
        }
    }

    public void moveSlideToTouchSensor(DcMotorSimple.Direction direction){
        this.extendo.setDirection(direction);
        this.extendo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.extendo.setPower(IntoTheDeepConstants.POWER);
        long t1 = System.currentTimeMillis();
        while(!slideSensor.isPressed()){
            long t2 = System.currentTimeMillis();
            if(t2-t1>4000){
                break;
            }
            sleep(10);
        }
        this.extendo.setPower(IntoTheDeepConstants.POWER_ZERO);
    }

    public class ExtendFarFromSubmersibleAction implements Action {


        private boolean initalized = false;


        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {


            if (!initalized) {
                extendo.setPower(IntoTheDeepConstants.POWER);
                extendo.setDirection(extendoDirection);
                initalized = true;
            }
            double pos = extendo.getCurrentPosition();
            telemetryPacket.put("intake slide extendPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_EXTEND_FAR_SUBMERSIBLE) {
                return true;
            }else{
                extendo.setPower(IntoTheDeepConstants.POWER_ZERO);
                return false;
            }
        }

    }

    public Action extendFarFromSubmersibleAction() {
        return new ExtendFarFromSubmersibleAction();
    }


    public class RetractFarFromSubmersibleAction implements Action {


        private boolean initalized = false;


        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {


            if (!initalized) {
                extendo.setPower(IntoTheDeepConstants.POWER);
                extendo.setDirection(extendoDirection.inverted());
                initalized = true;
            }
            double pos = extendo.getCurrentPosition();
            telemetryPacket.put("intake slide extendPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_EXTEND_FAR_SUBMERSIBLE) {
                return true;
            }else{
                extendo.setPower(IntoTheDeepConstants.POWER_ZERO);
                return false;
            }
        }


    }

    public Action retractFarFromSubmersibleAction() {
        return new RetractFarFromSubmersibleAction();
    }


    public class ExtendCloseFromSubmersibleAction implements Action {


        private boolean initalized = false;


        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {


            if (!initalized) {
                extendo.setPower(IntoTheDeepConstants.POWER);
                extendo.setDirection(extendoDirection);
                initalized = true;
            }
            double pos = extendo.getCurrentPosition();
            telemetryPacket.put("intake slide extendPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_EXTEND_NEAR_SUBMERSIBLE) {
                return true;
            }else{
                extendo.setPower(IntoTheDeepConstants.POWER_ZERO);
                return false;
            }
        }


    }

    public Action extendCloseToSubmersibleAction() {
        return new ExtendCloseFromSubmersibleAction();
    }


    public class RetractCloseFromSubmersibleAction implements Action {


        private boolean initalized = false;


        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {


            if (!initalized) {
                extendo.setPower(IntoTheDeepConstants.POWER);
                extendo.setDirection(extendoDirection.inverted());
                initalized = true;
            }
            double pos = extendo.getCurrentPosition();
            telemetryPacket.put("intake slide extendPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_EXTEND_NEAR_SUBMERSIBLE) {
                return true;
            }else{
                extendo.setPower(IntoTheDeepConstants.POWER_ZERO);
                return false;
            }
        }


    }

    public Action retractCloseToSubmersibleAction() {
        return new RetractCloseFromSubmersibleAction();
    }

}


