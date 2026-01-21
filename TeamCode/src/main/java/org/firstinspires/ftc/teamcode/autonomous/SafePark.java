package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "SafePark", group = "Autonomous")
public class SafePark extends LinearOpMode {
    private double speedMultiplier = 1;
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    public double leftFrontPos;
    public double rightFrontPos;
    public double leftBackPos;
    public double rightBackPos;

    public double sliderMotorPos;
    public double sliderMotorVelocity;
    public double sliderArmLeftMotorVelocity;
    public double sliderArmRightMotorVelocity;
    private double denominator = 0;
    private double frontLeftPower = 0, backLeftPower = 0, frontRightPower = 0, backRightPower = 0;
    private double y = 0, x = 0, rx = 0;



    private double CLAW_OPEN_POSITION = 0.6;
    private double CLAW_CLOSED_POSITION = 0.381;
    private double Claw_Tilt_Down = -1;
    private double Claw_Tilt_Up = 0.3;
    private double sAMotorVeloLim = 480;
    private double sMotorVeloLim = -460;
    private double crsPower = 0;

    @Override
    public void runOpMode() {
        leftFront = hardwareMap.dcMotor.get("frontLeft");
        leftBack = hardwareMap.dcMotor.get("backLeft");
        rightFront = hardwareMap.dcMotor.get("frontRight");
        rightBack = hardwareMap.dcMotor.get("backRight");

        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        if (!gamepad1.ps) {
            telemetry.addData("Status:", "Initialized");
            waitForStart();

            if (opModeIsActive()) {
                MoveForward(1);
                sleep(500);
                StopDrive();
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



