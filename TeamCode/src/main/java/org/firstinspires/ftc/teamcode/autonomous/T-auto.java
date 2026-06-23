package org.firstinspires.ftc.teamcode.autonomous;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "MyNewAutonomous", group = "Autonomous")
public class MyNewAutonomous extends LinearOpMode {

    // 1. We pull in the Subsystems from your original file
    private Baskets.Claw1 claw1;
    private Baskets.Claw2 claw2;
    private Baskets.Lift lift;
    private Baskets.LiftTilt liftTilt;

    @Override
    public void runOpMode() throws InterruptedException {
        // 2. Define where your robot starts on the field (X, Y, and Angle)
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));
        
        // Initialize Roadrunner drive core
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        
        // Initialize your mechanisms using your existing classes
        Baskets basketsInstance = new Baskets();
        claw1 = basketsInstance.new Claw1(hardwareMap);
        claw2 = basketsInstance.new Claw2(hardwareMap);
        lift = basketsInstance.new Lift(hardwareMap);
        liftTilt = basketsInstance.new LiftTilt(hardwareMap);

        // 3. Build your paths before the match starts
        // This simple path tells the robot to drive forward 24 inches
        TrajectoryActionBuilder driveForwardPath = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(24, 0));

        // Prepare the robot: Close the claw tight on the starting block
        Actions.runBlocking(claw1.closeClaw());

        // --- Wait for the drive team to press the PLAY button ---
        waitForStart();
        if (isStopRequested()) return;

        // 4. Run the steps in order
        
        // Step A: Drive forward 24 inches
        Actions.runBlocking(driveForwardPath.build());
        
        // Step B: Raise the lift up
        Actions.runBlocking(lift.liftUpBar2());
        sleep(500); // Give the hardware a brief moment to settle
        
        // Step C: Open the claw to drop the element
        Actions.runBlocking(claw1.openClaw());
        sleep(200);
        
        // Step D: Lower the lift safely back down
        Actions.runBlocking(lift.liftDown());
    }
}
