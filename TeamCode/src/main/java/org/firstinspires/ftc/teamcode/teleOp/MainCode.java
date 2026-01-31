package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="MainCode", group="Iterative Opmode")

public class MainCode extends OpMode
{

    private double speedMultiplier = 1;
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    private DcMotor outtakeTop;
    private DcMotor outtakeBottom;
    private DcMotor intakeMotor1;
    private DcMotor intakeMotor2;

    private Servo sorterServo;
    private Servo shooterServo;

    // private NormalizedColorSensor colorSensor;

    private double sorterPos1 = 1.0;
    private double sorterPos2 = 0.5;
    private double sorterPos3 = 0.0;
    private double sorterServoPos = 0.0;

    private double shooterMinPos = 1.0;
    private double shooterMaxPos = 0.4;
    private double shooterPos3 = 0.8;
    private double shooterPos4 = 0.7;
    private double shooterPos5 = 0.6;
    private double shooterPos6 = 0.5;
    private double shooterPos7 = 0.4;

    private double shooterCurrentPos = 1.0;
    private boolean lastY = false;
    private boolean lastA = false;
    private final double shooterStep = 0.1;

    boolean intakeOn = false;
    boolean outtakeOn = false;
    boolean lastx = false;
    boolean lastb = false;
    boolean intakeActivated = false;
    private double denominator = 0;
    private double frontLeftPower =0, backLeftPower =0, frontRightPower =0, backRightPower =0;
    private double y =0, x =0, rx =0;




    @Override
    public void init() {
        leftFront = hardwareMap.dcMotor.get("frontLeft");
        leftBack = hardwareMap.dcMotor.get("backLeft");
        rightFront = hardwareMap.dcMotor.get("frontRight");
        rightBack = hardwareMap.dcMotor.get("backRight");

        outtakeBottom = hardwareMap.dcMotor.get("outtakeBottom");
        outtakeTop = hardwareMap.dcMotor.get("outtakeTop");
        intakeMotor1 = hardwareMap.dcMotor.get("intakeMotor1");
        intakeMotor2 = hardwareMap.dcMotor.get("intakeMotor2");

        sorterServo = hardwareMap.servo.get("sorterServo");
        shooterServo = hardwareMap.servo.get("shooterServo");


        // colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        // colorSensor.setGain(40);

        // TODO: 1/9/2026 here chage the ction of the outake motors they are prob wronge because i didnt have time to check them
        outtakeTop.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeBottom.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor2.setDirection(DcMotorSimple.Direction.FORWARD);

        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {

        if (gamepad1.right_bumper) {
            speedMultiplier = 0.25;
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

        // NormalizedRGBA colors = colorSensor.getNormalizedColors();

        sorterServoPos = sorterServo.getPosition();


        boolean outtakeToggleButton = gamepad1.b;
        if (outtakeToggleButton && !lastb) {
            outtakeOn = !outtakeOn;
        }
        lastb = outtakeToggleButton;

        if (outtakeOn) {
            outtakeTop.setPower(1);
            outtakeBottom.setPower(1);
        } else {
            outtakeTop.setPower(0);
            outtakeBottom.setPower(0);
        }
        boolean yPressed = gamepad1.y;
        boolean aPressed = gamepad1.a;


        /* MOVE DOWN (Y) */
        if (yPressed && !lastY) {
            shooterCurrentPos -= shooterStep;

            if (shooterCurrentPos < shooterMinPos) {
                shooterCurrentPos = shooterMinPos;
            }

            shooterServo.setPosition(shooterCurrentPos);
        }

        /* MOVE UP (A) */
        if (aPressed && !lastA) {
            shooterCurrentPos += shooterStep;

            if (shooterCurrentPos > shooterMaxPos) {
                shooterCurrentPos = shooterMaxPos;
            }

            shooterServo.setPosition(shooterCurrentPos);
        }

        /*
        lastY = yPressed;
        lastA = aPressed;


        boolean intakeToggleButton = gamepad1.x;
        if (intakeToggleButton && !lastx) {
            intakeOn = !intakeOn;
        }
        lastx = intakeToggleButton;

        double manualPower = -gamepad1.right_stick_y;

        if (Math.abs(manualPower) < 0.05) {
            manualPower = 0;
        }

        double finalIntakePower;

        if (manualPower != 0) {
            finalIntakePower = manualPower;
        } else if (intakeOn) {
            finalIntakePower = 1;
        } else {
            finalIntakePower = 0;
        }

        intakeMotor1.setPower(finalIntakePower);*/

        double stickIntakePower = -gamepad1.right_stick_y;

        intakeMotor1.setPower(stickIntakePower);
        intakeMotor2.setPower(stickIntakePower);

        if (gamepad1.dpad_down && !intakeActivated) {

            intakeActivated = true;
            displayTelemetry();

            intakeMotor1.setPower(1);
            intakeMotor2.setPower(1);

            try {

                Thread.sleep(250);

            } catch (InterruptedException e) {

                telemetry.addLine("An exception has occurred within the code :(");

            }

        } else if (gamepad1.dpad_down && intakeActivated) {

            intakeActivated = false;
            displayTelemetry();

            intakeMotor1.setPower(0);
            intakeMotor2.setPower(0);

            try {

                Thread.sleep(250);

            } catch (InterruptedException e) {

                telemetry.addLine("An exception has occurred within the code :(");

            }

        }

        if (gamepad1.dpad_up && !intakeActivated) {

            sorterServo.setPosition(sorterPos2);

            intakeActivated = true;
            displayTelemetry();

            intakeMotor1.setPower(-0.5);
            intakeMotor2.setPower(-0.5);

            try {

                Thread.sleep(250);

            } catch (InterruptedException e) {

                telemetry.addLine("An exception has occurred within the code :(");

            }

        } else if (gamepad1.dpad_up && intakeActivated) {

            sorterServo.setPosition(sorterPos2);

            intakeActivated = false;
            displayTelemetry();

            intakeMotor1.setPower(0);
            intakeMotor2.setPower(0);

            try {

                Thread.sleep(250);

            } catch (InterruptedException e) {

                telemetry.addLine("An exception has occured within the code :(");

            }

        }

        if (gamepad1.dpad_left) {
            sorterServo.setPosition(sorterPos1);
        }
        if (gamepad1.dpad_right) {
            sorterServo.setPosition(sorterPos3);
        }

        displayTelemetry();

    }

    public void displayTelemetry(){

        telemetry.addData("leftFrontPower: ", leftFront.getPower());
        telemetry.addData("leftBackPower: ", leftBack.getPower());
        telemetry.addData("rightBackPower: ", rightBack.getPower());
        telemetry.addData("rightFrontPower: ", rightFront.getPower());
        telemetry.addLine();
        telemetry.addData("Sorter servo Pos: ", sorterServo.getPosition());
        telemetry.addData("Sorter servo varPos: ", sorterServoPos);
        telemetry.addLine();
        // telemetry.addData("color sensor gain: ", colorSensor.getGain());
        // telemetry.addData("color sensor alpha: ", colors.alpha);
        // telemetry.addData("color sensor blue: ", colors.blue);
        // telemetry.addData("color sensor red: ", colors.red);
        // telemetry.addData("color sensor green: ", colors.green);
        // telemetry.addLine();
        telemetry.addData("intake activated: ", intakeActivated);

        telemetry.update();

    }

}