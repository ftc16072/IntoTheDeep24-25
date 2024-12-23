package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class NavigationPIDTuner extends QQOpMode{
    private final int autodelay = 0;

    ElapsedTime elapsedTime = new ElapsedTime();
    private double step = 0;
    boolean isPlacing;
    boolean chamberContactWasPressed;
    int contactLostPos;
    boolean clawWasClosed;
    public void init(){
        super.init();
        robot.otos.setOtosPosition(7,-61.5,0);
        robot.scoringClaw.close();
        robot.intakeClaw.wristFlat();
    }
    public void start(){
        elapsedTime.reset();
    }
    public void loop(){
        super.loop();

        if(step == 0){
            boolean isDoneDriving = nav.driveToPositionIN(7.5, -37.5,0);
            if (isDoneDriving){
                step = 1;
            }
        }else if(step == 1){
            boolean isDoneDriving = nav.driveToPositionIN(7.5,-85.5,0);
            if (isDoneDriving){
                step = 0;
            }
        }
        chamberContactWasPressed = robot.scoreArm.isChamberContacted();
        clawWasClosed = robot.scoringClaw.isClawClosed();
    }
}



