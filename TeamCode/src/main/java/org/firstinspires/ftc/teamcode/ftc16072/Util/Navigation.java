package org.firstinspires.ftc.teamcode.ftc16072.Util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
@Config
public class Navigation {
    public static double MAX_TRANSLATE = 1;
    public static double MIN_TRANSLATE = -MAX_TRANSLATE;
    public static final double MAX_ROTATE = 0.3;
    public static final double MIN_ROTATE = -MAX_ROTATE;
    Robot robot;

    public static double TRANSLATIONAL_KP = 0.03;
    public static double TRANSLATIONAL_KI = 0.0;
    public static double TRANSLATIONAL_KD = 0.000;
    public static double TRANSLATIONAL_KF = 0;
    public static double TRANSLATIONAL_TOLERANCE_THRESHOLD = 1;

    public static double ROTATIONAL_KP = 0.01;
    public static double ROTATIONAL_KI = 0.000;
    public static double ROTATIONAL_KD = 0.00;
    public static double ROTATIONAL_KF = 0;
    public static double ROTATIONAL_TOLERANCE_THRESHOLD = 3;

    PIDFController PIDx, PIDy, PIDh;

    double lastDesiredX, lastDesiredY, lastDesiredH;
    Telemetry telemetry;

    public Navigation(Robot robot, Telemetry telemetry){
        this.robot = robot;
        PIDx = new PIDFController(TRANSLATIONAL_KP,TRANSLATIONAL_KI,TRANSLATIONAL_KD,TRANSLATIONAL_KF, MAX_TRANSLATE, MIN_TRANSLATE);
        PIDy = new PIDFController(TRANSLATIONAL_KP,TRANSLATIONAL_KI,TRANSLATIONAL_KD,TRANSLATIONAL_KF,MAX_TRANSLATE,MIN_TRANSLATE);
        PIDh = new PIDFController(ROTATIONAL_KP,ROTATIONAL_KI,ROTATIONAL_KD,ROTATIONAL_KF, MAX_ROTATE, MIN_ROTATE);
        this.telemetry = telemetry;
    }
    public void driveFieldRelative(double forwardSpeed, double strafeRightSpeed, double rotateCWSpeed){
        double robotAngle = robot.controlHub.getYaw(AngleUnit.RADIANS);
        //convert to polar
        double theta = Math.atan2(forwardSpeed, strafeRightSpeed);
        double r = Math.hypot(forwardSpeed, strafeRightSpeed);

        theta = AngleUnit.normalizeRadians(theta - robotAngle);

        //convert to cartesian
        double newForwardSpeed = r * Math.sin(theta);
        double newStrafeRightSpeed = r * Math.cos(theta);

        robot.mecanumDrive.move(newForwardSpeed,newStrafeRightSpeed,rotateCWSpeed);

    }

    private boolean notWithinTolerance(double desired, double current, double tolerance){
        double error = desired - current;
        return !(Math.abs(error) < tolerance);
    }

    public boolean driveToPositionIN(double desiredX,double desiredY,double desiredHeading){
        double forwardSpeed;
        double strafeRightSpeed;
        double rotateCCWSpeed;
        PIDx.updateConstants(TRANSLATIONAL_KP,TRANSLATIONAL_KI,TRANSLATIONAL_KD,TRANSLATIONAL_KF,MAX_TRANSLATE,MIN_TRANSLATE);
        PIDx.updateConstants(TRANSLATIONAL_KP,TRANSLATIONAL_KI,TRANSLATIONAL_KD,TRANSLATIONAL_KF,MAX_TRANSLATE,MIN_TRANSLATE);
        PIDy.updateConstants(TRANSLATIONAL_KP,TRANSLATIONAL_KI,TRANSLATIONAL_KD,TRANSLATIONAL_KF,MAX_TRANSLATE,MIN_TRANSLATE);
        PIDy.updateConstants(TRANSLATIONAL_KP,TRANSLATIONAL_KI,TRANSLATIONAL_KD,TRANSLATIONAL_KF,MAX_TRANSLATE,MIN_TRANSLATE);
        if((desiredX != lastDesiredX) || (desiredY != lastDesiredY) || (desiredHeading != lastDesiredH)){
            lastDesiredX = desiredX;
            lastDesiredY = desiredY;
            lastDesiredH = desiredHeading;
            PIDx.reset();
            PIDy.reset();
            PIDh.reset();
        }
        if(robot.limelight.isAprilTagSeen()){
            robot.otos.setOtosPosition(
                    robot.limelight.getRobotPositionX(DistanceUnit.INCH),
                    robot.limelight.getRobotPositionY(DistanceUnit.INCH),
                    robot.controlHub.getYaw(AngleUnit.DEGREES));
        }
        double currentPositionX = robot.otos.getOtosPosition().x;
        double currentPositionY = robot.otos.getOtosPosition().y;
        double currentPositionH = robot.controlHub.getYaw(AngleUnit.DEGREES);
        if(notWithinTolerance(desiredX,currentPositionX,TRANSLATIONAL_TOLERANCE_THRESHOLD)){
            strafeRightSpeed = PIDx.calculate(desiredX, currentPositionX);
        }else{strafeRightSpeed = 0;}
        if(notWithinTolerance(desiredY,currentPositionY,TRANSLATIONAL_TOLERANCE_THRESHOLD)){
            forwardSpeed = PIDy.calculate(desiredY, currentPositionY);
        }else {forwardSpeed = 0;}
        if (notWithinTolerance(desiredHeading, currentPositionH,ROTATIONAL_TOLERANCE_THRESHOLD)) {
            rotateCCWSpeed = PIDh.calculate(desiredHeading, currentPositionH);
        }else {rotateCCWSpeed = 0;}

        telemetry.addData("Current X", currentPositionX);
        telemetry.addData("Desired X", desiredX);
        telemetry.addData("Desired Y", desiredY);
        telemetry.addData("Current Y", currentPositionY);
        telemetry.addData("Desired Heading", desiredHeading);
        telemetry.addData("Current Heading", currentPositionH);

        telemetry.addData("strafe right speed",strafeRightSpeed);
        telemetry.addData("foward speed",forwardSpeed);
        telemetry.addData("rotate CW Speed", -rotateCCWSpeed);

        driveFieldRelative(forwardSpeed,strafeRightSpeed,-rotateCCWSpeed);
        return !(notWithinTolerance(desiredX,currentPositionX,TRANSLATIONAL_TOLERANCE_THRESHOLD)||
                notWithinTolerance(desiredY,currentPositionY,TRANSLATIONAL_TOLERANCE_THRESHOLD)||
                notWithinTolerance(desiredHeading,currentPositionH,ROTATIONAL_TOLERANCE_THRESHOLD));
    }
}