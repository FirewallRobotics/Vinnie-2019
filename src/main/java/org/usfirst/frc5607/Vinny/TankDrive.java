package org.usfirst.frc5607.Vinny;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;


public class TankDrive
{
    TalonSRX leftMaster = new TalonSRX(1);
    TalonSRX leftSlave1 = new TalonSRX(2);
    VictorSPX leftSlave2 = new VictorSPX(3);
    TalonSRX rightMaster = new TalonSRX(4);
    TalonSRX rightSlave1 = new TalonSRX(5);
    VictorSPX rightSlave2 = new VictorSPX(6);

    XboxController controller1 = new XboxController(0);
    Double deadZone = 0.2;

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
        double lJoystickSpeed = controller1.getY(GenericHID.Hand.kLeft);
        double rJoystickSpeed = controller1.getY(GenericHID.Hand.kRight);
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