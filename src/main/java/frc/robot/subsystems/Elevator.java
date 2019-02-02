package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import org.team3236.Conversion;

public class Elevator extends Subsystem {

	private WPI_TalonSRX talon, talonWithEncoder;
	public Conversion conversion;

	private static final double MAXHEIGHT = 4000; // In ticks!

	public Elevator() {
		talon = new WPI_TalonSRX(RobotMap.ELEVATORTALON);
		talonWithEncoder = new WPI_TalonSRX(RobotMap.ELEVATORTALONENC);

		talonWithEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		conversion = new Conversion(40, Conversion.Units.IN); // Change the 40 later //
	}

	public void zeroEncoder() {
		talonWithEncoder.setSelectedSensorPosition(0);
	}

	public int getRawEncoder() {
		return -talonWithEncoder.getSelectedSensorPosition();
	}

	public double getEncoder() {
		return conversion.getInches(getRawEncoder());
	}

	public void ascend(double speed) {
		talon.set(speed);
		talonWithEncoder.set(-speed);
	}

	public void descend(double speed) {
		// If the encoder is reading less than an inch from the bottom
		if (getEncoder() < 1) {
			talon.set(0);
			talonWithEncoder.set(0);
		} else {
			talon.set(-speed);
			talonWithEncoder.set(speed);
		}
	}

	public void goTo(double rawPosition, double rawSpeed) {
		// Set the position to the top if its higher than the maxheight
		double destination = Math.min(rawPosition, MAXHEIGHT);
		double current = getRawEncoder();
	}

	public void set(double speed) {
		if (speed > 0) {

		} else {
			
		}
	}

	

	public void initDefaultCommand() {}
}