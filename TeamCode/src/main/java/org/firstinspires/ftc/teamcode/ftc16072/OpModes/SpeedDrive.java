package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;


@TeleOp()
public class SpeedDrive extends QQOpMode{

    @Override
    public void loop() {
        if(gamepad1.dpad_left){
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.NORMAL);
        } else if (gamepad1.dpad_right) {
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.FAST);
        } else if (gamepad1.dpad_down) {
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.SLOW);
        } else if (gamepad1.dpad_up) {
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.TURBO);
        }
        robot.mecanumDrive.move(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);
    }
}
