package org.firstinspires.ftc.teamcode.components;

public class IntoTheDeepConstants {
    public static int TICKS_EXTEND_INITIALIZE = 0;
    public static int TICKS_EXTEND_NEAR_SUBMERSIBLE = 350 - TICKS_EXTEND_INITIALIZE;
    public static int TICKS_EXTEND_FAR_SUBMERSIBLE = 650 - TICKS_EXTEND_INITIALIZE;
    public static int TICKS_HIGH_BASKET = 3350;
    public static int TICKS_LOW_BASKET = 1500;
    public static int TICKS_SCORE_ABOVE_HIGH_CHAMBER = 2200;
    public static int TICKS_SCORE_ABOVE_LOW_CHAMBER = 1000;
    public static int TICKS_SCORE_HIGH_CHAMBER = 1500;
    public static int TICKS_SCORE_LOW_CHAMBER = 500;
    public static int TICKS_SCORE_HIGH_CHAMBER_RETRACT = TICKS_SCORE_ABOVE_HIGH_CHAMBER - TICKS_SCORE_HIGH_CHAMBER;
    public static int TICKS_SCORE_LOW_CHAMBER_RETRACT = TICKS_SCORE_ABOVE_LOW_CHAMBER - TICKS_SCORE_LOW_CHAMBER;

    public static int TICKS_SCORE_HIGH_RUNG = 2443;
    public static int TICKS_SCORE_LOW_RUNG = 1470;

    public static double INTAKE_WRIST_LEFT_PICKUP_POSITION = .35;
    public static double INTAKE_WRIST_RIGHT_PICKUP_POSITION = .65;
    public static double INTAKE_WRIST_LEFT_TRANSFER_POSITION = 0;
    public static double INTAKE_WRIST_RIGHT_TRANSFER_POSITION = 1;

    public static double INTAKE_WRIST_PICKUP_POSITION = 1;
    public static double INTAKE_WRIST_TRANSFER_POSITION = 0;

    public static double OUTTAKE_WRIST_TRANSFER_POSITION = 0.98;
    public static double OUTTAKE_WRIST_SAMPLE_SCORE_POSITION =  .35;
    public static double OUTTAKE_WRIST_SPECIMEN_SCORE_POSITION = 0;
    public static double OUTTAKE_WRIST_SPECIMEN_PICKUP_POSITION = 0.20;

    public static double OUTTAKE_CLAW_CLOSE = .85;
    public static double OUTTAKE_CLAW_OPEN = 0.4;

    public static double ACTIVE_INTAKE_SPIN = 1.0;
    public static double ACTIVE_INTAKE_STOP = 0;

    public static double INTAKE_EXTENDO_FAR_EXTEND_POSITION = 1;
    public static double INTAKE_EXTENDO_NEAR_EXTEND_POSITION = 0.5;
    public static double INTAKE_EXTENDO_RETRACTED_POSITION = 0;

    public static double POWER = 1.0;
    public static double POWER_ZERO = 0.0;

    public static double SLIDE_HOLD_POWER = .3;

}
