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

        leftExtender.setDirection(DcMotorSimple.Direction.REVERSE);
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
                double powerDifferential =
                        (
                                leftExtender.getCurrentPosition()/ LEFT_EXTENDER_ENDSTOP - rightExtender.getCurrentPosition()/ RIGHT_EXTENDER_ENDSTOP
                        ) / 2 * 10;

                //1 if going out, -1 if going in
                int direction = gamepad2.left_stick_y > 0 ? 1 : -1;

                leftExtender.setPower(Clamp((-gamepad2.left_stick_y - direction * powerDifferential)));
                rightExtender.setPower(Clamp((-gamepad2.left_stick_y + direction * powerDifferential)));
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
            // To set the wrist servo A is down, B is forward
            {
                if (gamepad2.a) {
                    wristServo.setPosition(0);
                }
                if (gamepad2.b) {
                    wristServo.setPosition(0.3);
                }
            }
            // Gripper controls Y is open, X is close
            {
                if (gamepad2.y){
                    gripperServo.setPosition(0);
                }
                if (gamepad2.x){
                    gripperServo.setPosition(.25);
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

    static double Clamp(double value){
        return Math.min(1,Math.max(-1, value));
    }
}



