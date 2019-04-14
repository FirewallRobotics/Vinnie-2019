package org.usfirst.frc5607.Vinny.subsystems;
import org.usfirst.frc5607.Vinny.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
public class BasketAssembly
{
    private static DoubleSolenoid basketSolenoid = Robot.firstSolenoid;
    private static Servo srvo;
    public BasketAssembly()
    {
        srvo = new Servo(1);
        raiseHandle();

    }

    public void dump()
    {
        basketSolenoid.set(DoubleSolenoid.Value.kForward);
        
    }
    public void lowerHandle() {
        srvo.set(0.4);
    } 
    public void raiseHandle() {
        srvo.set(1);
    }

    public void raise()
    {
        basketSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public void start(){
        if (Robot.oi.getBasketDump()){
            raiseHandle();
            dump();
        }
        if (Robot.oi.getBasketRaise()){
            raise();
        }
        if (Robot.oi.getLowerHandle()){
            lowerHandle();
        }
        if (Robot.oi.getRaiseHandle()){
            raiseHandle();
        }
    }
}


