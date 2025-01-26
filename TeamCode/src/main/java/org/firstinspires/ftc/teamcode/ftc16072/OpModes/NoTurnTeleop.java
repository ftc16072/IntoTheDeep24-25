package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;

@TeleOp
public class NoTurnTeleop extends QQOpMode {

    public static final double TRIGGER_THRESHOLD = 0.5;
    public static final int SCORE_ARM_MANUAL_CHANGE = 30;
    public static final double MANUAL_CHANGE_AMOUNT_WRIST = 0.03;
    boolean isPlacing;
    boolean XWasPressed;
    boolean manipulatorXWasPressed;
    boolean chamberContactWasPressed;
    boolean clawWasClosed;
    boolean slidesSwitchWasPressed;
    boolean intakeClawWasClosed;

    public void init() {
        isPlacing = false;
        super.init();
        robot.intakeClaw.setTargetColorBlue();

    }

    public void start(){
        robot.intakeArm.goToDropPos();
    }

    public void loop() {
        telemetry.addData("Control Hub Draw", robot.controlHub.getControlHubCurrent(CurrentUnit.AMPS));
        telemetry.addData("Expansion Hub Draw", robot.controlHub.getExpansionHubCurrent(CurrentUnit.AMPS));
        telemetry.addData("Total Draw", (robot.controlHub.getControlHubCurrent(CurrentUnit.AMPS)+robot.controlHub.getExpansionHubCurrent(CurrentUnit.AMPS)));
        super.loop();
        double forward = -gamepad1.left_stick_y;
        double left = gamepad1.left_stick_x;
        double rotate = 0;
        if (gamepad1.right_stick_button) {
            if (gamepad1.right_stick_y < -0.1) {
                double slideSpeed = (gamepad1.right_stick_y) * -5;
                robot.intakeSlides.extend(slideSpeed);
            } else if (gamepad1.right_stick_y > 0.1) {
                double slideSpeed = (gamepad1.right_stick_y) * 5;
                robot.intakeSlides.retract(slideSpeed);
            }
            if (robot.intakeSlides.isSafeToRotate()) {
                if (gamepad1.right_stick_x > 0.3) {
                    robot.intakeArm.rotateArmRight();
                } else if (gamepad1.right_stick_x < -0.3) {
                    robot.intakeArm.rotateArmLeft();
                }
            }
        }else{
            rotate = gamepad1.right_stick_x;
        }
        nav.driveFieldRelative(forward, left, rotate);

        if (gamepad1.left_trigger > TRIGGER_THRESHOLD) {
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.TURBO);
        } else if (gamepad1.left_bumper) {
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.SLOW);
        } else robot.mecanumDrive.setSpeed(MecanumDrive.Speed.NORMAL);

        if (gamepad1.a) {
            if (robot.intakeSlides.isSwitchPressed()) {
                robot.scoreArm.goToIntake();
                robot.scoringClaw.open();
                robot.scoreArm.setNotScoring();
            } else {
                robot.intakeSlides.startPosition();
            }
            if (robot.intakeSlides.isSwitchPressed() && !slidesSwitchWasPressed) {
                robot.scoreArm.goToIntake();
                robot.scoringClaw.open();
                robot.intakeArm.goToDropPos();
            }
        } else if (robot.scoringClaw.isClawClosed() && !clawWasClosed) {
            robot.intakeClaw.open();
            robot.scoreArm.goToMove();
            robot.intakeArm.goToIntake();
            robot.scoreArm.goToPlace();
        } else if (gamepad2.dpad_up) {
            robot.scoreArm.manualPositionChange(SCORE_ARM_MANUAL_CHANGE);
        } else if (gamepad2.dpad_down) {
            robot.scoreArm.manualPositionChange(-SCORE_ARM_MANUAL_CHANGE);
        }
        if (robot.scoringClaw.isScoreSwitchPressed()){
            robot.scoreArm.setNotScoring();
        }
        if (gamepad1.left_stick_button) {
            robot.controlHub.resetGyro();
        }

        if (robot.scoreArm.isChamberContacted()) {
            robot.scoreArm.goToScoring();
        }

        if (gamepad1.y) {
            robot.intakeSlides.fullExtension();
        }
        if (gamepad1.b) {
            robot.intakeArm.goToDropPos();
        }
        if (robot.intakeClaw.hasTarget()){
            gamepad1.rumble(100);
        }
        if (gamepad1.x && !XWasPressed) {
            robot.intakeClaw.open();
            if(robot.intakeClaw.hasTarget()){
                robot.intakeClaw.IntakeWithVision();
            }else {
            robot.intakeClaw.wristIntake();}
        }
        if (gamepad1.right_bumper) {
            robot.intakeClaw.open();
        } else if (gamepad1.right_trigger > TRIGGER_THRESHOLD) {
            robot.intakeClaw.close();
        }
        if (robot.intakeClaw.isClawClosed() && !intakeClawWasClosed) {
            robot.intakeClaw.wristTransfer();
            robot.intakeArm.goToIntake();
        }

        if (gamepad1.dpad_right) {
            robot.intakeClaw.adjustWrist(-MANUAL_CHANGE_AMOUNT_WRIST);
        } else if (gamepad1.dpad_left) {
            robot.intakeClaw.adjustWrist(MANUAL_CHANGE_AMOUNT_WRIST);
        }

        if (gamepad2.b) {
            robot.scoreArm.goToPlace();
            robot.intakeArm.goToIntake();
            robot.scoreArm.setNotScoring();
        } else if (gamepad2.x) {
            robot.scoreArm.goToScoring();
            robot.intakeArm.goToIntake();
            robot.scoreArm.setNotScoring();
        } else if (manipulatorXWasPressed) {
            robot.scoringClaw.open();
        } else if (gamepad2.y) {
            robot.scoreArm.goToMove();
            robot.scoreArm.setNotScoring();
        }
        if (gamepad2.dpad_right) {
            robot.intakeSlides.extend(1);
        } else if (gamepad2.dpad_left) {
            robot.intakeSlides.retract(1);
        }if (gamepad1.dpad_up){
            robot.intakeArm.moveArmDown();
        }if(gamepad1.dpad_down){
            robot.intakeArm.moveArmUp();
        }
        if (gamepad2.right_trigger > TRIGGER_THRESHOLD) {
            robot.scoringClaw.open();
            robot.scoreArm.setNotScoring();
        } else if (gamepad2.right_bumper) {
            robot.scoringClaw.close();
        }

        XWasPressed = gamepad1.x;
        chamberContactWasPressed = robot.scoreArm.isChamberContacted();
        clawWasClosed = robot.scoringClaw.isClawClosed();
        intakeClawWasClosed = robot.intakeClaw.isClawClosed();
        slidesSwitchWasPressed = robot.intakeSlides.isSwitchPressed();
        manipulatorXWasPressed = gamepad2.x;

    }
}

