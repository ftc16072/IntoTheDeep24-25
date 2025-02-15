package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp()
public class FieldRelativeDrive extends QQOpMode{

    @Override
    public void init(){
        super.init();
        robot.limelight.start();
    }

    @Override
    public void loop() {
        telemetry.addData("xPosition", robot.otos.getOtosPosition().x);
        telemetry.addData("yPosition", robot.otos.getOtosPosition().y);
        nav.driveFieldRelative(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);
        robot.limelight.setYaw(AngleUnit.DEGREES, robot.controlHub.getYaw(AngleUnit.DEGREES));
        telemetry.addData("bot_pose", robot.limelight.getRobotPosition().toString());
        telemetry.addData("limelight_x", robot.limelight.getRobotPositionX(DistanceUnit.INCH));
        telemetry.addData("limelight_y", robot.limelight.getRobotPositionY(DistanceUnit.INCH));
        telemetry.addData("april tag seen",robot.limelight.isAprilTagSeen());
    }
}
