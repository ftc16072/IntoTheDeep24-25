package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.Util.Navigation;

public abstract class QQOpMode extends OpMode {
    public Robot robot = new Robot();
    public Navigation nav;
    boolean isAllianceRed;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        nav = new Navigation(robot, telemetry);
        robot.init(hardwareMap);
        robot.controlHub.resetGyro();

        isAllianceRed = robot.scoringClaw.isColorRed();
        if(isAllianceRed) {
            telemetry.addData("Alliance", "RED");
            robot.intakeClaw.setTargetColorRed();
        }
        else{
            telemetry.addData("Alliance", "BLUE");
            robot.intakeClaw.setTargetColorBlue();
        }
    }
    public void init_loop(){
        if(isAllianceRed) {
            telemetry.addData("Alliance", "RED");
        }
        else{
            telemetry.addData("Alliance", "BLUE");
        }
    }

    @Override
    public void start(){
        robot.limelight.start();
    }

    @Override
    public void loop(){
        robot.update(telemetry);
    }

}
