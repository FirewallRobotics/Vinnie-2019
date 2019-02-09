package org.usfirst.frc5607.Vinny.subsystems;
import org.usfirst.frc5607.Vinny.Robot;
import org.usfirst.frc5607.Vinny.OI;
import edu.wpi.first.wpilibj.Spark;

public class HatchCoverAssembly
{
    private static OI oi = Robot.oi;
    private static Spark _spark = new Spark(1);
    public HatchCoverAssembly()
    {
        
    }

    public void rotateForward()
    {
        //Need to put in stop code
        _spark.set(0.25);
    }
    

    public void rotateBackwards()
    {
        //Need to put in stop code
        _spark.set(-0.25);
    }
}
