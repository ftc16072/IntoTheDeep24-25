package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;

@TeleOp
@SuppressWarnings("unused")
public class RI3WTeleOp extends QQOpMode{

    public static final double TRIGGER_THRESHOLD = 0.5;

    @Override
    public void loop() {
        super.loop();
        double forward = -gamepad1.left_stick_y;
        double left = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        if (gamepad1.left_trigger > TRIGGER_THRESHOLD && gamepad1.right_trigger > TRIGGER_THRESHOLD){
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.TURBO);
        } else if (gamepad1.left_trigger > TRIGGER_THRESHOLD) {
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.SLOW);
        } else if (gamepad1.right_trigger > TRIGGER_THRESHOLD) {
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.FAST);
        } else robot.mecanumDrive.setSpeed(MecanumDrive.Speed.NORMAL);

        nav.driveFieldRelative(forward, left, rotate);

        if (gamepad1.dpad_up) {
            //dbar up
        }
        else if (gamepad1.dpad_down){
            //dbar downn
        }

        if (gamepad1.dpad_right){
            //slide extend
        }
        else if (gamepad1.dpad_left){
            //slides retract
        }

        if (gamepad1.left_bumper){
            //claw open
        }
        else if (gamepad1.right_bumper){
            //claw.close
        }


    }
}
