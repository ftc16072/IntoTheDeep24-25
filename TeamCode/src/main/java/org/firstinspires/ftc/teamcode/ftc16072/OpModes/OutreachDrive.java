package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.ftc16072.Util.Navigation;


@TeleOp()
public class OutreachDrive extends QQOpMode{

    @Override
    public void init(){
        robot.makeDriveOnly();
        robot.init(hardwareMap);
        robot.controlHub.resetGyro();
        nav = new Navigation(robot, telemetry, isAllianceRed);
    }

    @Override
    public void start(){

    }

    @Override
    public void loop() {
        if(gamepad1.left_bumper){
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.TURBO);
        }else{
            robot.mecanumDrive.setSpeed(MecanumDrive.Speed.SLOW);
        }
        nav.driveFieldRelative(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);
        if(gamepad1.b){
            robot.controlHub.resetGyro();
        }
    }
}
