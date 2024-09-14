package org.firstinspires.ftc.teamcode.ftc16072.Tests;

import org.firstinspires.ftc.robotcore.external.Telemetry;

abstract public class QQTest {
    private String name;

    QQTest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void run(boolean on, Telemetry telemetry);

}
