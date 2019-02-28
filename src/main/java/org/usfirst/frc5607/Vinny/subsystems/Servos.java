package org.usfirst.frc5607.Vinny.subsystems;
import org.usfirst.frc5607.Vinny.Robot;
import edu.wpi.first.wpilibj.Servo;

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
        if(Robot.oi.getServoIn()){
            srvo.set(0);
        }
        if(Robot.oi.getServoOut()){
            srvo.set(1);
        }
    }
}