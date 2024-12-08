package org.firstinspires.ftc.teamcode.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntoTheDeepOuttakeSlide implements IntoTheDeepComponent {

    private DcMotor bottomMotor;
    private DcMotorSimple.Direction bottomMotorDirection;
    private Telemetry telemetry;
    private IntoTheDeepChassis intoTheDeepChassis;
    private OuttakeSlideState outtakeSlideState;

    public IntoTheDeepOuttakeSlide(IntoTheDeepChassis intoTheDeepChassis, DcMotor bottomMotor, DcMotor.Direction bottomMotorDirection, Telemetry telemetry) {

        this.bottomMotor = bottomMotor;
        this.bottomMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.bottomMotor.setPower(IntoTheDeepConstants.POWER_ZERO);
        this.bottomMotorDirection = bottomMotorDirection;
        this.telemetry = telemetry;
        //ticks per inch = 120.53 ~ 123
        outtakeSlideState = OuttakeSlideState.RETRACTED_OUTTAKE;
        this.intoTheDeepChassis = intoTheDeepChassis;
    }

    @Override
    public void init() {

    }

    public enum OuttakeSlideState {
        SCORE_LOWER_BASKET,
        SCORE_HIGHER_BASKET,
        PICKUP_SPECIMEN,
        POSITION_ABOVE_HIGHER_CHAMBER,
        POSITION_ABOVE_LOWER_CHAMBER,
        SCORE_LOWER_CHAMBER,
        SCORE_HIGHER_CHAMBER,
        RETRACTED_OUTTAKE,
        HIGH_RUNG,
        LOW_RUNG

    }

    public void powerOff() {
        this.bottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.bottomMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.bottomMotor.setPower(IntoTheDeepConstants.POWER_ZERO);
    }

    public void initialize(int encoderTicks, DcMotorSimple.Direction bottonDirection) {
        this.bottomMotor.setDirection(bottonDirection);
        this.bottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.bottomMotor.setPower(IntoTheDeepConstants.POWER);
        this.bottomMotor.setTargetPosition(encoderTicks);
        this.bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.bottomMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public Action scoreHighBasketAction() {
        return new ScoreHighBasketAction();
    }

    public class ScoreHighBasketAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_HIGH_BASKET, bottomMotorDirection);
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);

            if (pos < IntoTheDeepConstants.TICKS_HIGH_BASKET) {
                intoTheDeepChassis.drive();
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.SLIDE_HOLD_POWER);
                outtakeSlideState = OuttakeSlideState.SCORE_HIGHER_BASKET;
                return false;
            }
        }

    }

    public Action retractFromHighBasketAction() {
        return new RetractFromHighBasketAction();
    }

    public class RetractFromHighBasketAction implements Action {

        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_HIGH_BASKET, bottomMotorDirection.inverted());
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);

            if (pos < IntoTheDeepConstants.TICKS_HIGH_BASKET) {
                intoTheDeepChassis.drive();
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.POWER_ZERO);
                outtakeSlideState = OuttakeSlideState.RETRACTED_OUTTAKE;
                return false;
            }
        }

    }

    public Action scoreLowBasketAction() {
        return new ScoreLowBasketAction();
    }

    public class ScoreLowBasketAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_LOW_BASKET, bottomMotorDirection);
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_LOW_BASKET) {
                intoTheDeepChassis.drive();
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.SLIDE_HOLD_POWER);
                outtakeSlideState = OuttakeSlideState.SCORE_LOWER_BASKET;
                return false;
            }
        }
    }

    public Action retractFromLowBasketAction() {
        return new RetractFromLowBasketAction();
    }

    public class RetractFromLowBasketAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_LOW_BASKET, bottomMotorDirection.inverted());
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_LOW_BASKET) {
                intoTheDeepChassis.drive();
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.POWER_ZERO);
                outtakeSlideState = OuttakeSlideState.RETRACTED_OUTTAKE;
                return false;
            }
        }
    }

    public Action travelAboveHighChamberAction() {
        return new TravelAboveHighChamberAction();
    }

    public class TravelAboveHighChamberAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_SCORE_ABOVE_HIGH_CHAMBER, bottomMotorDirection);
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_SCORE_ABOVE_HIGH_CHAMBER) {
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.SLIDE_HOLD_POWER);
                outtakeSlideState = OuttakeSlideState.POSITION_ABOVE_HIGHER_CHAMBER;
                return false;
            }
        }
    }

    public Action scoreHighChamberAction() {
        return new ScoreHighChamberAction();
    }

    public class ScoreHighChamberAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_SCORE_HIGH_CHAMBER, bottomMotorDirection.inverted());
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_SCORE_HIGH_CHAMBER) {
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.SLIDE_HOLD_POWER);
                outtakeSlideState = OuttakeSlideState.SCORE_HIGHER_CHAMBER;
                return false;
            }
        }
    }

    public Action retractFromHighChamberAction() {
        return new RetractFromHighChamberAction();
    }

    public class RetractFromHighChamberAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {


            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_SCORE_HIGH_CHAMBER_RETRACT, bottomMotorDirection.inverted());
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_SCORE_HIGH_CHAMBER_RETRACT) {
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.POWER_ZERO);
                outtakeSlideState = OuttakeSlideState.RETRACTED_OUTTAKE;
                return false;
            }
        }
    }

    public Action travelAboveLowChamberAction() {
        return new TravelAboveLowChamberAction();
    }

    public class TravelAboveLowChamberAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_SCORE_ABOVE_LOW_CHAMBER, bottomMotorDirection);
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_SCORE_ABOVE_LOW_CHAMBER) {
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.SLIDE_HOLD_POWER);
                outtakeSlideState = OuttakeSlideState.POSITION_ABOVE_LOWER_CHAMBER;
                return false;
            }
        }
    }

    public Action scoreLowChamberAction() {
        return new ScoreLowChamberAction();
    }

    public class ScoreLowChamberAction implements Action {

        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_SCORE_LOW_CHAMBER, bottomMotorDirection.inverted());
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);

            if (pos < IntoTheDeepConstants.TICKS_SCORE_LOW_CHAMBER) {
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.SLIDE_HOLD_POWER);
                outtakeSlideState = OuttakeSlideState.SCORE_LOWER_CHAMBER;
                return false;
            }
        }
    }

    public Action retractFromLowChamberAction() {
        return new RetractFromLowChamberAction();
    }

    public class RetractFromLowChamberAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {


            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_SCORE_LOW_CHAMBER_RETRACT, bottomMotorDirection.inverted());
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_SCORE_LOW_CHAMBER_RETRACT) {
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.POWER_ZERO);
                outtakeSlideState = OuttakeSlideState.RETRACTED_OUTTAKE;
                return false;
            }
        }
    }


    public Action scoreHighRungAction() {
        return new ScoreHighRungAction();
    }

    public class ScoreHighRungAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_SCORE_HIGH_RUNG, bottomMotorDirection);
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);

            if (pos < IntoTheDeepConstants.TICKS_SCORE_HIGH_RUNG) {
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.SLIDE_HOLD_POWER);
                outtakeSlideState = OuttakeSlideState.HIGH_RUNG;
                return false;
            }
        }
    }

    public Action retractFromHighRungAction() {
        return new RetractFromHighRungAction();
    }


    public class RetractFromHighRungAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_SCORE_HIGH_RUNG, bottomMotorDirection.inverted());
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_SCORE_HIGH_RUNG) {
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.POWER_ZERO);
                outtakeSlideState = OuttakeSlideState.RETRACTED_OUTTAKE;
                return false;
            }
        }
    }

    public Action scoreLowRungAction() {
        return new ScoreLowRungAction();
    }

    public class ScoreLowRungAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_SCORE_LOW_RUNG, bottomMotorDirection);
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_SCORE_LOW_RUNG) {
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.SLIDE_HOLD_POWER);
                outtakeSlideState = OuttakeSlideState.LOW_RUNG;
                return false;
            }
        }
    }


    public Action retractFromLowRungAction() {
        return new RetractFromLowRungAction();
    }

    public class RetractFromLowRungAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {


            if (!initialized) {
                initialize(IntoTheDeepConstants.TICKS_SCORE_LOW_RUNG, bottomMotorDirection.inverted());
                initialized = true;
            }
            double pos = bottomMotor.getCurrentPosition();
            telemetryPacket.put("outtake slide liftPos: ", pos);


            if (pos < IntoTheDeepConstants.TICKS_SCORE_LOW_RUNG) {
                return true;
            } else {
                bottomMotor.setPower(IntoTheDeepConstants.POWER_ZERO);
                outtakeSlideState = OuttakeSlideState.RETRACTED_OUTTAKE;
                return false;
            }
        }
    }
}
