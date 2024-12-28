package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class PreloadAuto extends QQOpMode{
    private final int AUTO_DELAY_SECS = 0;

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

    }
    public void start(){
        elapsedTime.reset();
    }
    public void loop(){
        super.loop();
        if(robot.scoreArm.isChamberContacted()){
            robot.scoreArm.goToScoring();
            isPlacing = true;
        }else if(!robot.scoreArm.isChamberContacted() && isPlacing && chamberContactWasPressed){
            contactLostPos = robot.scoreArm.getCurrentPos();
        }else if(isPlacing && robot.scoreArm.isTimeToReleaseClaw(contactLostPos)){
            robot.scoringClaw.open();
            isPlacing = false;
        }


        if(step == 0){
            robot.scoreArm.goToPlace();
            if(elapsedTime.seconds() > AUTO_DELAY_SECS){
                step = 1;
            }
        }else if(step == 1){
            boolean isDoneDriving = nav.driveToPositionIN(38, -61.5,0);
            if (isDoneDriving){
                step = 2;
            }
        }else if(step == 2){
            boolean isDoneDriving = nav.driveToPositionIN(14,-109.5,0);
            if (isDoneDriving){
                step = 3;
            }
        }else if (step == 3){
            robot.scoreArm.goToIntake();
        }
        chamberContactWasPressed = robot.scoreArm.isChamberContacted();
        clawWasClosed = robot.scoringClaw.isClawClosed();
    }
}



