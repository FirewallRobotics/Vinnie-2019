package org.usfirst.frc5607.Vinny;
import edu.wpi.first.wpilibj.Solenoid;
import org.usfirst.frc5607.Vinny.Robot;
import org.usfirst.frc5607.Vinny.OI;

public class Pneumatic
{
    private static OI oi = Robot.oi;
    private static Solenoid firstSolenoid = new Solenoid(7);

    public Pneumatic()
    {

    }
    public void start()
    {
        firstSolenoid.set(oi.getXboxController1().getAButtonPressed());
    }
}
