package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.ArrayList;
import java.util.List;

public class autoBuilder {
        private final List<Runnable> commands = new ArrayList<>();
        private final Drive drive;
        private final Telemetry telemetry;
        private static final float INCHES_TO_TILES = 1.0f / 24.0f;

        private float currentX = 0;
        private float currentY = 0;
        private double currentHeading = 0; // Heading in degrees

        public autoBuilder(HardwareMap hardwareMap, Telemetry telemetry) {
                this.drive = new Drive(hardwareMap);
                this.telemetry = telemetry;
        }

        public autoBuilder gotoPosition(float targetX, float targetY, double targetHeading) {
                commands.add(() -> {
                        // Calculate distance
                        double distance = Math.sqrt(Math.pow(targetX - currentX, 2) + Math.pow(targetY - currentY, 2));

                        // Calculate heading (angle) to the target position
                        double headingToTarget = Math.toDegrees(Math.atan2(targetY - currentY, targetX - currentX));

                        // Adjust the robot's heading, making it the shortest turn
                        double turnAmount = headingToTarget - currentHeading;
                        turnAmount = (turnAmount + 180) % 360 - 180;

                        telemetry.addData("Action", "Moving to (%.2f, %.2f) with heading %.2f degrees", targetX, targetY, targetHeading);
                        telemetry.update();

                        // Perform the movement and rotation.  Consider the order.
                        drive.setRotateDegrees(turnAmount); // Rotate to the target heading *first*
                        drive.driveTiles((float) (distance * INCHES_TO_TILES)); // Move forward

                        // Update current position and heading
                        currentX = targetX;
                        currentY = targetY;
                        currentHeading = targetHeading;
                });
                return this;
        }

        public autoBuilder strafeToPosition(float targetX, float targetY, double targetHeading) {
                commands.add(() -> {
                        // Calculate distance
                        double distance = Math.sqrt(Math.pow(targetX - currentX, 2) + Math.pow(targetY - currentY, 2));

                        // Calculate angle for strafing.  This is different from gotoPosition.
                        double strafeAngle = Math.toDegrees(Math.atan2(targetY - currentY, targetX - currentX));

                        // Adjust the robot's heading, making it the shortest turn
                        double turnAmount = strafeAngle - currentHeading;
                        turnAmount = (turnAmount + 180) % 360 - 180;

                        telemetry.addData("Action", "Strafing to (%.2f, %.2f) with heading %.2f degrees", targetX, targetY, targetHeading);
                        telemetry.update();

                        // Perform the movement and rotation
                        drive.setRotateDegrees(turnAmount);  // Rotate to the strafe angle
                        drive.strafeTiles((float) (distance * INCHES_TO_TILES)); // Use strafeTiles
                        currentX = targetX;
                        currentY = targetY;
                        currentHeading = targetHeading;
                });
                return this;
        }

        public autoBuilder waitTimeMS(long milliseconds) {
                commands.add(() -> {
                        telemetry.addData("Action", "Waiting %.2f seconds", milliseconds / 1000.0);
                        telemetry.update();
                        try {
                                Thread.sleep(milliseconds);
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt(); // Restore the interrupted status
                        }
                }
                );
            return null;
        }
}
