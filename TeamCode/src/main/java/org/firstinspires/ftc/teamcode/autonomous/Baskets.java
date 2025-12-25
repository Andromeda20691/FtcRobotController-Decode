package org.firstinspires.ftc.teamcode.autonomous;

import androidx.annotation.NonNull;

//For RR
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;


//Normal robot import
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;



@Config
@Autonomous(name = "Baskets", group = "Autonomous")
public class Baskets extends LinearOpMode {

    public class Lift {
        private DcMotorEx sliderMotor;

        public Lift(HardwareMap hardwareMap) {
            sliderMotor = hardwareMap.get(DcMotorEx.class, "sliderMotor");
            sliderMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            sliderMotor.setDirection(DcMotorEx.Direction.REVERSE);
            sliderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public class LiftUpBar2 implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    sliderMotor.setPower(1);
                    initialized = true;
                }

                double pos = sliderMotor.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 1050.0) {
                    return true;
                } else {
                    sliderMotor.setPower(0);
                    return false;
                }
            }
        }

        public Action liftUpBar2() {
            return new LiftUpBar2();
        }

        public class LiftDown implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    sliderMotor.setPower(-0.6);
                    initialized = true;
                }

                double pos = sliderMotor.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 0) {
                    return true;
                } else {
                    sliderMotor.setPower(0);
                    return false;
                }
            }
        }

        public Action liftDown() {
            return new LiftDown();
        }
        public class LiftDownUnclip implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    sliderMotor.setPower(-0.6);
                    initialized = true;
                }

                double pos = sliderMotor.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 300) {
                    return true;
                } else {
                    sliderMotor.setPower(0);
                    return false;
                }
            }
        }

        public Action liftDownUnclip() {
            return new LiftDownUnclip();
        }
    }


    public class LiftTilt {
        private DcMotorEx sliderArmLeftMotor;
        private DcMotorEx sliderArmRightMotor;

        public LiftTilt(HardwareMap hardwareMap) {
            sliderArmLeftMotor = hardwareMap.get(DcMotorEx.class, "sliderArmLeftMotor");
            sliderArmLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            sliderArmLeftMotor.setDirection(DcMotorEx.Direction.FORWARD);
            sliderArmLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderArmLeftMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));

            sliderArmRightMotor = hardwareMap.get(DcMotorEx.class, "sliderArmRightMotor");
            sliderArmRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            sliderArmRightMotor.setDirection(DcMotorEx.Direction.REVERSE);
            sliderArmRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderArmRightMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        }

        public class LiftTiltUp implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                sliderArmLeftMotor.setPower(0.5);
                sliderArmRightMotor.setPower(0.5);

                double pos11 = sliderArmLeftMotor.getCurrentPosition();
                double pos22 = sliderArmRightMotor.getCurrentPosition();
                packet.put("liftPos1", pos11);
                packet.put("liftPos2", pos22);
                if (pos11 > 0 || pos22 > 0) {
                    return true;
                } else {
                    sliderArmLeftMotor.setPower(0);
                    sliderArmRightMotor.setPower(0);
                    return false;
                }
            }
        }

        public Action liftTiltUp() {
            return new LiftTiltUp();
        }

        public class LiftTiltDown implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                sliderArmLeftMotor.setPower(-0.5);
                sliderArmRightMotor.setPower(-0.5);

                double pos1 = sliderArmLeftMotor.getCurrentPosition();
                double pos2 = sliderArmRightMotor.getCurrentPosition();
                packet.put("liftPos1", pos1);
                packet.put("liftPos2", pos2);
                if (pos1 > -400 || pos2 > -400) {
                    return true;
                } else {
                    sliderArmLeftMotor.setPower(0);
                    sliderArmRightMotor.setPower(0);
                    return false;
                }
            }
        }

        public Action liftTiltDown() {
            return new LiftTiltDown();
        }
    }

    public class Claw1 {
        private Servo servoClaw1;

        public Claw1(HardwareMap hardwareMap) {
            servoClaw1 = hardwareMap.get(Servo.class, "servoClaw1");
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                servoClaw1.setPosition(0.35);
                return false;
            }
        }

        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                servoClaw1.setPosition(0.6);
                return false;
            }
        }

        public Action openClaw() {
            return new OpenClaw();
        }
    }

    public class Claw2 {
        private Servo servoClaw2;

        public Claw2(HardwareMap hardwareMap) {
            servoClaw2 = hardwareMap.get(Servo.class, "servoClaw2");
        }

        public class TiltUpClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                servoClaw2.setPosition(0.4);
                return false;
            }
        }

        public Action tiltUpClaw() {
            return new TiltUpClaw();
        }

        public class TiltMidClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                servoClaw2.setPosition(0.3);
                return false;
            }
        }

        public Action tiltMidClaw() {
            return new TiltMidClaw();
        }

        public class TiltDownClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                servoClaw2.setPosition(0);
                return false;
            }
        }

        public Action tiltDownClaw() {
            return new TiltDownClaw();
        }
    }

    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(9, 54.3, Math.toRadians(-90));
        Pose2d clipPos = new Pose2d(9, 26, Math.toRadians(-90));
        Pose2d clipLeavePos = new Pose2d(9, 32, Math.toRadians(-90));
        Pose2d HangPos1 = new Pose2d(35, 32, Math.toRadians(-90));
        Pose2d HangPos2 = new Pose2d(35, 3, Math.toRadians(-90));
        Pose2d HangPos3Rot = new Pose2d(29.6, 3, Math.toRadians(180));
        Pose2d HangPos4 = new Pose2d(20, 3, Math.toRadians(180));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Claw1 servoClaw1 = new Claw1(hardwareMap);
        Claw2 servoClaw2 = new Claw2(hardwareMap);
        Lift sliderMotor = new Lift(hardwareMap);
        LiftTilt sliderArmLeftMotor = new LiftTilt(hardwareMap);
        LiftTilt sliderArmRightMotor = new LiftTilt(hardwareMap);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(clipPos.position.x, clipPos.position.y));

        TrajectoryActionBuilder tab2 = drive.actionBuilder(clipPos)
                .strafeTo(new Vector2d(clipLeavePos.position.x, clipLeavePos.position.y));


        TrajectoryActionBuilder tab3 = drive.actionBuilder(clipLeavePos)
                .strafeTo(new Vector2d(HangPos1.position.x, HangPos1.position.y));

        TrajectoryActionBuilder tab4 = drive.actionBuilder(HangPos1)
                .strafeTo(new Vector2d(HangPos2.position.x, HangPos2.position.y));

        TrajectoryActionBuilder tab5 = drive.actionBuilder(HangPos2)
                .turnTo(-135);

        TrajectoryActionBuilder tab6 = drive.actionBuilder(HangPos3Rot)
                .strafeTo(new Vector2d(HangPos4.position.x, HangPos4.position.y));



        Actions.runBlocking(servoClaw1.closeClaw());


        waitForStart();

        if (isStopRequested()) return;
        Actions.runBlocking(servoClaw2.tiltDownClaw());

        Actions.runBlocking(
                tab1.build()
        );
        Actions.runBlocking(
                sliderMotor.liftUpBar2()
        );
        sleep(200);
        Actions.runBlocking(
                sliderArmLeftMotor.liftTiltDown()
        );
        Actions.runBlocking(
                sliderArmRightMotor.liftTiltDown()
        );
        Actions.runBlocking(
                sliderMotor.liftDownUnclip()
        );
        Actions.runBlocking(
                servoClaw1.openClaw()
        );
        sleep(200);
        Actions.runBlocking(
                tab2.build()
        );
        Actions.runBlocking(
                tab3.build()
        );
        Actions.runBlocking(
                tab4.build()
        );
        Actions.runBlocking(
                tab5.build()
        );
        Actions.runBlocking(
                sliderMotor.liftUpBar2()
        );
        Actions.runBlocking(
                tab6.build()
        );
        Actions.runBlocking(
                sliderArmLeftMotor.liftTiltDown()
        );
        Actions.runBlocking(
                sliderArmRightMotor.liftTiltDown()
        );






    }
}

