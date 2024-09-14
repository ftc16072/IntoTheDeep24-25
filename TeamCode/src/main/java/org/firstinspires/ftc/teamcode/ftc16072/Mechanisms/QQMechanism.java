package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;

import java.util.List;

abstract public class QQMechanism {
    public abstract void init(HardwareMap hwMap);

    public abstract List<QQTest> getTests();
    public void update(){}
    public String getName(){
        return this.getClass().getSimpleName();
    }

}
