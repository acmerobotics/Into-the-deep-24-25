package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepOuttakeSlide;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepHang;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepIntakeSlide;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//import org.firstinspires.ftc.teamcode.MecanumDrive;

//import org.firstinspires.ftc.teamcode.TeleOp.SparkFunOTOSDrive;


@Config
@Autonomous
public abstract class IntoTheDeepAutonomous extends LinearOpMode {

    OpenCvWebcam webcam;

    IntoTheDeepOuttakeSlide outtake;
    IntoTheDeepIntakeSlide intake;
    IntoTheDeepHang hang;

    IntoTheDeepConfiguration itdConfiguration;

    @Override
    public void runOpMode() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        //webcam.setPipeline(pipeline);

        webcam.setMillisecondsPermissionTimeout(5000); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                webcam.startStreaming(640, 360, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        while (!opModeIsActive()){
            configureAutonomousSettings();
            showAutonomousSettings();
            sleep(200);
        }
        waitForStart();

        //Auton Code


    }



    public void configureAutonomousSettings(){
        if (gamepad1.dpad_up){
            itdConfiguration.incrementDelay(500);
        } else if(gamepad1.dpad_down){
            itdConfiguration.decrementDelay(500);
        }
    }

    public void showAutonomousSettings(){
    }


}

