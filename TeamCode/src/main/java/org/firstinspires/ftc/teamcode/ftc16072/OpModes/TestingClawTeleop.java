package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestingClawTeleop extends QQOpMode{
    @Override
    public void loop() {
        super.loop();

        if (gamepad1.triangle) {
            robot.claw.close();
        } else {
            robot.claw.open();
        }
    }


}
