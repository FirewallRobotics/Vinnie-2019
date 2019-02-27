package org.usfirst.frc5607.Vinny.subsystems;
import org.usfirst.frc5607.Vinny.Robot;
import org.usfirst.frc5607.Vinny.OI;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Seesaw
{
    private static OI oi = Robot.oi;
	WPI_TalonSRX _talon = new WPI_TalonSRX(8);
	private byte seesawState = 0;
	private SensorCollection pot = _talon.getSensorCollection();
	private Double deadzone = 0.15;
	public Seesaw()
    {
		/* Config the sensor used for Primary PID and sensor direction */
        //_talon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 
         //                                   0,
			//	                            30);
 
		/* Ensure sensor is positive when output is positive */
		_talon.setSensorPhase(true);
		_talon.configFactoryDefault();
		_talon.setNeutralMode(NeutralMode.Brake);
		//Make sure seesaw will not break gear when it goes to 824 or 812
		_talon.configForwardSoftLimitThreshold(824);
		_talon.configForwardSoftLimitEnable(true);
		_talon.configReverseSoftLimitThreshold(812);
		_talon.configReverseSoftLimitEnable(true);
		/**
		 * Set based on what direction you want forward/positive to be.
		 * This does not affect sensor phase. 
		 */ 
		_talon.setInverted(false);

		/* Config the peak and nominal outputs, 12V means full */
		//_talon.configNominalOutputForward(0,30);
		//_talon.configNominalOutputReverse(0,30);
		//_talon.configPeakOutputForward(0.20,30);
		//_talon.configPeakOutputReverse(-0.05,30);

		/**
		 * Config the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		//_talon.configAllowableClosedloopError(0, 1, 30);

		/* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
		//_talon.config_kF(0, 0.0, 30);
		//_talon.config_kP(0, 1, 30);
		//_talon.config_kI(0, 0.0, 30);
		//_talon.config_kD(0, 1.0, 30);

		/**
		 * Grab the 360 degree position of the MagEncoder's absolute
		 * position, and intitally set the relative sensor to match.
		 */
		//int absolutePosition = 840;
		//int absolutePosition = _talon.getSensorCollection().

		/* Mask out overflows, keep bottom 12 bits */
		//absolutePosition &= 0xFFF;
		//if (true) { absolutePosition *= -1; }
		
		/* Set the quadrature (relative) sensor to match absolute */
		//_talon.setSelectedSensorPosition(absolutePosition, 0, 30);
		//_talon.set(ControlMode.PercentOutput, 0);
    }
    public void start()
    {
        if(Math.abs(Robot.oi.getJoySpeed())< deadzone){

			if(Robot.oi.getLowerHatch()){
				seesawState = 1;
			}
			if(Robot.oi.getHighHatch()){
				seesawState = 2;
			}
			if(Robot.oi.getCSCargoDeploy()){
				seesawState = 3;
			}
			if(Robot.oi.getRSLowerCargo()){
				seesawState = 2;
			}
			if(Robot.oi.getRSHigherCargo()){
				seesawState = 4;
			}

			if (seesawState == 0) {
				_talon.set(ControlMode.PercentOutput, 0);
			}
			else if (seesawState == 1) { 
				goToPosition(817);
			}
			else if (seesawState == 2) { 
				goToPosition(824);
			}
			else if (seesawState == 3){
				goToPosition(818);
			}
			else if (seesawState == 4){
				goToPosition(812);
			}
			SmartDashboard.putNumber("seesaw state", seesawState);
		}
		else{
			_talon.set(ControlMode.PercentOutput, Robot.oi.getJoySpeed() * -0.2);
			seesawState = 0;
		}
		SmartDashboard.putNumber("pot value", Math.abs(pot.getAnalogIn()));
	}
	
	public void goToPosition(int pos) {
		//_talon.setSelectedSensorPosition(840, 0, 30);\
		SmartDashboard.putNumber("Target Pos", pos);
		int potvalue = Math.abs(Math.round(pot.getAnalogIn()));
		if (potvalue < pos){
			_talon.set(ControlMode.PercentOutput, 0.1);
		}
		else if (potvalue > pos){
			_talon.set(ControlMode.PercentOutput, -0.1);
		}
		else {
			_talon.set(ControlMode.PercentOutput, 0);
		}
	}
}
