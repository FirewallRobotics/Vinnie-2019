package org.usfirst.frc5607.Vinny;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc5607.Vinny.Robot;
import org.usfirst.frc5607.Vinny.OI;

public class Pneumatic
{
    private static OI oi = Robot.oi;
    private static DoubleSolenoid firstSolenoid = new DoubleSolenoid(0,1);

    public Pneumatic()
    {

    }
    public void start()
    {

        if(oi.getXboxController1().getBButtonPressed()){
            firstSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        else if (oi.getXboxController1().getAButtonPressed()){
            firstSolenoid.set(DoubleSolenoid.Value.kForward);
        }
        else{
            firstSolenoid.set(DoubleSolenoid.Value.kOff);
        }
    }
}
