package org.usfirst.frc5607.Vinny.subsystems;
import org.usfirst.frc5607.Vinny.Robot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.GenericHID;

public class Servos
{
    private int counter;
    Servo srvo;

    public Servos(){
        counter = 0;
        srvo = new Servo(1); 
        srvo.set(0);
    }
    public void start(){
        //if (Robot.oi.getXboxController1().getYButtonPressed()){
        if (Robot.oi.getXboxController1().getTriggerAxis(GenericHID.Hand.kLeft) > 0.20) {
            counter++;
            if ((counter % 2) == 0){
                srvo.set(0);
            }
            else{
                srvo.set(1);
            }
        }

    }
}