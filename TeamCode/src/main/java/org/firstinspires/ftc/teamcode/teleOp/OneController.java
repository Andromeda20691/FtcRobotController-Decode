package org.firstinspires.ftc.teamcode.teleOp;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PwmControl;


@TeleOp(name="OneController", group="Iterative Opmode")
@Disabled

public class OneController extends OpMode
{

    private double speedMultiplier = 1;
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    private DcMotorEx sliderMotor;
    private  DcMotorEx sliderArmLeftMotor;
    private  DcMotorEx sliderArmRightMotor;


    public double sliderMotorPos;
    public double sliderMotorVelocity;
    public double sliderArmLeftMotorVelocity;
    public double sliderArmRightMotorVelocity;
    private double denominator = 0;
    private double frontLeftPower =0, backLeftPower =0, frontRightPower =0, backRightPower =0;
    private double y =0, x =0, rx =0;


    private int sliderArmPickup = -1330;

    private Servo servoClaw1;
    private Servo servoClaw2;

    private CRServo crServoTest;

    private double CLAW_OPEN_POSITION = 0.6;
    private double CLAW_CLOSED_POSITION = 0.35;
    private double Claw_Tilt_Down = 0;
    private double Claw_Tilt_Mid = 0.28;
    private double Claw_Tilt_Up = 0.4;
    private double sAMotorVeloLim = 480;
    private double sMotorVeloLim = -210;


    @Override
    public void init() {
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

        telemetry.addData("Status", "Initialized");
        telemetry.update();

    }
    @Override
    public void loop() {

        sliderMotorPos = sliderMotor.getCurrentPosition();


        if(gamepad1.right_bumper) {
            speedMultiplier = 0.25;
        } else {
            speedMultiplier = 0.5;
        }
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator * speedMultiplier;
        double backLeftPower = (y - x + rx) / denominator * speedMultiplier;
        double frontRightPower = (y - x - rx) / denominator * speedMultiplier;
        double backRightPower = (y + x - rx) / denominator * speedMultiplier;
        leftFront.setPower(frontLeftPower);
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);

        double up = gamepad1.right_trigger;
        double down = gamepad1.left_trigger;
        if(sliderMotorPos > 3100) {
            up = 0;
        }else if (sliderMotorPos <= 0) {
            down = 0;
        }

        double sliderMotorPower = up - down;
        if (Math.abs(sliderMotorPower) > 0.05) {
            sliderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            sliderMotor.setPower(sliderMotorPower);
        } else if(sliderMotorPos > 2800){
            sliderMotor.setPower(0.2);
        } else {
            sliderMotor.setPower(0);
            sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        if(gamepad1.dpad_down){
            leftFront.setPower(0);
            leftBack.setPower(0);
            rightFront.setPower(0);
            rightBack.setPower(0);
            servoClaw1.setPosition(CLAW_OPEN_POSITION);
            servoClaw2.setPosition(Claw_Tilt_Down);
            sliderMotor.setTargetPosition(0);
            sliderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderMotor.setPower(0.5);
            while(sliderMotor.isBusy()) {}
            sliderMotor.setPower(0);
            sliderArmLeftMotor.setTargetPosition(sliderArmPickup);
            sliderArmRightMotor.setTargetPosition(sliderArmPickup);
            sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderArmLeftMotor.setPower(0.5);
            sliderArmRightMotor.setPower(0.5);
            while (sliderArmLeftMotor.isBusy() && sliderArmRightMotor.isBusy()){
            }
            sliderArmLeftMotor.setPower(0);
            sliderArmRightMotor.setPower(0);
        } else if(gamepad1.dpad_up){
            leftFront.setPower(0);
            leftBack.setPower(0);
            rightFront.setPower(0);
            rightBack.setPower(0);
            servoClaw2.setPosition(Claw_Tilt_Up);
            sliderArmLeftMotor.setTargetPosition(0);
            sliderArmRightMotor.setTargetPosition(0);
            sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderArmLeftMotor.setPower(0.5);
            sliderArmRightMotor.setPower(0.5);
            while (sliderArmLeftMotor.isBusy() && sliderArmRightMotor.isBusy()){
            }
            sliderArmLeftMotor.setPower(0);
            sliderArmRightMotor.setPower(0);
            servoClaw2.setPosition(Claw_Tilt_Up);
        }


        if (gamepad1.b) {
            servoClaw1.setPosition(CLAW_OPEN_POSITION);
        }else if (gamepad1.a) {
            servoClaw1.setPosition(CLAW_CLOSED_POSITION);
        }

        if (gamepad1.ps) {
            sliderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sliderArmRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderArmRightMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
            sliderArmLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderArmLeftMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        }
        telemetry.addData("Slider Motor Pos:",sliderMotorPos);
        telemetry.addLine();
        telemetry.addData("sliderArmLeftMotorPos:",sliderArmRightMotor.getCurrentPosition());
        telemetry.addData("sliderArmRightMotorPos:",sliderArmLeftMotor.getCurrentPosition());
        telemetry.update();
    }
}
