package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;

@TeleOp
public class NoTurnTeleop extends QQOpMode{

    public static final double TRIGGER_THRESHOLD = 0.5;
    public static final int MANUAL_CHANGE = 15;
    public static final double MANUAL_CHANGE_AMOUNT_WRIST = 0.03;
    private boolean isPlacing = false;
    private boolean isSearching = false;
    private boolean isIntaking = false;
    boolean XWasPressed;
    boolean manipulatorXWasPressed;
    boolean chamberContactWasPressed;
    boolean clawWasClosed;
    boolean slidesSwitchWasPressed;
    int contactLostPos;

    public void init(){
        isPlacing = false;
        super.init();
        robot.intakeClaw.wristFlat();
    }
    public void loop(){
        super.loop();
        double forward = -gamepad1.left_stick_y;
        double left = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        nav.driveFieldRelative(forward, left, rotate);

        if (gamepad1.left_trigger > TRIGGER_THRESHOLD ){
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.TURBO);
        } else if (gamepad1.left_bumper) {
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.SLOW);
        } else robot.mecanumDrive.setSpeed(MecanumDrive.Speed.NORMAL);

        if (gamepad1.a){
            if (robot.intakeSlides.isSwitchPressed()){
                robot.scoreArm.goToIntake();
                robot.scoringClaw.open();
                robot.intakeArm.goToDropPos();
            }else {
                robot.intakeSlides.startPosition();
            }
        if (robot.intakeSlides.isSwitchPressed() && !slidesSwitchWasPressed){
            robot.scoreArm.goToIntake();
            robot.scoringClaw.open();
            robot.intakeArm.goToDropPos();
        }
        }else if (robot.scoringClaw.isClawClosed() && !clawWasClosed) {
            robot.intakeClaw.open();
            robot.scoreArm.goToPlace();
            robot.intakeArm.transfer();
        }else if (gamepad1.dpad_up){
            robot.scoreArm.manualPositionChange(MANUAL_CHANGE);
        }else if (gamepad1.dpad_down){
            robot.scoreArm.manualPositionChange(-MANUAL_CHANGE);
        }
        if (gamepad1.left_stick_button){
            robot.controlHub.resetGyro();
        }

        if(robot.scoreArm.isChamberContacted()) {
            robot.scoreArm.goToScoring();
        }

        if(gamepad1.y){
            robot.intakeClaw.close();
            robot.intakeSlides.fullExtension();
        }
        if(gamepad1.b){
            robot.intakeClaw.close();
            robot.intakeSlides.halfExtension();
        }
        if (gamepad1.x && !XWasPressed){
            if(isSearching){
                robot.intakeClaw.close();
                robot.intakeArm.goToIntake();
                isIntaking = true;
                isSearching = false;
            }else {
                robot.intakeArm.searching();
                isSearching = true;
            }
        }if (isIntaking && !gamepad1.x){
            robot.intakeClaw.close();
            robot.intakeClaw.wristFlat();
            robot.intakeArm.transfer();
            isIntaking = false;
        }
        if (gamepad1.right_bumper){
            robot.intakeClaw.open();
        }else if(gamepad1.right_trigger>TRIGGER_THRESHOLD){
            robot.intakeClaw.close();
        }

        if (gamepad1.dpad_right){
            robot.intakeClaw.adjustWrist(-MANUAL_CHANGE_AMOUNT_WRIST);
        }else if(gamepad1.dpad_left){
            robot.intakeClaw.adjustWrist(MANUAL_CHANGE_AMOUNT_WRIST);
        }

        if (gamepad2.b){
            robot.scoreArm.goToPlace();
            robot.intakeArm.transfer();
        }else if(gamepad2.x){
            robot.scoreArm.goToScoring();
            robot.intakeArm.transfer();
        }else if(manipulatorXWasPressed){
            robot.scoringClaw.open();
        } else if (gamepad2.y) {
            robot.scoreArm.goToMove();
        }
        if (gamepad2.dpad_up){
            robot.intakeSlides.manualPositionChange(MANUAL_CHANGE);
        } else if (gamepad2.dpad_down) {
            robot.intakeSlides.manualPositionChange(-MANUAL_CHANGE);
        }
        if (gamepad2.right_trigger>TRIGGER_THRESHOLD){
            robot.scoringClaw.open();
        }else if(gamepad2.right_bumper){
            robot.scoringClaw.close();
        }

        XWasPressed = gamepad1.x;
        chamberContactWasPressed = robot.scoreArm.isChamberContacted();
        clawWasClosed = robot.scoringClaw.isClawClosed();
        slidesSwitchWasPressed = robot.intakeSlides.isSwitchPressed();
        manipulatorXWasPressed = gamepad2.x;

    }
}

