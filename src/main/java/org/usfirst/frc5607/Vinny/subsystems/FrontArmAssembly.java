package org.usfirst.frc5607.Vinny.subsystems;
import org.usfirst.frc5607.Vinny.Robot;
import org.usfirst.frc5607.Vinny.OI;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
public class FrontArmAssembly
{
  private static OI oi = Robot.oi;
  WPI_TalonSRX _talon = new WPI_TalonSRX(7);
  private static Spark _spark = new Spark(0);
  private SensorCollection pot = _talon.getSensorCollection();
	public FrontArmAssembly()
    {/* Config the sensor used for Primary PID and sensor direction */
        //_talon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 
          //                                  0,
			//	                            30);
 
		/* Ensure sensor is positive when output is positive */
		_talon.setSensorPhase(true);
		_talon.configFactoryDefault();
		_talon.setNeutralMode(NeutralMode.Brake);
		/**
		 * Set based on what direction you want forward/positive to be.
		 * This does not affect sensor phase. 
		 */ 
		_talon.setInverted(false);

		/* Config the peak and nominal outputs, 12V means full */
		//_talon.configNominalOutputForward(0,30);
		//_talon.configNominalOutputReverse(0,30);
		//_talon.configPeakOutputForward(1,30);
		//_talon.configPeakOutputReverse(-1,30);

		/**
		 * Config the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		//_talon.configAllowableClosedloopError(0, 0, 30);

		/* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
		//_talon.config_kF(0, 0.0, 30);
		//_talon.config_kP(0, 0.15, 30);
		//_talon.config_kI(0, 0.0, 30);
		//_talon.config_kD(0, 1.0, 30);

		/**
		 * Grab the 360 degree position of the MagEncoder's absolute
		 * position, and intitally set the relative sensor to match.
		 */
		//int absolutePosition = _talon.getSensorCollection().getPulseWidthPosition();

		/* Mask out overflows, keep bottom 12 bits */
		//absolutePosition &= 0xFFF;
		//if (true) { absolutePosition *= -1; }
		
		/* Set the quadrature (relative) sensor to match absolute */
		//_talon.setSelectedSensorPosition(absolutePosition, 0, 30);

    }
    public void start()
    {
		if(Robot.oi.getFrontArmLower()){
			_talon.set(ControlMode.PercentOutput, .5);
		}
		else{
			_talon.set(ControlMode.PercentOutput, 0);
		}
		if(Robot.oi.getFrontArmRaise()){
			_talon.set(ControlMode.PercentOutput, -0.5);
		}
		else{
			_talon.set(ControlMode.PercentOutput, 0);
		}
		if(Robot.oi.getFrontArmSpin()){
			_spark.setSpeed(0.5);
		}
		else{
			_spark.setSpeed(0);
		}
		SmartDashboard.putNumber("arm pot", pot.getAnalogIn());
    }
}
