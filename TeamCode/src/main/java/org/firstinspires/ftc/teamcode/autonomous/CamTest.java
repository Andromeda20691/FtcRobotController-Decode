package org.firstinspires.ftc.teamcode.autonomous;


import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


public class CamTest extends LinearOpMode{

    private double speedMultiplier = 1;
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    private DcMotor sliderMotor;




    public double sliderMotorPos;
    private double denominator =0;
    private double frontLeftPower =0, backLeftPower =0, frontRightPower =0, backRightPower =0;
    private double y =0, x =0, rx =0;

    private Servo servoClaw;

    public static double CLAW_OPEN_POSITION = 0.43;
    public static double CLAW_CLOSED_POSITION = 0.1;





    private long lastFrameTime = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "initializing Camera");
        telemetry.update();

        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .build();

        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .build();



        long initStartTime = System.currentTimeMillis();
        boolean framesReceived = false;

        // Check for frames during initialization (5-second timeout)
        while (System.currentTimeMillis() - initStartTime < 5000 && !isStarted() && !isStopRequested()){
            double frameRate = visionPortal.getFps();
            if (frameRate > 10) {
                framesReceived = true;
                break;
            }
            telemetry.addData("Status", "Waiting for camera frames");
            telemetry.update();
        }

        // Determine if frames are being received
        if (framesReceived) {
            telemetry.addData("Status", "Camera initialized and receiving frames");
        } else {
            telemetry.addData("Status", "Camera not providing frames");
        }
        telemetry.update();



        //sleep(500);
        //telemetry.addData("Status", "Camera initialized");
        //telemetry.update();
        waitForStart();


        while (!isStopRequested() && opModeIsActive()) {

            if (tagProcessor.getDetections().size() > 0){
                AprilTagDetection tag = tagProcessor.getDetections().get(0);

                telemetry.addData("x",tag.ftcPose.x);
                telemetry.addData("y",tag.ftcPose.y);
                telemetry.addData("z",tag.ftcPose.z);
                telemetry.addData("roll",tag.ftcPose.roll);
                telemetry.addData("pitch",tag.ftcPose.pitch);
                telemetry.addData("yaw",tag.ftcPose.yaw);
                telemetry.addData("bearing",tag.ftcPose.bearing);
                telemetry.addData("elevation",tag.ftcPose.elevation);
                telemetry.addData("range",tag.ftcPose.range);
                telemetry.addData("tagID",tag.id);







            }
            telemetry.update();


        }

            leftFront = hardwareMap.dcMotor.get("leftFront");
            leftBack = hardwareMap.dcMotor.get("leftBack");
            rightFront = hardwareMap.dcMotor.get("rightFront");
            rightBack = hardwareMap.dcMotor.get("rightBack");

        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);



            servoClaw = hardwareMap.servo.get("servoClaw");

            sliderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
            sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Turn the motor back on when we are done
            sliderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            sliderMotorPos = sliderMotor.getCurrentPosition(); //

            sliderMotor = hardwareMap.dcMotor.get("sliderMotor");








            /*if (tag.id == 11 || tag.id == 12 || tag.id == 13 || tag.id == 14 || tag.id == 15 || tag.id == 16) {
                    //code to center the robot with the detected tag
                }*/





    }


}
