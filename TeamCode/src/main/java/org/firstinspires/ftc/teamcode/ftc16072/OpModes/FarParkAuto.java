package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class FarParkAuto extends QQOpMode{
    private double step = -1;
    ElapsedTime elapsedTime = new ElapsedTime();
    public final int autodelay = 23;
    public void init(){
        super.init();
        robot.otos.setOtosPosition(-31.5,-61.5,0);
    }
    public void start(){
        elapsedTime.reset();
    }
    public void loop(){
        if(step == 0){
            if(elapsedTime.seconds() > autodelay){
                step = 1;
            }
        }
        else if(step == 1){
            boolean doneDriving = nav.driveToPositionIN(-31.5,-60,0);
            if(doneDriving){
                step = 2;
            }
        }else if(step == 2){
            boolean doneDriving = nav.driveToPositionIN(62,-60,0);
            if(doneDriving){
                step = 3;
            }if(step == 3){
                robot.scoreArm.goToIntake();
            }
        }
    }
}
