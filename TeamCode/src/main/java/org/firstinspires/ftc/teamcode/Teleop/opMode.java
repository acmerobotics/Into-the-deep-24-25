package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="opMode", group="Linear OpMode")
public class opMode extends LinearOpMode {
    public void runOpMode() {
        DcMotor DcMotorA = hardwareMap.get(DcMotor.class, "Drive1");
        DcMotor DcMotorB = hardwareMap.get(DcMotor.class, "Drive2");
        DcMotor DcMotorC = hardwareMap.get(DcMotor.class, "Drive3");
        DcMotor DcMotorD = hardwareMap.get(DcMotor.class, "Drive4");
        DcMotorC.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotorD.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();
        while (opModeIsActive()){
            telemetry.addData("Motor A Pos:", DcMotorA.getCurrentPosition());
            telemetry.addData("Motor B Pos:", DcMotorB.getCurrentPosition());
            telemetry.addData("Motor C Pos:", DcMotorC.getCurrentPosition());
            telemetry.addData("Motor D Pos:", DcMotorD.getCurrentPosition());

            if (gamepad1.left_stick_y != 0){
                DcMotorA.setPower(gamepad1.left_stick_y);
                DcMotorB.setPower(gamepad1.left_stick_y);
                DcMotorC.setPower(gamepad1.left_stick_y);
                DcMotorD.setPower(gamepad1.left_stick_y);
            }

            else if (gamepad1.right_stick_x != 0){
                DcMotorA.setPower(-gamepad1.right_stick_x);
                DcMotorB.setPower(-gamepad1.right_stick_x);
                DcMotorC.setPower(gamepad1.right_stick_x);
                DcMotorD.setPower(gamepad1.right_stick_x);

            }

            else if (gamepad1.left_stick_y == 0) {
                DcMotorA.setPower(0);
                DcMotorB.setPower(0);
                DcMotorC.setPower(0);
                DcMotorD.setPower(0);
            }
           if (gamepad1.dpad_left){
                DcMotorA.setPower(1);
                DcMotorB.setPower(-1);
                DcMotorC.setPower(1);
                DcMotorD.setPower(-1);
            }
           if (gamepad1.dpad_right){
                DcMotorA.setPower(-1);
                DcMotorB.setPower(1);
                DcMotorC.setPower(-1);
                DcMotorD.setPower(1);
            }
            telemetry.update();
        }
    }
}
