package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class DriveToChamber extends QQTimeoutNode {

    public static final double FORWARD_SPEED = 0.2;

    public DriveToChamber(double seconds) {
        super(seconds);
    }

    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        if(hasTimedOut()){
            opMode.robot.mecanumDrive.stop();
            return State.FAILURE;
        }
        opMode.robot.mecanumDrive.move(FORWARD_SPEED,0,0);
        if (opMode.robot.scoreArm.isChamberContacted()){
            opMode.robot.mecanumDrive.stop();
            return State.SUCCESS;

        }
        return State.RUNNING;
    }
}
