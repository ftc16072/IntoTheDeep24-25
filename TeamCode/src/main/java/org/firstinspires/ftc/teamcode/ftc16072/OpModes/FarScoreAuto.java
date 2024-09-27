package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class FarScoreAuto extends QQOpMode{
    private double step = 0;
    public void init(){
        super.init();
        robot.otos.setOtosPosition(-31.5,-61.5,0);
    }
    public void loop(){
        super.loop();
        if(step == 0){
            boolean doneDriving = nav.driveToPositionIN(-31.5,-49,0);
            if(doneDriving){
                step = 1;
            }
        }else if(step == 1){
            boolean doneDriving = nav.driveToPositionIN(-52.14,-52.14,135);
            if(doneDriving){
                step = 2;
            }if(step == 2){
                robot.doubleReverse4Bar.setPosition(1);//NEEDS TO BE TUNED
                robot.slides.extend();
                robot.claw.open();
            }
        }
    }
}
