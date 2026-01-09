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
    private DcMotor intakeMotor;

    private Servo sorterServo;

    private NormalizedColorSensor colorSensor;

    private double sorterPos1 = 1.0;
    private double sorterPos2 = 0.5;
    private double sorterPos3 = 0.0;

    private double sorterServoPos = 0.0;

    int sorterState = 0;
    boolean lastA = false;

    boolean intakeOn = false;
    boolean lastY = false;



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
        intakeMotor = hardwareMap.dcMotor.get("intakeMotor");

        sorterServo = hardwareMap.servo.get("sorterServo");

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        colorSensor.setGain(40);

        // TODO: 1/9/2026 here chage the ction of the outake motors they are prob wronge because i didnt have time to check them
        outtakeTop.setDirection(DcMotorSimple.Direction.FORWARD);
        outtakeBottom.setDirection(DcMotorSimple.Direction.REVERSE);


        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);


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
    }

    @Override
    public void loop() {

        if(gamepad1.right_bumper) {
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

        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        sorterServoPos = sorterServo.getPosition();


        if (gamepad2.a){
            outtakeTop.setPower(1);
            outtakeBottom.setPower(1);
        }else if (gamepad2.b){
            outtakeTop.setPower(0);
            outtakeBottom.setPower(0);
        }


        boolean intakeToggleButton = gamepad2.y;
        if (intakeToggleButton && !lastY) {
            intakeOn = !intakeOn;
        }
        lastY = intakeToggleButton;

        double manualPower = -gamepad2.right_stick_y;

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

        intakeMotor.setPower(finalIntakePower);



        boolean a = gamepad2.x;
        if (a && !lastA) {
            sorterState = (sorterState + 1) % 3;
        }
        switch (sorterState) {
            case 0:
                sorterServo.setPosition(sorterPos1);
                break;
            case 1:
                sorterServo.setPosition(sorterPos2);
                break;
            case 2:
                sorterServo.setPosition(sorterPos3);
                break;
        }
        lastA = a;
        

        if (gamepad2.dpad_left){
            sorterServo.setPosition(sorterPos1);
        }
        if (gamepad2.dpad_up){
            sorterServo.setPosition(sorterPos2);
        }
        if (gamepad2.dpad_right){
            sorterServo.setPosition(sorterPos3);
        }



        telemetry.addData("leftFrontPower",leftFront.getPower());
        telemetry.addData("leftBackPower",leftBack.getPower());
        telemetry.addData("rightBackPower",rightBack.getPower());
        telemetry.addData("rightFrontPower",rightFront.getPower());
        telemetry.addLine();
        telemetry.addData("Sorter servo Pos:",sorterServo.getPosition());
        telemetry.addData("Sorter servo varPos:",sorterServoPos);
        telemetry.addLine();
        telemetry.addData("color sensor gain:", colorSensor.getGain());
        telemetry.addData("color sensor alpha:", colors.alpha);
        telemetry.addData("color sensor blue:", colors.blue);
        telemetry.addData("color sensor red:", colors.red);
        telemetry.addData("color sensor green:", colors.green);

        telemetry.update();
    }
}