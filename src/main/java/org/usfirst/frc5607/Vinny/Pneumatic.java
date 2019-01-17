package org.usfirst.frc5607.Vinny;
import edu.wpi.first.wpilibj.Solenoid;
import org.usfirst.frc5607.Vinny.Robot;
import org.usfirst.frc5607.Vinny.OI;

public class Pneumatic
{
    private static Solenoid firstSolenoid = Robot.testSolenoid;
    private static OI oi = Robot.oi;

    public Pneumatic()
    {

    }
    public void start()
    {
        firstSolenoid.set(oi.getXboxController1().getAButtonPressed());
    }
}
