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


@TeleOp(name="Matchcode", group="Iterative Opmode")
@Disabled
public class MatchCode extends OpMode
{

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
    private  DcMotorEx sliderArmLeftMotor;
    private  DcMotorEx sliderArmRightMotor;


    public double sliderMotorPos;
    public double sliderMotorVelocity;
    public double sliderArmLeftMotorVelocity;
    public double sliderArmRightMotorVelocity;
    private double denominator = 0;
    private double frontLeftPower =0, backLeftPower =0, frontRightPower =0, backRightPower =0;
    private double y =0, x =0, rx =0;


    private int bar2 = 6800;
    private int basket1 = 2000;
    private int basket2 = 8400;
    private int sliderArmUpPos = 2000;
    private int sliderArmDownPos = 4000;
    private int sliderArmPickup = -1330;
    private int sliderArmDrop = -300;

    private Servo servoClaw1;
    private Servo servoClaw2;
    private CRServo crsArmRight;
    private CRServo crsArmLeft;

    private CRServo crServoTest;

    private double CLAW_OPEN_POSITION = 1;
    private double CLAW_CLOSED_POSITION = -1;
    private double Claw_Tilt_Down = 0;
    private double Claw_Tilt_Mid = 0.28;
    private double Claw_Tilt_Up = 0.4;
    private double sAMotorVeloLim = 480;
    private double sMotorVeloLim = -210;
    private double crsPower = 0;

    /*private double Claw_Tilt_Down = -1;
    private double Claw_Tilt_Mid = 0.3;
    private double Claw_Tilt_Up = 0.40;*/


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
        //sliderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sliderMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        sliderArmLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        //sliderArmLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderArmLeftMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        sliderArmLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        sliderArmRightMotor.setDirection(DcMotor.Direction.REVERSE);
        //sliderArmRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderArmRightMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        sliderArmRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        servoClaw1 = hardwareMap.servo.get("servoClaw1");
        servoClaw2 = hardwareMap.servo.get("servoClaw2");
        crServoTest = hardwareMap.crservo.get("crServoTest");
        crsArmLeft = hardwareMap.crservo.get("crsArmLeft");
        crsArmRight = hardwareMap.crservo.get("crsArmRight");

        servoClaw2.scaleRange(-1,1);

        telemetry.addData("Status", "Initialized");
        telemetry.update();




