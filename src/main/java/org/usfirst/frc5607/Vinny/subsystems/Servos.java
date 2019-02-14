package org.usfirst.frc5607.Vinny.subsystems;
import org.usfirst.frc5607.Vinny.Robot;
import edu.wpi.first.wpilibj.Servo;

public class Servotest
{
    private int counter;
    Servo srvo;

    public Servotest(){
        counter = 0;
        srvo = new Servo(1); 
        srvo.set(0);
    }
    public void start(){
        if (Robot.oi.getXboxController1().getYButtonPressed()){
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