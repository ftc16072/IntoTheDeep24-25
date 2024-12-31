package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;

@TeleOp
public class NoTurnTeleop extends QQOpMode {

    public static final double TRIGGER_THRESHOLD = 0.5;
    public static final int SCORE_ARM_MANUAL_CHANGE = 15;
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

    }

    public void loop() {
        super.loop();
        double forward = -gamepad1.left_stick_y;
        double left = gamepad1.left_stick_x;
        double rotate = 0;
        if (gamepad1.right_stick_button) {
            if (gamepad1.right_stick_y < -0.2) {
                robot.intakeSlides.extend();
            } else if (gamepad1.right_stick_y > 0.2) {
                robot.intakeSlides.retract();
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
            robot.scoreArm.goToPlace();
            robot.intakeArm.goToIntake();
        } else if (gamepad1.dpad_up) {
            robot.scoreArm.manualPositionChange(SCORE_ARM_MANUAL_CHANGE);
        } else if (gamepad1.dpad_down) {
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
        if (gamepad1.x && !XWasPressed) {
            robot.intakeClaw.wristIntake();
        }
        if (gamepad1.right_bumper) {
            robot.intakeClaw.open();
        } else if (gamepad1.right_trigger > TRIGGER_THRESHOLD) {
            robot.intakeClaw.close();
        }
        if (robot.intakeClaw.isClawClosed() && !intakeClawWasClosed) {
            robot.intakeClaw.wristTransfer();
            robot.intakeSlides.startPosition();
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
        if (gamepad2.dpad_up) {
            robot.intakeSlides.extend();
        } else if (gamepad2.dpad_down) {
            robot.intakeSlides.retract();
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
        slidesSwitchWasPressed = robot.intakeSlides.isSwitchPressed();
        manipulatorXWasPressed = gamepad2.x;

    }
}

