package org.usfirst.frc5607.Vinny.subsystems;

import org.usfirst.frc5607.Vinny.TankDrive;
import org.usfirst.frc5607.Vinny.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc5607.Vinny.OI;

public class ButtLift{
    private static DoubleSolenoid liftSol = new DoubleSolenoid(4,5);
    private OI oi = Robot.oi;
    private byte state = 0;
    
    public ButtLift(){
        liftSol.set(DoubleSolenoid.Value.kReverse);

    }
    public void start(){
        if(oi.getLiftState()){
            state++;
        }
        if((state % 2) == 1){
            state = 1;
            Robot.tDrive.speedScaleFactor = .2;
            liftSol.set(DoubleSolenoid.Value.kForward);
        }
        else{
            Robot.tDrive.speedScaleFactor = .75;
            liftSol.set(DoubleSolenoid.Value.kReverse);
        }
    }
}