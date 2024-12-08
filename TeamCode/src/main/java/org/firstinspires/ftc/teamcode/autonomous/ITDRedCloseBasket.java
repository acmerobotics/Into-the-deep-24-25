package org.firstinspires.ftc.teamcode.autonomous;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepIntakeClaw;
import org.firstinspires.ftc.teamcode.components.IntoTheDeepOuttakeClaw;
import org.firstinspires.ftc.teamcode.subsystem.IntoTheDeepHangSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.IntoTheDeepIntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.IntoTheDeepOutakeSubsystem;

public class ITDRedCloseBasket extends IntoTheDeepAutonomousRed{
    IntoTheDeepHangSubsystem hang;
    IntoTheDeepIntakeSubsystem intake;
    IntoTheDeepOutakeSubsystem outtake;
    IntoTheDeepConfiguration itdConfiguration;
    IntoTheDeepIntakeClaw intakeClaw;
    IntoTheDeepOuttakeClaw outtakeClaw;

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-36, -60, Math.toRadians(-90)); //ALL NUMBERS BASED ON 15in TRACK WIDTH
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder scoreSpecimen = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-10,-34));
        //.build();
        TrajectoryActionBuilder pickupSample1 = drive.actionBuilder(new Pose2d(10,-34, Math.toRadians(90)))
                .splineToLinearHeading(new Pose2d(-30,-40, Math.toRadians(-180)), Math.toRadians(-90))
                .strafeTo(new Vector2d(-30,-25.5));
        //.build();
        TrajectoryActionBuilder scoreSample1 = drive.actionBuilder(new Pose2d(-30,-25.5, Math.toRadians(-180)))
                .splineToLinearHeading(new Pose2d(-55,-55, Math.toRadians(-315)), Math.toRadians(-180));
        //.build();
        TrajectoryActionBuilder pickupSample2 = drive.actionBuilder(new Pose2d(-55,-55, Math.toRadians(-315)))
                .splineToSplineHeading(new Pose2d(-37.25,-25.5,Math.toRadians(-180)), Math.toRadians(-315));
        //.build();
        TrajectoryActionBuilder scoreSample2 = drive.actionBuilder(new Pose2d(-37.25, -25.5, Math.toRadians(-180)))
                .splineToLinearHeading(new Pose2d(-55,-55,Math.toRadians(-315)), Math.toRadians(-180));
        //.build();
        TrajectoryActionBuilder pickupSample3 = drive.actionBuilder(new Pose2d(-55,-55, Math.toRadians(-315)))
                .splineToLinearHeading(new Pose2d(-44.5,-25.5,Math.toRadians(-180)), Math.toRadians(-315));
        //.build();
        TrajectoryActionBuilder scoreSample3 = drive.actionBuilder(new Pose2d(-44.5, -25.5, Math.toRadians(-180)))
                .splineToLinearHeading(new Pose2d(-55,-55,Math.toRadians(-315)), Math.toRadians(-180));
        //.build();
        TrajectoryActionBuilder parkRobot = drive.actionBuilder(new Pose2d(-55, -55, Math.toRadians(-315)))
                .splineToLinearHeading(new Pose2d(-28,-12, Math.toRadians(0)), Math.toRadians(-315));
        //.build();

        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.update();
        }

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                scoreSpecimen.build(),
                                new InstantAction(() -> outtake.positionHighChamber())
                        ),
                        new InstantAction(() -> outtake.highChamberScore()),
                        outtakeClaw.openOuttakeClaw(),
                        new ParallelAction(
                                pickupSample1.build(),
                                new InstantAction(() -> outtake.highBasketToTransfer()),
                                new InstantAction(() -> intake.findSampleFromNearSubmersible())
                        ),
                        new InstantAction(() -> intake.pickupSampleFromNearSubmersible()),
                        new ParallelAction(
                                scoreSample1.build(),
                                new InstantAction(() -> outtake.positionHighBasket())
                        ),
                        outtakeClaw.openOuttakeClaw(),
                        new ParallelAction(
                                pickupSample2.build(),
                                new InstantAction(() -> outtake.highBasketToTransfer()),
                                new InstantAction(() -> intake.findSampleFromNearSubmersible())
                        ),
                        new InstantAction(() -> intake.pickupSampleFromNearSubmersible()),
                        new ParallelAction(
                                scoreSample2.build(),
                                new InstantAction(() -> outtake.positionHighBasket())
                        ),
                        outtakeClaw.openOuttakeClaw(),
                        new ParallelAction(
                                pickupSample3.build(),
                                new InstantAction(() -> outtake.highBasketToTransfer()),
                                new InstantAction(() -> intake.findSampleFromNearSubmersible())
                        ),
                        new InstantAction(() -> intake.pickupSampleFromNearSubmersible()),
                        new ParallelAction(
                                scoreSample3.build(),
                                new InstantAction(() -> outtake.positionHighBasket())
                        ),
                        outtakeClaw.openOuttakeClaw(),
                        new ParallelAction(
                                parkRobot.build(),
                                new InstantAction(() -> outtake.highBasketToTransfer())
                        )

                )


        );

    }
}
