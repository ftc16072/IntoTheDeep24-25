package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.Util.Navigation;

public abstract class QQOpMode extends OpMode {
    public Robot robot = new Robot();
    public Navigation nav;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        nav = new Navigation(robot, telemetry);
        robot.init(hardwareMap);
        robot.controlHub.resetGyro();
        //robot.claw.close();
        //robot.arm.goToDrive();

    }

    @Override
    public void loop(){
        robot.update(telemetry);
    }

}
