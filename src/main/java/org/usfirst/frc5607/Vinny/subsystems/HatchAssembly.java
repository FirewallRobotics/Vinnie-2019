package org.usfirst.frc5607.Vinny.subsystems;
import org.usfirst.frc5607.Vinny.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class HatchAssembly
{
    private static DoubleSolenoid HatchSolenoid = Robot.secondSolenoid;
    public HatchAssembly(){
        
    }
    public void expand()
    {
        HatchSolenoid.set(DoubleSolenoid.Value.kForward);
    }    
    public void retract()
    {
        HatchSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void start(){
        if (Robot.oi.getHatchExpand()){
            expand();
        }
        if (Robot.oi.getHatchRetract()){
            retract();
        }
    }
}