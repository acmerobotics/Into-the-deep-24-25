package org.firstinspires.ftc.teamcode.Teleop;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="opMode", group="Linear OpMode")
public class opMode extends LinearOpMode {
    static final int LEFT_EXTENDER_ENDSTOP = 1695;
    static final int RIGHT_EXTENDER_ENDSTOP = 1695;
    static final int LIFT_ENDSTOP = 2100;
    static final double WRIST_UP_POSITION = 0;
    static final double WRIST_DOWN_POSITION = 0.3;
    static final double GRIPPER_OPEN_POSITION = 0;
    static final double GRIPPER_CLOSED_POSITION = 0.25;

    public void runOpMode() {
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        DcMotor leftExtender = hardwareMap.get(DcMotor.class, "leftExtender");
        DcMotor rightExtender = hardwareMap.get(DcMotor.class, "rightExtender");
        DcMotor lift = hardwareMap.get(DcMotor.class, "lift");
        Servo wristServo = hardwareMap.get(Servo.class, "wristServo");
        Servo gripperServo = hardwareMap.get(Servo.class, "gripperServo");

        rightExtender.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftExtender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightExtender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftExtender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightExtender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        waitForStart();

        while (opModeIsActive()) {

           // Translation and rotation controls
            {
                double x = gamepad1.left_stick_x;
                double y = -gamepad1.left_stick_y; // Invert y to match coordinate system
                double turn = gamepad1.right_stick_x;
                leftFront.setPower(x + y + turn);
                leftBack.setPower(-x + y + turn);
                rightBack.setPower(x + y - turn);
                rightFront.setPower(-x + y - turn);
            }

            // Extender controls
            {
                leftExtender.setPower(0);
                rightExtender.setPower(0);

                if (gamepad1.y && leftExtender.getCurrentPosition() < LEFT_EXTENDER_ENDSTOP && rightExtender.getCurrentPosition() < RIGHT_EXTENDER_ENDSTOP) {
                    leftExtender.setPower(1);
                    rightExtender.setPower(1);
                }
                if (gamepad1.a && leftExtender.getCurrentPosition() > 0 && rightExtender.getCurrentPosition() > 0) {
                    leftExtender.setPower(-1);
                    rightExtender.setPower(-1);
                }
            }
            // Lift controls
            {
                lift.setPower(0);

                if (gamepad1.dpad_up && lift.getCurrentPosition() < LIFT_ENDSTOP) {
                    lift.setPower(1);
                }

                if (gamepad1.dpad_down && lift.getCurrentPosition() > 0) {
                    lift.setPower(-1);
                }
            }
            // To set the wrist servo B is down, X is up
            {
                if (gamepad1.x) {
                    wristServo.setPosition(WRIST_UP_POSITION);
                }
                if (gamepad1.b) {
                    wristServo.setPosition(WRIST_DOWN_POSITION);
                }
            }
            // Gripper controls Y is open, X is close
            {
                if (gamepad2.y){
                    gripperServo.setPosition(GRIPPER_OPEN_POSITION);
                }

                if (gamepad2.x){
                    gripperServo.setPosition(GRIPPER_CLOSED_POSITION);
                }
            }

            telemetry.addData("leftFront Pos:", leftFront.getCurrentPosition());
            telemetry.addData("leftBack Pos:", leftBack.getCurrentPosition());
            telemetry.addData("rightBack Pos:", rightBack.getCurrentPosition());
            telemetry.addData("rightFront  Pos:", rightFront.getCurrentPosition());
            telemetry.addData("leftExtender Pos", leftExtender.getCurrentPosition());
            telemetry.addData("rightExtender Pos", rightExtender.getCurrentPosition());
            telemetry.addData("Lift Pos", lift.getCurrentPosition());
            telemetry.addData("Wrist Pos: ", wristServo.getPosition());
            telemetry.addData("Gripper Pos: ", gripperServo.getPosition());
            telemetry.addData("Left stick Y: ", gamepad2.left_stick_y);
            telemetry.addData("Right stick Y: ", gamepad2.right_stick_y);
            telemetry.update();
        }
    }
}



