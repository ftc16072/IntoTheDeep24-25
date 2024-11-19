package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;
@TeleOp
public class ScrimmageTeleop extends QQOpMode{

    public static final double TRIGGER_THRESHOLD = 0.5;
    public static final int MANUAL_CHANGE = 5;
    private boolean isPlacing = false;

    public void init(){
        isPlacing = false;
        super.init();
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
            robot.scoringClaw.open();
        }else if (gamepad1.b) {
            robot.scoringClaw.close();
            robot.scoreArm.goToPlace();
        }else if (gamepad1.dpad_right){
            robot.scoringClaw.wristStart();
        }else if (gamepad1.dpad_left) {
            robot.scoringClaw.wristEnd();
        }else if (gamepad1.dpad_up){
            robot.scoreArm.manualPositionChange(MANUAL_CHANGE);
        }else if (gamepad1.dpad_down){
            robot.scoreArm.manualPositionChange(-MANUAL_CHANGE);
        }
        if (gamepad1.y && gamepad1.dpad_right){
            robot.controlHub.resetGyro();
        }
        if (gamepad1.right_trigger > TRIGGER_THRESHOLD) {
            robot.scoringClaw.close();
        }else if (gamepad1.right_bumper){
        robot.scoringClaw.open();}
/*
       if (robot.scoreArm.isAboveWristThreshold()){
            robot.claw.wristEnd();
        }else {robot.claw.wristStart();}*/

        if(gamepad1.x){
            robot.scoreArm.goToPlace();
            robot.mecanumDrive.move(-.6,0,0);
            isPlacing = true;
        }else if(!gamepad1.x && isPlacing){
            robot.scoringClaw.open();
            robot.mecanumDrive.move(0,0,0);








            isPlacing = false;
        }else{
            nav.driveFieldRelative(forward, left, rotate);
        }
    }
}
