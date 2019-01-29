package org.usfirst.frc5607.Vinny;
import org.usfirst.frc5607.Vinny.OI;
import org.usfirst.frc5607.Vinny.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.GenericHID;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
=======
import edu.wpi.first.networktables.*;
>>>>>>> added initial test for vision code


public class TankDrive
{
    private static OI oi = Robot.oi;
    private TalonSRX leftMaster = new TalonSRX(1);
    private TalonSRX leftSlave1 = new TalonSRX(2);
    private VictorSPX leftSlave2 = new VictorSPX(3);
    private TalonSRX rightMaster = new TalonSRX(4);
    private TalonSRX rightSlave1 = new TalonSRX(5);
    private VictorSPX rightSlave2 = new VictorSPX(6);
    private SensorCollection pot = leftMaster.getSensorCollection();

    private Double deadZone = 0.2;

    private NetworkTableInstance inst = NetworkTableInstance.getDefault();
    private NetworkTable table = inst.getTable("SmartDashboard");
    private NetworkTableEntry x = table.getEntry("X");
    private NetworkTableEntry y = table.getEntry("Y");
    private NetworkTableEntry radius = table.getEntry("Radius");

    private double MaxRadius = 60;
    private double MinRadius = 10;
    private double MaxOutR = 1;
    private double MinOutR = -1;
    private double NeutralOffSetR= 0.25;
    private double PGainR = 0.250;
    private double MaxX = 160;
    private double MinX = 10;
    private double Maxx = 1;
    private double Minx= -1;
    private double NeutralOffSetX= 0;
    private double PGainX = 0.25;
    private double ScaledX = 0;
    private double ScaledY = 0;
    private double ScaledRadius = 0;


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
        double analogIn = ((pot.getAnalogIn() - 7) / 9.07);
        SmartDashboard.putNumber("Potentiometer", analogIn);
    }
    public void autonomous(){
        double X = Math.round(x.getDouble(-1));
        double Y = Math.round(y.getDouble(-1));
        double Radius =  radius.getDouble(-1);
        if(X == -1){
         ScaledX = 0;
         ScaledY = 0;
         ScaledRadius = 0;
        } else {
        ScaledX =  PGainX * ((((Maxx - Minx)*((X- MinX)/(MaxX - MinX))) + Minx) -  NeutralOffSetX);
        ScaledRadius =  PGainR * ((((MaxOutR - MinOutR)*((Radius - MinRadius)/(MaxRadius - MinRadius))) + MinOutR) -  NeutralOffSetR);
        }
        
        Double LeftSpeed = -(ScaledRadius +  ScaledX) - 0.03;
        Double RightSpeed = ScaledRadius -  ScaledX;
        //System.out.println("ScaledX: "+ ScaledX + " ScaledRadius: " + ScaledRadius);
        System.out.println("LeftSpeed: "+ LeftSpeed + " RightSpeed: " + RightSpeed);
        leftMaster.set(ControlMode.PercentOutput,  -RightSpeed);
        rightMaster.set(ControlMode.PercentOutput, -LeftSpeed);

    }
}