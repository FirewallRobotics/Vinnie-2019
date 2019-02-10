//not sure if using spark or servo
package org.usfirst.frc5607.Vinny.subsystems;
import org.usfirst.frc5607.Vinny.Robot;
import org.usfirst.frc5607.Vinny.OI;
//import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.GenericHID.Hand;
public class HatchCoverAssembly
{
    private static OI oi = Robot.oi;
    //private static Spark _spark = new Spark(1);
    private static Servo _Servo = new Servo(1);
    public HatchCoverAssembly()
    {
        
    }

    public void rotateForward()
    {
        //Need to put in stop code
        //_spark.set(0.25);
        _Servo.setAngle(180);
    }
    

    public void rotateBackwards()
    {
        //Need to put in stop code
        //_spark.set(-0.25);
        _Servo.setAngle(0);
    }
    public void start()
    {
        if(oi.getXboxController1().getTriggerAxis(Hand.kRight)>0.20){
            rotateForward();
        }
        else if(oi.getXboxController1().getTriggerAxis(Hand.kLeft)>0.20){
            rotateBackwards();
            
        }

    }
}
    
        

