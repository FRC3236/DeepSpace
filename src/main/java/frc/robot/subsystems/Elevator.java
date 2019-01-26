package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import org.team3236.Conversion;

public class Elevator extends Subsystem {

	private WPI_TalonSRX talon, talonWithEncoder;
	public Conversion conversion;

	public Elevator() {
		talon = new WPI_TalonSRX(RobotMap.ELEVATORTALON);
		talonWithEncoder = new WPI_TalonSRX(RobotMap.ELEVATORTALONENC);

		talonWithEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		conversion = new Conversion(40, Conversion.Units.IN); // Change the 40 later //
	}

	public void zeroEncoder() {
		talonWithEncoder.setSelectedSensorPosition(0);
	}

	/**
	 * Gets the encoder value in ticks
	 * @return Distance of the encoder in ticks
	 */
	public int getRawEncoder() {
		return -talonWithEncoder.getSelectedSensorPosition();
	}

	/**
	 * Gets the encoder value in inches
	 * @return Distance of the encoder in inches
	 */
	public double getEncoder() {
		return conversion.getInches(getRawEncoder());
	}

	public void ascend(double speed) {
		talon.set(speed);
		talonWithEncoder.set(-speed);
	}

	public void descend(double speed) {
		talon.set(-speed);
		talonWithEncoder.set(speed);
	}

	public void set(double speed) {
		if (speed > 0) {

		} else {
			
		}
	}

	

	public void initDefaultCommand() {}
}