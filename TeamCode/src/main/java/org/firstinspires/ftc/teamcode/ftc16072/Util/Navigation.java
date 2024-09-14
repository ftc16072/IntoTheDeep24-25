package org.firstinspires.ftc.teamcode.ftc16072.Util;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;

public class Navigation {
    Robot robot;

    static double TRANSLATIONAL_KP = 0.1;
    static double TRANSLATIONAL_KI = 0.1;
    static double TRANSLATIONAL_KD = 0.001;
    static double TRANSLATIONAL_KF = 0;
    static double TRANSLATIONAL_TOLERANCE_THRESHOLD = 0.5;

    static double ROTATIONAL_KP = 0.1;
    static double ROTATIONAL_KI = 0.001;
    static double ROTATIONAL_KD = 0.001;
    static double ROTATIONAL_KF = 0;
    static double ROTATIONAL_TOLERANCE_THRESHOLD = 1;

    PIDFController PIDx, PIDy, PIDh;

    double lastDesiredX, lastDesiredY, lastDesiredH;
    Telemetry telemetry;

    public Navigation(Robot robot, Telemetry telemetry){
        this.robot = robot;
        PIDx = new PIDFController(TRANSLATIONAL_KP,TRANSLATIONAL_KI,TRANSLATIONAL_KD,TRANSLATIONAL_KF,1,-1);
        PIDy = new PIDFController(TRANSLATIONAL_KP,TRANSLATIONAL_KI,TRANSLATIONAL_KD,TRANSLATIONAL_KF,1,-1);
        PIDh = new PIDFController(ROTATIONAL_KP,ROTATIONAL_KI,ROTATIONAL_KD,ROTATIONAL_KF,1,-1);
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
        telemetry.addData("strafe right speed",-strafeLeftSpeed);
        telemetry.addData("foward speed",forwardSpeed);
        telemetry.addData("rotate CW Speed", -rotateCCWSpeed);
        driveFieldRelative(forwardSpeed,-strafeLeftSpeed,-rotateCCWSpeed);
        return !(notWithinTolerance(desiredX,currentPosition.x,TRANSLATIONAL_TOLERANCE_THRESHOLD)||
                notWithinTolerance(desiredY,currentPosition.y,TRANSLATIONAL_TOLERANCE_THRESHOLD)||
                notWithinTolerance(desiredHeading, currentPosition.h,ROTATIONAL_TOLERANCE_THRESHOLD));
    }
}