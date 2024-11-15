package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;

@TeleOp
public class NoTurnTeleop extends QQOpMode{

    public static final double TRIGGER_THRESHOLD = 0.5;
    public static final int MANUAL_CHANGE = 5;
    public static final double MANUAL_CHANGE_AMOUNT_WRIST = 0.01;
    private boolean isPlacing = false;
    private boolean isSearching = false;
    private boolean isIntaking = false;
    boolean manipulatorXWasPressed;

    public void init(){
        isPlacing = false;
        super.init();
        robot.intakeSlides.telemetry = telemetry;
        robot.scoreArm.telemetry = telemetry;
        robot.intakeClaw.wristFlat();
    }
    public void loop(){
        super.loop();
        double forward = -gamepad1.left_stick_y;
        double left = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        if (gamepad1.left_trigger > TRIGGER_THRESHOLD ){
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.TURBO);
        } else if (gamepad1.left_bumper) {
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.SLOW);
        } else robot.mecanumDrive.setSpeed(MecanumDrive.Speed.NORMAL);

        if (gamepad1.a){
            robot.scoreArm.goToIntake();
            robot.claw.open();
            robot.intakeArm.goToDropPos();
        }else if (gamepad1.b) {
            robot.claw.close();
            robot.scoreArm.goToPlace();
            robot.intakeArm.transfer();
        }else if (gamepad1.dpad_up){
            robot.scoreArm.manualPositionChange(MANUAL_CHANGE);
        }else if (gamepad1.dpad_down){
            robot.scoreArm.manualPositionChange(-MANUAL_CHANGE);
        }
        if (gamepad1.y && gamepad1.dpad_right){
            robot.controlHub.resetGyro();
        }
        
        if (gamepad1.right_trigger > TRIGGER_THRESHOLD) {
            robot.intakeClaw.open();
            robot.claw.close();
        }else if (gamepad1.right_bumper){
        robot.claw.open();}


        if(gamepad1.x){
            robot.scoreArm.goToScoring();
            isPlacing = true;
        }else if(!gamepad1.x && isPlacing){
            robot.claw.open();
            isPlacing = false;
        }else{
            nav.driveFieldRelative(forward, left, rotate);
        }
        if(gamepad2.y){
            robot.intakeSlides.fullExtension();
            robot.intakeClaw.close();
        }
        if(gamepad2.b){
            robot.intakeSlides.halfExtension();
            robot.intakeClaw.close();
        }
        if(gamepad2.a){
            robot.intakeSlides.startPosition();
        }
        if(gamepad2.dpad_up){
            robot.intakeSlides.manualPositionChange(5);
        }
        if(gamepad2.dpad_down){
            robot.intakeSlides.manualPositionChange(-5);
        }
        if (gamepad2.x && !manipulatorXWasPressed){
            if(isSearching){
                robot.intakeClaw.open();
                robot.intakeArm.goToIntake();
                isIntaking = true;
                isSearching = false;
            }else {
                robot.intakeArm.searching();
                isSearching = true;
            }
        }if (isIntaking && !gamepad2.x){
            robot.intakeClaw.close();
            robot.intakeClaw.wristFlat();
            robot.intakeArm.transfer();
            isIntaking = false;
        }
        if (gamepad2.right_bumper){
            robot.intakeClaw.open();
        }else if(gamepad2.right_trigger>TRIGGER_THRESHOLD){
            robot.intakeClaw.close();
        }

        if (gamepad2.dpad_right){
            robot.intakeClaw.adjustWrist(MANUAL_CHANGE_AMOUNT_WRIST);
        }else if(gamepad2.dpad_left){
            robot.intakeClaw.adjustWrist(-MANUAL_CHANGE_AMOUNT_WRIST);
        }

        manipulatorXWasPressed = gamepad2.x;

    }
}
