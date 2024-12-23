package org.firstinspires.ftc.teamcode.ftc16072.Util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
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
    public static double TRANSLATIONAL_TOLERANCE_THRESHOLD = 0.5;

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
        double strafeLeftSpeed;
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
        SparkFunOTOS.Pose2D currentPosition = robot.otos.getOtosPosition();
        if(notWithinTolerance(desiredX,currentPosition.x,TRANSLATIONAL_TOLERANCE_THRESHOLD)){
            forwardSpeed = PIDx.calculate(desiredX, currentPosition.x);
        }else{forwardSpeed = 0;}
        if(notWithinTolerance(desiredY,currentPosition.y,TRANSLATIONAL_TOLERANCE_THRESHOLD)){
            strafeLeftSpeed = PIDy.calculate(desiredY, currentPosition.y);
        }else {strafeLeftSpeed = 0;}
        if (notWithinTolerance(desiredHeading, currentPosition.h,ROTATIONAL_TOLERANCE_THRESHOLD)) {
            rotateCCWSpeed = PIDh.calculate(desiredHeading, currentPosition.h);
        }else {rotateCCWSpeed = 0;}

        telemetry.addData("Current X", currentPosition.x);
        telemetry.addData("Desired X", desiredX);
        telemetry.addData("Desired Y", desiredY);
        telemetry.addData("Current Y", currentPosition.y);
        telemetry.addData("Desired Heading", desiredHeading);
        telemetry.addData("Current Heading", currentPosition.h);

        telemetry.addData("strafe right speed",-strafeLeftSpeed);
        telemetry.addData("foward speed",forwardSpeed);
        telemetry.addData("rotate CW Speed", -rotateCCWSpeed);

        driveFieldRelative(forwardSpeed,-strafeLeftSpeed,-rotateCCWSpeed);
        return !(notWithinTolerance(desiredX,currentPosition.x,TRANSLATIONAL_TOLERANCE_THRESHOLD)||
                notWithinTolerance(desiredY,currentPosition.y,TRANSLATIONAL_TOLERANCE_THRESHOLD)||
                notWithinTolerance(desiredHeading, currentPosition.h,ROTATIONAL_TOLERANCE_THRESHOLD));
    }
}