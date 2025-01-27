package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class StrafeToSample extends QQTimeoutNode {
    public static final double STRAFE_SPEED = 0.15;
    State lastStatus = State.RUNNING;

    public StrafeToSample(double seconds) {
        super(seconds);
    }

    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        if (lastStatus != State.RUNNING){
            return lastStatus;
        }else{
            if(hasTimedOut()){
                opMode.robot.mecanumDrive.stop();
                lastStatus = State.FAILURE;
                return State.FAILURE;
            }
            if (opMode.robot.intakeClaw.hasTarget()){
                opMode.robot.mecanumDrive.stop();
                opMode.robot.intakeClaw.intakeWithVision();
                lastStatus = State.SUCCESS;
                return State.SUCCESS;
            }
            opMode.robot.mecanumDrive.move(0, STRAFE_SPEED,0);
            return State.RUNNING;
        }
    }
}

