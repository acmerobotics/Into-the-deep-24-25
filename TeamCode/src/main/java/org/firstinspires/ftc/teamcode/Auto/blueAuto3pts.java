package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Drive;

@Autonomous(name="blueAuto (3Pts scoring)", group="Autonomous")
public  class blueAuto3pts extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();
        Drive drive = new Drive(hardwareMap);
            if(opModeIsActive() && !isStopRequested()){
            drive.strafeTiles(-1);
            }
            while (opModeIsActive()){
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.update();
            }
    }
}