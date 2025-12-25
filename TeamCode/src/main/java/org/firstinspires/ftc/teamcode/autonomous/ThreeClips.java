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
@Autonomous(name = "3Clips", group = "Autonomous")
public class ThreeClips extends LinearOpMode {

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
                if (pos < 900.0) {
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
                if (pos > 350) {
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
                if (!initialized) {
                    sliderArmLeftMotor.setPower(0.5); // Move UP instead of down
                    sliderArmRightMotor.setPower(0.5);
                    initialized = true;
                }

                double pos1 = sliderArmLeftMotor.getCurrentPosition();
                double pos2 = sliderArmRightMotor.getCurrentPosition();
                packet.put("liftPos1", pos1);
                packet.put("liftPos2", pos2);

                if (pos1 < -1 || pos2 < -1) { // Stop when close to 0
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
                if (!initialized) {
                    sliderArmLeftMotor.setPower(-0.5);
                    sliderArmRightMotor.setPower(-0.5);
                    initialized = true;
                }

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

        public class LiftTiltPickup implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    sliderArmLeftMotor.setPower(-0.5);
                    sliderArmRightMotor.setPower(-0.5);
                    initialized = true;
                }

                double pos1 = sliderArmLeftMotor.getCurrentPosition();
                double pos2 = sliderArmRightMotor.getCurrentPosition();
                packet.put("liftPos1", pos1);
                packet.put("liftPos2", pos2);
                if (pos1 > -1500 || pos2 > -1500) {
                    return true;
                } else {
                    sliderArmLeftMotor.setPower(0);
                    sliderArmRightMotor.setPower(0);
                    return false;
                }
            }
        }

        public Action liftTiltPickup() {
            return new LiftTiltPickup();
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
        Pose2d initialPose = new Pose2d(15, -59, Math.toRadians(90));
        Pose2d barPos1 = new Pose2d(11, -29, Math.toRadians(90));
        Pose2d ParkPos1 = new Pose2d(11, -64, Math.toRadians(90));
        Pose2d ParkPos2 = new Pose2d(45, -64, Math.toRadians(90));

        Pose2d initPos1 = new Pose2d(11, -34, Math.toRadians(90));
        Pose2d initPos2 = new Pose2d(37, -34, Math.toRadians(90));
        Pose2d initPos3 = new Pose2d(37, -7, Math.toRadians(90));

        Pose2d pushReady = new Pose2d(48, -7, Math.toRadians(90));
        Pose2d push1 = new Pose2d(48, -50, Math.toRadians(90));

        Pose2d PickupPos1 = new Pose2d(48, -44, Math.toRadians(-90));

        Pose2d PickupPos2 = new Pose2d(48, -47
                , Math.toRadians(-90));




        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Claw1 servoClaw1 = new Claw1(hardwareMap);
        Claw2 servoClaw2 = new Claw2(hardwareMap);
        Lift sliderMotor = new Lift(hardwareMap);
        LiftTilt sliderArmLeftMotor = new LiftTilt(hardwareMap);
        LiftTilt sliderArmRightMotor = new LiftTilt(hardwareMap);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(barPos1.position.x, barPos1.position.y), barPos1.heading);


        TrajectoryActionBuilder tab2 = drive.actionBuilder(barPos1)
                .strafeTo(new Vector2d(ParkPos1.position.x, ParkPos1.position.y))
                .strafeTo(new Vector2d(ParkPos2.position.x, ParkPos2.position.y));

        TrajectoryActionBuilder tab3 = drive.actionBuilder(barPos1)
                .strafeTo(new Vector2d(initPos1.position.x, initPos1.position.y))
                .strafeTo(new Vector2d(initPos2.position.x, initPos2.position.y))
                .strafeTo(new Vector2d(initPos3.position.x, initPos3.position.y))
                .strafeTo(new Vector2d(pushReady.position.x, pushReady.position.y))
                .strafeTo(new Vector2d(push1.position.x, push1.position.y));

        TrajectoryActionBuilder tab4 = drive.actionBuilder(push1)
                .strafeTo(new Vector2d(PickupPos1.position.x, PickupPos1.position.y));

        TrajectoryActionBuilder tab44 = drive.actionBuilder(PickupPos1)
                .turnTo(Math.toRadians(-90));

        TrajectoryActionBuilder tab444 = drive.actionBuilder(push1)
                .strafeTo(new Vector2d(PickupPos2.position.x, PickupPos2.position.y));


        TrajectoryActionBuilder tab5 = drive.actionBuilder(PickupPos1)
                .splineTo(new Vector2d(barPos1.position.x, barPos1.position.y), barPos1.heading);

        TrajectoryActionBuilder tab6 = drive.actionBuilder(barPos1)
                .strafeTo(new Vector2d(push1.position.x, push1.position.y));



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
        Actions.runBlocking(
                sliderArmLeftMotor.liftTiltUp()
        );
        Actions.runBlocking(
                sliderArmRightMotor.liftTiltUp()
        );
        Actions.runBlocking(
                tab3.build()
        );
        Actions.runBlocking(
                sliderMotor.liftDown()
        );
        Actions.runBlocking(
                servoClaw2.tiltMidClaw()
        );
        Actions.runBlocking(
                sliderArmLeftMotor.liftTiltUp()
        );
        Actions.runBlocking(
                sliderArmRightMotor.liftTiltUp()
        );
        Actions.runBlocking(
                tab4.build()
        );
        Actions.runBlocking(
                tab44.build()
        );
        Actions.runBlocking(
                sliderArmLeftMotor.liftTiltPickup()
        );
        Actions.runBlocking(
                sliderArmRightMotor.liftTiltPickup()
        );
        sleep(500);
        Actions.runBlocking(
                tab444.build()
        );
        Actions.runBlocking(
                servoClaw1.closeClaw()
        );
        Actions.runBlocking(
                sliderArmLeftMotor.liftTiltUp()
        );
        Actions.runBlocking(
                sliderArmRightMotor.liftTiltUp()
        );
        Actions.runBlocking(
                tab5.build()
        );
        Actions.runBlocking(
                servoClaw2.tiltDownClaw()
        );
        Actions.runBlocking(
                sliderMotor.liftUpBar2()
        );
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
        Actions.runBlocking(
                tab6.build()
        );






    }
}

