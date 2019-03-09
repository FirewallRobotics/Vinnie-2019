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
	private int manualpos = 0;
	private int _minTravel    = 786;
	private int _maxTravel    = 827;
	private int _rsHatchLow   = 799;
	private int _csHatch      = _rsHatchLow; /* Same as _rocketHatch now */
	private int _csCargo      = 803;
	private int _rsCargoLow   = 820;
	private int _rsHatchMid   = 816;
	private int targetPosition = _csCargo; /* Initialize in _csHatch Position */
	public Seesaw()
    {
		_talon.configFactoryDefault();
		/* Config the sensor used for Primary PID and sensor direction */
        //_talon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 
         //                                   0,
			//	                            30);
 
		/* Ensure sensor is positive when output is positive */
		_talon.setSensorPhase(true);
		_talon.setNeutralMode(NeutralMode.Brake);


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
		// min value is 786
		// max value is 827
        if(Math.abs(Robot.oi.getJoySpeed())< deadzone){
			if(Robot.oi.getLowerHatch()){ //rocket hatch
				seesawState = 1;
				targetPosition = _rsHatchLow;
			}
			if(Robot.oi.getHighHatch()){ // cargo hatch
				seesawState = 2;
				targetPosition = _csHatch;
			}
			if(Robot.oi.getCSCargoDeploy()){
				seesawState = 3;
				targetPosition = _csCargo;
			} 
			if(Robot.oi.getRSLowerCargo()){
				seesawState = 4;
				targetPosition = _rsCargoLow;
			}
			if(Robot.oi.getRSHigherHatch()){
				seesawState = 5;
				targetPosition = _rsHatchMid;
			}
			if (seesawState == 0) {
				targetPosition = manualpos;				
			}
			SmartDashboard.putNumber("seesaw state", seesawState);
			goToPosition(targetPosition, 0.08);
		}
		else{
			_talon.set(ControlMode.PercentOutput, Robot.oi.getJoySpeed() * -.3);
			seesawState = 0;
			manualpos = Math.round(pot.getAnalogIn());
		}
		SmartDashboard.putNumber("pot value", Math.abs(pot.getAnalogIn()));
	}
	
	public void goToPosition(int pos, double speed) {
		//_talon.setSelectedSensorPosition(840, 0, 30);\
		SmartDashboard.putNumber("Target Pos", pos);
		int potvalue = Math.abs(Math.round(pot.getAnalogIn()));
		if (potvalue >= _minTravel && potvalue <= _maxTravel)
		{
			if (potvalue < pos){
				_talon.set(ControlMode.PercentOutput, speed);
			}
			else if (potvalue > pos){
				_talon.set(ControlMode.PercentOutput, -speed);
			}
			else {
				speed = 0.15;
				_talon.set(ControlMode.PercentOutput, 0);
			}
		}
		else{
			_talon.set(ControlMode.PercentOutput, 0);
		}
	}
}
