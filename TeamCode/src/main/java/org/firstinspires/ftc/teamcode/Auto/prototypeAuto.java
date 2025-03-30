package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot.autoBuilder;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "Prototype autonomous", group = "Autonomous")
public class prototypeAuto extends LinearOpMode {
    private List<Runnable> autonomousPaths; // List to store the autonomous paths
    private List<Runnable> autonomouspath0; // List to store the autonomous paths
    private List<Runnable> autonomouspath1;
    private int selectedPath; // Variable to store the selected path

    public void runOpMode() {
        autonomousPaths = new ArrayList<>(); // Initialize the list
        autonomouspath0 = new ArrayList<>();
        autonomouspath1 = new ArrayList<>();// Initialize the list
        autoBuilder autoBuilder = new autoBuilder(hardwareMap, telemetry);
        selectedPath = 0; // Default to path0

        // Create and add Runnable paths to the list
        autonomouspath0.add(() -> {
            // Path 1: Example actions using autoBuilder
            autoBuilder.gotoPosition(24, 24, 0);
            autoBuilder.waitTimeMS(1000);
            autoBuilder.strafeToPosition(24, 48, 90);
            autoBuilder.gotoPosition(0, 48, 0);
        });
        autonomouspath1.add(() -> {
            //Path 2 : different movements
            autoBuilder.gotoPosition(0, 0, 0);
            autoBuilder.waitTimeMS(500);
        });
        // Add more paths as needed...
        telemetry.addData("Path Selection","0 for path0, 1 for path1");
        telemetry.update();
        // Add user input to change selected path (Example)
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while(timer.seconds() < 5 && !isStopRequested()){
            if (gamepad1.a) {
                selectedPath = 0;
            } else if (gamepad1.b) {
                selectedPath = 1;
            }
        }
        waitForStart(); // Wait for the start button to be pressed

        // Execute the selected path(s)
        if(opModeIsActive()){
            runAutonomousPath(autoBuilder);
        }
    }

    private void runAutonomousPath(autoBuilder autoBuilder) {
        // Select the correct path based on the selectedPath variable
        List<Runnable> pathsToRun;
        switch (selectedPath) {
            case 0:
                pathsToRun = autonomouspath0;
                break;
            case 1:
                pathsToRun = autonomouspath1;
                break;
            default:
                pathsToRun = autonomouspath0; // Default to path0 if an invalid selection is made
        }

        // Iterate through the selected path and execute it
        for (Runnable path : pathsToRun) {
            path.run(); // Execute each Runnable
        }
    }
}