package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "SafeHang", group = "Autonomous")
public class SafeHang extends LinearOpMode {
    private double speedMultiplier = 1;
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    public double leftFrontPos;
    public double rightFrontPos;
    public double leftBackPos;
    public double rightBackPos;

    private DcMotorEx sliderMotor;
    private DcMotorEx sliderArmLeftMotor;
    private DcMotorEx sliderArmRightMotor;

    public double sliderMotorPos;
    public double sliderMotorVelocity;
    public double sliderArmLeftMotorVelocity;
    public double sliderArmRightMotorVelocity;
    private double denominator = 0;
    private double frontLeftPower = 0, backLeftPower = 0, frontRightPower = 0, backRightPower = 0;
    private double y = 0, x = 0, rx = 0;


    private int bar2 = 6800;
    private int basket1 = 2000;
    private int basket2 = 8400;
    private int sliderArmUpPos = 2000;
    private int sliderArmDownPos = 4000;
    private int sliderArmPickup = -1330;
    private int sliderArmDrop = -1100;

    private Servo servoClaw1;
    private Servo servoClaw2;
    private CRServo crsArmRight;
    private CRServo crsArmLeft;

    private CRServo crServoTest;

    private double CLAW_OPEN_POSITION = 0.6;
    private double CLAW_CLOSED_POSITION = 0.381;
    private double Claw_Tilt_Down = -1;
    private double Claw_Tilt_Up = 0.3;
    private double sAMotorVeloLim = 480;
    private double sMotorVeloLim = -460;
    private double crsPower = 0;

    @Override
    public void runOpMode() {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");

        sliderMotor = hardwareMap.get(DcMotorEx.class, "sliderMotor");
        sliderArmLeftMotor = hardwareMap.get(DcMotorEx.class, "sliderArmLeftMotor");
        sliderArmRightMotor = hardwareMap.get(DcMotorEx.class, "sliderArmRightMotor");

        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sliderMotor.setDirection(DcMotor.Direction.REVERSE);
        sliderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sliderMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        sliderArmLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        sliderArmLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderArmLeftMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        sliderArmLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        sliderArmRightMotor.setDirection(DcMotor.Direction.REVERSE);
        sliderArmRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderArmRightMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        sliderArmRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        servoClaw1 = hardwareMap.servo.get("servoClaw1");
        servoClaw2 = hardwareMap.servo.get("servoClaw2");
        crServoTest = hardwareMap.crservo.get("crServoTest");
        crsArmLeft = hardwareMap.crservo.get("crsArmLeft");
                
        crsArmRight = hardwareMap.crservo.get("crsArmRight");

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        if (!gamepad1.ps) {
            sliderMotorVelocity = -500;
            sliderMotor.setPower(-0.5);
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while (sliderMotorVelocity <= sMotorVeloLim) {
                sliderMotorVelocity = sliderMotor.getVelocity();
                telemetry.addData("Staus", "slider is not in starting position");
                telemetry.update();
            }
            sliderMotor.setPower(0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sliderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


            sliderArmLeftMotor.setPower(0.28);
            sliderArmRightMotor.setPower(0.28);
            try {
                Thread.sleep(430);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sliderArmLeftMotorVelocity = 500;
            sliderArmRightMotorVelocity = 500;
            while (sliderArmLeftMotorVelocity > sAMotorVeloLim && sliderArmRightMotorVelocity > sAMotorVeloLim) {
                sliderArmLeftMotorVelocity = sliderArmLeftMotor.getVelocity();
                sliderArmRightMotorVelocity = sliderArmRightMotor.getVelocity();
                telemetry.addData("Staus", "slider Arm is not in starting position");
                telemetry.addData("Staus", "slider is in starting position");
                telemetry.update();
            }
            sliderArmLeftMotor.setPower(0);
            sliderArmRightMotor.setPower(0);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sliderArmLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderArmLeftMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
            sliderArmRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderArmRightMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));


            servoClaw1.setPosition(CLAW_CLOSED_POSITION);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            telemetry.addData("Status:", "Slider Arm is in starting position");
            telemetry.addData("Status:", "Slider is in starting position");
            telemetry.addData("Status:", "Claw1 init position");
            telemetry.addData("Status:", "Claw2 init position");
            telemetry.addData("Status:", "Initialized");
            waitForStart();

            if (opModeIsActive()) {
                servoClaw1.setPosition(CLAW_CLOSED_POSITION);

                MoveForward(0.3);
                sleep(400);
                StopDrive();
                MoveLeft(0.3);
                sleep(1050);
                StopDrive();
                MoveForward(0.3);
                sleep(1700);
                StopDrive();
                RotateRight(0.3);
                sleep(900);
                StopDrive();
                MoveForward(0.3);
                sleep(1800);
                StopDrive();
                sliderMotor.setPower(1);
                sleep(500);
                sliderMotor.setPower(0);
                sleep(100);
                sliderArmLeftMotor.setPower(-0.75);
                sliderArmRightMotor.setPower(-0.75);
                sleep(500);
                sliderArmLeftMotor.setPower(0);
                sliderArmRightMotor.setPower(0);
                sleep(20000);
            }
        }
    }
    public void MoveForward(double power) {
        leftFront.setPower(power);
        leftBack.setPower(power);
        rightFront.setPower(power);
        rightBack.setPower(power);
    }

    public void MoveBackward(double power) {
        leftFront.setPower(-power);
        leftBack.setPower(-power);
        rightFront.setPower(-power);
        rightBack.setPower(-power);
    }

    public void StopDrive() {
        MoveForward(0);
    }

    public void RotateLeft(double power) {
        rightBack.setPower(power);
        rightFront.setPower(power);
        leftFront.setPower(-power);
        leftBack.setPower(-power);
    }

    public void RotateRight(double power) {
        rightBack.setPower(-power);
        rightFront.setPower(-power);
        leftFront.setPower(power);
        leftBack.setPower(power);
    }

    public void MoveRight(double power) {
        rightBack.setPower(power);
        rightFront.setPower(-power);
        leftFront.setPower(power);
        leftBack.setPower(-power);
    }

    public void MoveLeft(double power) {
        rightBack.setPower(-power);
        rightFront.setPower(power);
        leftFront.setPower(-power);
        leftBack.setPower(power);
    }
}



