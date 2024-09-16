package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class CloseParkAuto extends QQOpMode{
    private double step = 0;
    public void init(){
        super.init();
        robot.otos.setOtosPosition(13.5,-61.5,0);
    }
    public void loop(){
        if(step == 0){
            boolean doneDriving = nav.driveToPositionIN(13.5,-60,0);
            if(doneDriving){
                step = 1;
            }
        }else if(step == 1){
            boolean doneDriving = nav.driveToPositionIN(67,-60,0);
            if(doneDriving){
                step = 2;
            }if(step == 2){
                telemetry.addData("parked", 0);
            }
        }
    }
}
