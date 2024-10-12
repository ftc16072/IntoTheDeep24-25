package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;

public class ScrimmageTeleop extends QQOpMode{

    public static final double TRIGGER_THRESHOLD = 0.5;
    public static final int MANUAL_CHANGE = 5;

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
        robot.arm.goToIntake();
        }else if (gamepad1.b) {
        robot.arm.goToPlacement();}
        else if (gamepad1.dpad_up){
            robot.arm.manualPositionChange(MANUAL_CHANGE);
        }else if (gamepad1.dpad_down){
            robot.arm.manualPositionChange(-MANUAL_CHANGE);
        }


        if (gamepad1.right_bumper) {
            robot.claw.close();
        }else if (gamepad1.right_trigger > TRIGGER_THRESHOLD){
        robot.claw.open();}
    }
}
