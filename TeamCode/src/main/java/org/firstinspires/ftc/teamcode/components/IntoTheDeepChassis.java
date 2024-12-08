package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntoTheDeepChassis {

    private DcMotor leftFrontAsDcMotor;
    private DcMotor rightFrontAsDcMotor;
    private DcMotor rightBackAsDcMotor;
    private DcMotor leftBackAsDcMotor;
    private Gamepad gamepad1;


    boolean obstacleDetected;

    public IntoTheDeepChassis(HardwareMap hardwareMap, Gamepad gamepad1, Telemetry telemetry) {

        leftFrontAsDcMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        rightFrontAsDcMotor = hardwareMap.get(DcMotor.class, "frontRight");
        rightBackAsDcMotor = hardwareMap.get(DcMotor.class, "backRight");
        leftBackAsDcMotor = hardwareMap.get(DcMotor.class, "backLeft");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        leftFrontAsDcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackAsDcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontAsDcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackAsDcMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        this.gamepad1 = gamepad1;

    }

    private void setPowers(double frontLeftPower, double frontRightPower, double
            backLeftPower, double backRightPower) {
         double maxSpeed = 1.0;
        maxSpeed = Math.max(maxSpeed, Math.abs(frontLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(frontRightPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backRightPower));

        frontLeftPower /= maxSpeed;
        frontRightPower /= maxSpeed;
        backLeftPower /= maxSpeed;
        backRightPower /= maxSpeed;

        leftFrontAsDcMotor.setPower(frontLeftPower);
        rightFrontAsDcMotor.setPower(frontRightPower);
        leftBackAsDcMotor.setPower(backLeftPower);
        rightBackAsDcMotor.setPower(backRightPower);
    }

    public void drive() {

        double forward = -gamepad1.left_stick_y;
        double right = gamepad1.left_stick_x * 1.1;
        double rotate = gamepad1.right_stick_x;

        double frontLeftPower = forward + right + rotate;
        double frontRightPower = forward - right - rotate;
        double backLeftPower = forward - right + rotate;
        double backRightPower = forward + right - rotate;

        setPowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }
}
