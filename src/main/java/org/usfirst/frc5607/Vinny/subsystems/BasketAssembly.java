package org.usfirst.frc5607.Vinny.subsystems;
import org.usfirst.frc5607.Vinny.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class BasketAssembly
{
    private static DoubleSolenoid basketSolenoid = Robot.firstSolenoid;
    public BasketAssembly()
    {

    }

    public void dump()
    {
        basketSolenoid.set(DoubleSolenoid.Value.kForward);
        
    }

    public void raise()
    {
        basketSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
}


