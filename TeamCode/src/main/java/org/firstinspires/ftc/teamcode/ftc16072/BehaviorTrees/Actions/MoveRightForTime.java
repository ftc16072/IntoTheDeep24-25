package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class MoveRightForTime extends QQTimeoutNode {
    double speed;
    State lastStatus = State.RUNNING;
    public MoveRightForTime(double seconds, double speed) {
        super(seconds);
        this.speed = speed;
    }

    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        if (lastStatus != State.RUNNING){
            return lastStatus;}
        else {
            opMode.robot.mecanumDrive.move(0,speed,0);
            if (hasTimedOut()){
                opMode.robot.mecanumDrive.stop();
                lastStatus = State.SUCCESS;
                return State.SUCCESS;

            }
            return State.RUNNING;
            }
    }
}
