package org.firstinspires.ftc.teamcode.ftc16072.Util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;


import java.util.List;
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
    private Limelight3A limelight;

    public Navigation(Robot robot, Telemetry telemetry, HardwareMap hardwareMap) {
        this.robot = robot;
        PIDx = new PIDFController(TRANSLATIONAL_KP, TRANSLATIONAL_KI, TRANSLATIONAL_KD, TRANSLATIONAL_KF, MAX_TRANSLATE, MIN_TRANSLATE);
        PIDy = new PIDFController(TRANSLATIONAL_KP, TRANSLATIONAL_KI, TRANSLATIONAL_KD, TRANSLATIONAL_KF, MAX_TRANSLATE, MIN_TRANSLATE);
        PIDh = new PIDFController(ROTATIONAL_KP, ROTATIONAL_KI, ROTATIONAL_KD, ROTATIONAL_KF, MAX_ROTATE, MIN_ROTATE);
        this.limelight = hardwareMap.get(Limelight3A.class, "limelight");
        this.telemetry = telemetry;
    }

    public void driveFieldRelative(double forwardSpeed, double strafeRightSpeed, double rotateCWSpeed) {
        double robotAngle = robot.controlHub.getYaw(AngleUnit.RADIANS);
        //convert to polar
        double theta = Math.atan2(forwardSpeed, strafeRightSpeed);
        double r = Math.hypot(forwardSpeed, strafeRightSpeed);

        theta = AngleUnit.normalizeRadians(theta - robotAngle);

        //convert to cartesian
        double newForwardSpeed = r * Math.sin(theta);
        double newStrafeRightSpeed = r * Math.cos(theta);

        robot.mecanumDrive.move(newForwardSpeed, newStrafeRightSpeed, rotateCWSpeed);

    }

    private boolean notWithinTolerance(double desired, double current, double tolerance) {
        double error = desired - current;
        return !(Math.abs(error) < tolerance);
    }

    public boolean driveToPositionIN(double desiredX, double desiredY, double desiredHeading) {
        double forwardSpeed;
        double strafeLeftSpeed;
        double rotateCCWSpeed;
        PIDx.updateConstants(TRANSLATIONAL_KP, TRANSLATIONAL_KI, TRANSLATIONAL_KD, TRANSLATIONAL_KF, MAX_TRANSLATE, MIN_TRANSLATE);
        PIDx.updateConstants(TRANSLATIONAL_KP, TRANSLATIONAL_KI, TRANSLATIONAL_KD, TRANSLATIONAL_KF, MAX_TRANSLATE, MIN_TRANSLATE);
        PIDy.updateConstants(TRANSLATIONAL_KP, TRANSLATIONAL_KI, TRANSLATIONAL_KD, TRANSLATIONAL_KF, MAX_TRANSLATE, MIN_TRANSLATE);
        PIDy.updateConstants(TRANSLATIONAL_KP, TRANSLATIONAL_KI, TRANSLATIONAL_KD, TRANSLATIONAL_KF, MAX_TRANSLATE, MIN_TRANSLATE);
        if ((desiredX != lastDesiredX) || (desiredY != lastDesiredY) || (desiredHeading != lastDesiredH)) {
            lastDesiredX = desiredX;
            lastDesiredY = desiredY;
            lastDesiredH = desiredHeading;
            PIDx.reset();
            PIDy.reset();
            PIDh.reset();
        }
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) {
            telemetry.addData("Limelight", "No valid data");
            telemetry.update();
            return false;
        }
        Pose3D botPose = result.getBotpose();
        double currentX = botPose.getPosition().x;  // Field X position
        double currentY = botPose.getPosition().y;  // Field Y position
        double currentH = botPose.getPosition().z;  // Heading (Yaw)

        if (notWithinTolerance(desiredX, currentX, TRANSLATIONAL_TOLERANCE_THRESHOLD)) {
            forwardSpeed = PIDx.calculate(desiredX, currentX);
        } else {
            forwardSpeed = 0;
        }

        if (notWithinTolerance(desiredY, currentY, TRANSLATIONAL_TOLERANCE_THRESHOLD)) {
            strafeLeftSpeed = PIDy.calculate(desiredY, currentY);
        } else {
            strafeLeftSpeed = 0;
        }

        if (notWithinTolerance(desiredHeading, currentH, ROTATIONAL_TOLERANCE_THRESHOLD)) {
            rotateCCWSpeed = PIDh.calculate(desiredHeading, currentH);
        } else {
            rotateCCWSpeed = 0;
        }

        telemetry.addData("Current X", currentX);
        telemetry.addData("Desired X", desiredX);
        telemetry.addData("Desired Y", desiredY);
        telemetry.addData("Current Y", currentY);
        telemetry.addData("Desired Heading", desiredHeading);
        telemetry.addData("Current Heading", currentH);
        telemetry.addData("strafe right speed", -strafeLeftSpeed);
        telemetry.addData("forward speed", forwardSpeed);
        telemetry.addData("rotate CW Speed", -rotateCCWSpeed);
        telemetry.update();

        driveFieldRelative(forwardSpeed, -strafeLeftSpeed, -rotateCCWSpeed);
        return !(notWithinTolerance(desiredX, currentX, TRANSLATIONAL_TOLERANCE_THRESHOLD) ||
                notWithinTolerance(desiredY, currentY, TRANSLATIONAL_TOLERANCE_THRESHOLD) ||
                notWithinTolerance(desiredHeading, currentH, ROTATIONAL_TOLERANCE_THRESHOLD));
    }
}

