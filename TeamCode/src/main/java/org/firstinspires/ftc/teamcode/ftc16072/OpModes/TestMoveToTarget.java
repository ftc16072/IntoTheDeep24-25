package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import org.firstinspires.ftc.teamcode.ftc16072.Util.MoveToTarget;

public class TestMoveToTarget extends QQOpMode{
    MoveToTarget moveToTarget;
    @Override
    public void init(){
        super.init();
        moveToTarget = new MoveToTarget(robot);
    }
    public void loop(){
        super.loop();
        moveToTarget.moveToTarget();
    }
}
