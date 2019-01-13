package org.usfirst.frc5607.Vinny;
import org.usfirst.frc5607.Vinny.OI;
import org.usfirst.frc5607.Vinny.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.GenericHID;


public class TankDrive
{
    private static OI oi = Robot.oi;
    private TalonSRX leftMaster = new TalonSRX(1);
    private TalonSRX leftSlave1 = new TalonSRX(2);
    private VictorSPX leftSlave2 = new VictorSPX(3);
    private TalonSRX rightMaster = new TalonSRX(4);
    private TalonSRX rightSlave1 = new TalonSRX(5);
    private VictorSPX rightSlave2 = new VictorSPX(6);

    private Double deadZone = 0.2;

    public TankDrive()
    {
        leftSlave1.follow(leftMaster);
        leftSlave2.follow(leftMaster);
        rightSlave1.follow(rightMaster);
        rightSlave2.follow(rightMaster);
        leftMaster.set(ControlMode.PercentOutput, 0);
        rightMaster.set(ControlMode.PercentOutput, 0);
    }
    public void Drive()
    {
        double lJoystickSpeed = oi.getXboxController1().getY(GenericHID.Hand.kLeft);
        double rJoystickSpeed = oi.getXboxController1().getY(GenericHID.Hand.kRight);
        if(Math.abs(lJoystickSpeed) > deadZone) 
		{
			leftMaster.set(ControlMode.PercentOutput, lJoystickSpeed * -1);
		}
		else if(Math.abs(lJoystickSpeed) < deadZone)
		{
			leftMaster.set(ControlMode.PercentOutput, 0);
		}
		
		//Sets Speed For Right Drive Motors
		if(Math.abs(rJoystickSpeed) > deadZone) 
		{
			rightMaster.set(ControlMode.PercentOutput, rJoystickSpeed);
		}
		else if(Math.abs(rJoystickSpeed) < deadZone) 
		{
			rightMaster.set(ControlMode.PercentOutput, 0);
		}
    }
}