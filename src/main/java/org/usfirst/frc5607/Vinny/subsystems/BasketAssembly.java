package org.usfirst.frc5607.Vinny.subsystems;
import org.usfirst.frc5607.Vinny.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;

public class BasketAssembly
{
    private static DoubleSolenoid basketSolenoid = Robot.firstSolenoid;
    private int counter = 0;

    public BasketAssembly()
    {
        counter = 0;
    }

    public void start()
    {
        /*if (Robot.oi.getXboxController1().getBumperPressed(GenericHID.Hand.kRight)) {
            counter++;
            if ((counter % 2) == 0){
                dump();
            }
            else{
                raise();
            }
        }*/
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


