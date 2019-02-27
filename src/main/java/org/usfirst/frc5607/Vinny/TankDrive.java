package org.usfirst.frc5607.Vinny;
import org.usfirst.frc5607.Vinny.OI;
import org.usfirst.frc5607.Vinny.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;

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

    // Previous Joystick readings to help prevent jerkiness
    private Double previousLeftReading = 0.0;
    private Double previousRightReading = 0.0;

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
    {   leftMaster.configFactoryDefault();
        leftMaster.setNeutralMode(NeutralMode.Brake);
        rightMaster.configFactoryDefault();
        rightMaster.setNeutralMode(NeutralMode.Brake);
        leftSlave1.follow(leftMaster);
        leftSlave2.follow(leftMaster);
        rightSlave1.follow(rightMaster);
        rightSlave2.follow(rightMaster);
        leftMaster.set(ControlMode.PercentOutput, 0);
        rightMaster.set(ControlMode.PercentOutput, 0);
    }
    public void Drive()
    {
        Double lJoystickSpeed = oi.getXboxController1().getY(GenericHID.Hand.kLeft);
        Double rJoystickSpeed = oi.getXboxController1().getY(GenericHID.Hand.kRight);
        if(Math.abs(lJoystickSpeed) > deadZone) 
		{
            lJoystickSpeed = -(lJoystickSpeed * 0.40);
            if (Math.abs(lJoystickSpeed - previousLeftReading) > 0.10) {
                if (lJoystickSpeed > previousLeftReading) {
                    lJoystickSpeed = previousLeftReading += 0.05;
                }
                else {
                    lJoystickSpeed = previousLeftReading -= 0.05;
                }
            }
            leftMaster.set(ControlMode.PercentOutput, lJoystickSpeed);
            previousLeftReading = lJoystickSpeed;
		}
		else if(Math.abs(lJoystickSpeed) < deadZone)
		{
			leftMaster.set(ControlMode.PercentOutput, 0);
		}
		
		//Sets Speed For Right Drive Motors
		if(Math.abs(rJoystickSpeed) > deadZone) 
		{
            rJoystickSpeed = rJoystickSpeed * 0.40;
            if (Math.abs(rJoystickSpeed - previousRightReading) > 0.10) {
                if (rJoystickSpeed > previousRightReading) {
                    rJoystickSpeed = previousRightReading += 0.05;
                }
                else {
                    rJoystickSpeed = previousRightReading += 0.05;
                }
            }
            rightMaster.set(ControlMode.PercentOutput, rJoystickSpeed);
            previousRightReading += rJoystickSpeed;
		}
		else if(Math.abs(rJoystickSpeed) < deadZone) 
		{
			rightMaster.set(ControlMode.PercentOutput, 0);
        }
        //double analogIn = ((pot.getAnalogIn() - 7) / 9.07);
        //SmartDashboard.putNumber("Potentiometer", analogIn);
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