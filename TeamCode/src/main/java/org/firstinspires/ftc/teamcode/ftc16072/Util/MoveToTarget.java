package org.firstinspires.ftc.teamcode.ftc16072.Util;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.opencv.core.Point;

public class MoveToTarget {
    Robot robot;
    public MoveToTarget(Robot robot){
        this.robot = robot;
    }
    public boolean moveToTarget(){
        if (robot.intakeClaw.hasOneNotInTarget()){
            Point delta = robot.intakeClaw.getDeltaToBlock();
            double r = Math.sqrt((delta.x * delta.x)+(delta.y * delta.y));
            double theta = Math.atan2(delta.y,delta.x);
            robot.intakeSlides.movePixels(r);
            robot.intakeArm.rotateArmByDegrees(theta);
            return true;
        }
        return false;
    }
}
