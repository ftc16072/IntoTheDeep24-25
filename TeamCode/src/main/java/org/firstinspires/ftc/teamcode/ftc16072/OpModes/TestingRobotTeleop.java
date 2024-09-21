package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
@Config
public class TestingRobotTeleop extends QQOpMode{
    @Override
    public void loop() {
        if (gamepad1.triangle) {
            robot.claw.close();
        } else {
            robot.claw.open();
        }
    }


}