        /*if(!gamepad1.ps) {

            servoClaw1.setPosition(CLAW_CLOSED_POSITION);

            sliderArmLeftMotor.setPower(0.4);
            sliderArmRightMotor.setPower(0.4);
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



            sliderArmLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderArmLeftMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
            sliderArmRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderArmRightMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));



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

        }*/
    }

    @Override
    public void loop() {

        sliderMotorPos = sliderMotor.getCurrentPosition();

        double sliderArmRightPos = sliderArmRightMotor.getCurrentPosition();
        double sliderArmLeftPos = sliderArmLeftMotor.getCurrentPosition();

        double servoClaw2Pos = servoClaw2.getPosition();

        if(gamepad1.right_bumper) {
            speedMultiplier = 0.25;
        } else {
            speedMultiplier = 1;
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


        crServoTest.setPower(gamepad2.left_stick_x);

        crsPower = gamepad2.left_stick_y;
        crsArmLeft.setPower(crsPower);
        crsArmRight.setPower(crsPower);

        double sliderArmPower = (gamepad2.right_stick_y / 2);
        if (Math.abs(sliderArmPower) > 0.05) {
                sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                sliderArmLeftMotor.setPower(sliderArmPower);
                sliderArmRightMotor.setPower(sliderArmPower);
        } else {
                sliderArmLeftMotor.setPower(0);
                sliderArmRightMotor.setPower(0);
                synchronized (this) {
                    try {
                        this.wait(100);
                    } catch (InterruptedException e) {
                    }
                    sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
        }


        if (gamepad1.b) {
            servoClaw1.setPosition(CLAW_OPEN_POSITION);
        }else if (gamepad1.a) {
            servoClaw1.setPosition(CLAW_CLOSED_POSITION);
        }else if (gamepad2.x) {
            servoClaw2.setPosition(Claw_Tilt_Down);
        }else if (gamepad2.b){
            servoClaw2.setPosition(Claw_Tilt_Up);
        }else if (gamepad2.y){
            servoClaw2.setPosition(Claw_Tilt_Mid);
        }

        /*if(gamepad2.right_stick_button){
            sliderMotor.setTargetPosition(0);
            sliderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderMotor.setPower(1);
            while (sliderMotor.isBusy()){
                if(gamepad1.right_bumper) {
                speedMultiplier = 0.3;
            } else if(gamepad1.left_bumper){
                speedMultiplier = 0.2;
            } else {
                speedMultiplier = 1;
            }
                y = -gamepad1.left_stick_y;
                x = gamepad1.left_stick_x;
                rx = gamepad1.right_stick_x;
                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                frontLeftPower = (y + x + rx) / denominator * speedMultiplier;
                backLeftPower = (y - x + rx) / denominator * speedMultiplier;
                frontRightPower = (y - x - rx) / denominator * speedMultiplier;
                backRightPower = (y + x - rx) / denominator * speedMultiplier;
                leftFront.setPower(frontLeftPower);
                leftBack.setPower(backLeftPower);
                rightFront.setPower(frontRightPower);
                rightBack.setPower(backRightPower);}
            sliderMotor.setPower(0);

            sliderArmLeftMotor.setTargetPosition(sliderArmPickup);
            sliderArmRightMotor.setTargetPosition(sliderArmPickup);
            sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderArmLeftMotor.setPower(0.5);
            sliderArmRightMotor.setPower(0.5);
            while (sliderArmLeftMotor.isBusy() && sliderArmRightMotor.isBusy()){
                if(gamepad1.right_bumper) {
                    speedMultiplier = 0.3;
                } else if(gamepad1.left_bumper){
                    speedMultiplier = 0.2;
                } else {
                    speedMultiplier = 1;
                }
                y = -gamepad1.left_stick_y;
                x = gamepad1.left_stick_x;
                rx = gamepad1.right_stick_x;
                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                frontLeftPower = (y + x + rx) / denominator * speedMultiplier;
                backLeftPower = (y - x + rx) / denominator * speedMultiplier;
                frontRightPower = (y - x - rx) / denominator * speedMultiplier;
                backRightPower = (y + x - rx) / denominator * speedMultiplier;
                leftFront.setPower(frontLeftPower);
                leftBack.setPower(backLeftPower);
                rightFront.setPower(frontRightPower);
                rightBack.setPower(backRightPower);
            }
            sliderArmLeftMotor.setPower(0);
            sliderArmRightMotor.setPower(0);

            servoClaw2.setPosition(Claw_Tilt_Up);
        }else */
        if(gamepad1.dpad_down){

            frontLeftPower = 0;
            frontRightPower = 0;
            backLeftPower = 0;
            backRightPower = 0;
            leftFront.setPower(frontLeftPower);
            leftBack.setPower(backLeftPower);
            rightFront.setPower(frontRightPower);
            rightBack.setPower(backRightPower);
            servoClaw2.setPosition(Claw_Tilt_Down);
            sliderMotor.setTargetPosition(840);
            sliderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderMotor.setPower(1);
            while (sliderMotor.isBusy()){}
            sliderMotor.setPower(0);

            sliderArmLeftMotor.setTargetPosition(sliderArmDrop);
            sliderArmRightMotor.setTargetPosition(sliderArmDrop);
            sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderArmLeftMotor.setPower(1);
            sliderArmRightMotor.setPower(1);
            while (sliderArmLeftMotor.isBusy() && sliderArmRightMotor.isBusy()){}

            sliderMotor.setTargetPosition(280);
            sliderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderMotor.setPower(1);
            while (sliderMotor.isBusy()){}
            sliderMotor.setPower(0);
            servoClaw1.setPosition(CLAW_OPEN_POSITION);

            sliderArmLeftMotor.setPower(0);
            sliderArmRightMotor.setPower(0);
        }


        if (gamepad2.ps) {
            sliderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sliderArmRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderArmRightMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
            sliderArmLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderArmLeftMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));

        }


        /*if (gamepad2.dpad_right) {
            sliderMotor.setTargetPosition(bar2);
            sliderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderMotor.setPower(1);
            while (sliderMotor.isBusy()){
                if(gamepad1.right_bumper) {
                    speedMultiplier = 0.3;
                } else {
                    speedMultiplier = 1;
                }
                y = -gamepad1.left_stick_y;
                x = gamepad1.left_stick_x;
                rx = gamepad1.right_stick_x;
                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                frontLeftPower = (y + x + rx) / denominator * speedMultiplier;
                backLeftPower = (y - x + rx) / denominator * speedMultiplier;
                frontRightPower = (y - x - rx) / denominator * speedMultiplier;
                backRightPower = (y + x - rx) / denominator * speedMultiplier;
                leftFront.setPower(frontLeftPower);
                leftBack.setPower(backLeftPower);
                rightFront.setPower(frontRightPower);
                rightBack.setPower(backRightPower);

                sliderArmPower = (gamepad2.right_stick_y / 2);
                if (Math.abs(sliderArmPower) > 0.05) {
                    sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderArmLeftMotor.setPower(sliderArmPower);
                    sliderArmRightMotor.setPower(sliderArmPower);
                } else {
                    sliderArmLeftMotor.setPower(0);
                    sliderArmRightMotor.setPower(0);
                    synchronized (this) {
                        try {
                            this.wait(100);
                        } catch (InterruptedException e) {
                        }
                        sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }
                }

                double up = gamepad2.right_trigger;
                double down = gamepad2.left_trigger;
                if(sliderMotorPos > 15900) {
                    up = 0;
                }else if (sliderMotorPos <= 0){
                    down = 0;
                }

                double sliderMotorPower = up - down;
                if (Math.abs(sliderMotorPower) > 0.05) {
                    sliderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderMotor.setPower(sliderMotorPower);
                } else {
                    sliderMotor.setPower(0);
                    sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }

                if (gamepad1.b || gamepad2.b) {
                    servoClaw1.setPosition(CLAW_OPEN_POSITION);
                }else if (gamepad1.a || gamepad2.a) {
                    servoClaw1.setPosition(CLAW_CLOSED_POSITION);
                }else if (gamepad2.x) {
                    servoClaw2.setPosition(Claw_Tilt_Down);
                }else if (gamepad2.y){
                    servoClaw2.setPosition(Claw_Tilt_Up);
                }
            }
            sliderMotor.setPower(0);
        }else if (gamepad2.dpad_left) {
            sliderMotor.setTargetPosition(basket1);
            sliderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderMotor.setPower(1);
            while (sliderMotor.isBusy()){
                if(gamepad1.right_bumper) {
                    speedMultiplier = 0.3;
                } else {
                    speedMultiplier = 1;
                }
                y = -gamepad1.left_stick_y;
                x = gamepad1.left_stick_x;
                rx = gamepad1.right_stick_x;
                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                frontLeftPower = (y + x + rx) / denominator * speedMultiplier;
                backLeftPower = (y - x + rx) / denominator * speedMultiplier;
                frontRightPower = (y - x - rx) / denominator * speedMultiplier;
                backRightPower = (y + x - rx) / denominator * speedMultiplier;
                leftFront.setPower(frontLeftPower);
                leftBack.setPower(backLeftPower);
                rightFront.setPower(frontRightPower);
                rightBack.setPower(backRightPower);

                sliderArmPower = (gamepad2.right_stick_y / 2);
                if (Math.abs(sliderArmPower) > 0.05) {
                    sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderArmLeftMotor.setPower(sliderArmPower);
                    sliderArmRightMotor.setPower(sliderArmPower);
                } else {
                    sliderArmLeftMotor.setPower(0);
                    sliderArmRightMotor.setPower(0);
                    synchronized (this) {
                        try {
                            this.wait(100);
                        } catch (InterruptedException e) {
                        }
                        sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }
                }

                double up = gamepad2.right_trigger;
                double down = gamepad2.left_trigger;
                if(sliderMotorPos > 15900) {
                    up = 0;
                }else if (sliderMotorPos <= 0){
                    down = 0;
                }

                double sliderMotorPower = up - down;
                if (Math.abs(sliderMotorPower) > 0.05) {
                    sliderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderMotor.setPower(sliderMotorPower);
                } else {
                    sliderMotor.setPower(0);
                    sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }

                if (gamepad1.b || gamepad2.b) {
                    servoClaw1.setPosition(CLAW_OPEN_POSITION);
                }else if (gamepad1.a || gamepad2.a) {
                    servoClaw1.setPosition(CLAW_CLOSED_POSITION);
                }else if (gamepad2.x) {
                    servoClaw2.setPosition(Claw_Tilt_Down);
                }else if (gamepad2.y){
                    servoClaw2.setPosition(Claw_Tilt_Up);
                }
            }
            sliderMotor.setPower(0);
        }else if (gamepad2.dpad_up) {
            sliderMotor.setTargetPosition(basket2);
            sliderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderMotor.setPower(1);
            while (sliderMotor.isBusy()){
                if(gamepad1.right_bumper) {
                    speedMultiplier = 0.3;
                } else {
                    speedMultiplier = 1;
                }
                y = -gamepad1.left_stick_y;
                x = gamepad1.left_stick_x;
                rx = gamepad1.right_stick_x;
                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                frontLeftPower = (y + x + rx) / denominator * speedMultiplier;
                backLeftPower = (y - x + rx) / denominator * speedMultiplier;
                frontRightPower = (y - x - rx) / denominator * speedMultiplier;
                backRightPower = (y + x - rx) / denominator * speedMultiplier;
                leftFront.setPower(frontLeftPower);
                leftBack.setPower(backLeftPower);
                rightFront.setPower(frontRightPower);
                rightBack.setPower(backRightPower);

                sliderArmPower = (gamepad2.right_stick_y / 2);
                if (Math.abs(sliderArmPower) > 0.05) {
                    sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderArmLeftMotor.setPower(sliderArmPower);
                    sliderArmRightMotor.setPower(sliderArmPower);
                } else {
                    sliderArmLeftMotor.setPower(0);
                    sliderArmRightMotor.setPower(0);
                    synchronized (this) {
                        try {
                            this.wait(100);
                        } catch (InterruptedException e) {
                        }
                        sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }
                }

                double up = gamepad2.right_trigger;
                double down = gamepad2.left_trigger;
                if(sliderMotorPos > 15900) {
                    up = 0;
                }else if (sliderMotorPos <= 0){
                    down = 0;
                }

                double sliderMotorPower = up - down;
                if (Math.abs(sliderMotorPower) > 0.05) {
                    sliderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderMotor.setPower(sliderMotorPower);
                } else {
                    sliderMotor.setPower(0);
                    sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }

                if (gamepad1.b || gamepad2.b) {
                    servoClaw1.setPosition(CLAW_OPEN_POSITION);
                }else if (gamepad1.a || gamepad2.a) {
                    servoClaw1.setPosition(CLAW_CLOSED_POSITION);
                }else if (gamepad2.x) {
                    servoClaw2.setPosition(Claw_Tilt_Down);
                }else if (gamepad2.y){
                    servoClaw2.setPosition(Claw_Tilt_Up);
                }
            }
            sliderMotor.setPower(0);
        }else if(gamepad2.dpad_down){
            sliderMotor.setTargetPosition(0);
            sliderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sliderMotor.setPower(1);
            while (sliderMotor.isBusy()){
                if(gamepad1.right_bumper) {
                    speedMultiplier = 0.3;
                } else {
                    speedMultiplier = 1;
                }
                y = -gamepad1.left_stick_y;
                x = gamepad1.left_stick_x;
                rx = gamepad1.right_stick_x;
                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                frontLeftPower = (y + x + rx) / denominator * speedMultiplier;
                backLeftPower = (y - x + rx) / denominator * speedMultiplier;
                frontRightPower = (y - x - rx) / denominator * speedMultiplier;
                backRightPower = (y + x - rx) / denominator * speedMultiplier;
                leftFront.setPower(frontLeftPower);
                leftBack.setPower(backLeftPower);
                rightFront.setPower(frontRightPower);
                rightBack.setPower(backRightPower);

                sliderArmPower = (gamepad2.right_stick_y / 2);
                if (Math.abs(sliderArmPower) > 0.05) {
                    sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderArmLeftMotor.setPower(sliderArmPower);
                    sliderArmRightMotor.setPower(sliderArmPower);
                } else {
                    sliderArmLeftMotor.setPower(0);
                    sliderArmRightMotor.setPower(0);
                    synchronized (this) {
                        try {
                            this.wait(100);
                        } catch (InterruptedException e) {
                        }
                        sliderArmLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        sliderArmRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }
                }

                double up = gamepad2.right_trigger;
                double down = gamepad2.left_trigger;
                if(sliderMotorPos > 15900) {
                    up = 0;
                }else if (sliderMotorPos <= 0){
                    down = 0;
                }

                double sliderMotorPower = up - down;
                if (Math.abs(sliderMotorPower) > 0.05) {
                    sliderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    sliderMotor.setPower(sliderMotorPower);
                } else {
                    sliderMotor.setPower(0);
                    sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }

                if (gamepad1.b || gamepad2.b) {
                    servoClaw1.setPosition(CLAW_OPEN_POSITION);
                }else if (gamepad1.a || gamepad2.a) {
                    servoClaw1.setPosition(CLAW_CLOSED_POSITION);
                }else if (gamepad2.x) {
                    servoClaw2.setPosition(Claw_Tilt_Down);
                }else if (gamepad2.y){
                    servoClaw2.setPosition(Claw_Tilt_Up);
                }
            }
            sliderMotor.setPower(0);
        }*/

        double up = gamepad2.right_trigger;
        double down = gamepad2.left_trigger;
        if(sliderMotorPos > 3100) {
            up = 0;
        }else if (sliderMotorPos <= 0){
            down = 0;
        }

        if(sliderArmLeftPos < -10 || sliderArmRightPos < -10){
            if(sliderMotorPos > 1260) {
                up = 0;
            }else if (sliderMotorPos <= 0){
                down = 0;
            }
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

        telemetry.addData("Slider Motor Pos:",sliderMotorPos);
        telemetry.addLine();
        telemetry.addData("sliderArmLeftMotorPos:",sliderArmRightMotor.getCurrentPosition());
        telemetry.addData("sliderArmRightMotorPos:",sliderArmLeftMotor.getCurrentPosition());
        telemetry.addLine();
        telemetry.addData("Claw2Pos",servoClaw2Pos);
        telemetry.addData("CrTestPos",crServoTest.getPower());
        telemetry.addLine();
        telemetry.addData("CrTestPos",leftFront.getPower());
        telemetry.addData("CrTestPos",leftBack.getPower());
        telemetry.addData("CrTestPos",rightBack.getPower());
        telemetry.addData("CrTestPos",rightFront.getPower());

        telemetry.update();
    }
}
