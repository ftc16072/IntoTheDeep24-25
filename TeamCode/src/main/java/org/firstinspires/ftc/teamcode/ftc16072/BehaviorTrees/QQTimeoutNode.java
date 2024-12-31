package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees;

import com.qualcomm.robotcore.util.ElapsedTime;

abstract public class QQTimeoutNode extends QQNode {
    ElapsedTime timer = new ElapsedTime();
    public double seconds;
    boolean isTimerStarted;

    public QQTimeoutNode(double seconds){
        super();
        this.seconds = seconds;
    }
    public boolean hasTimedOut(){
        if (!isTimerStarted){
            timer.reset();
            isTimerStarted = true;
        }
        if (timer.seconds() >= seconds){
            return true;
        }
        return false;
    }
}
