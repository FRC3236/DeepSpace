package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import org.team3236.Conversion;

public class Elevator extends Subsystem {

	private WPI_TalonSRX talon, talonWithEncoder;
	public Conversion conversion;

	private static final double HOLDINGCONSTANT = 0.25; // The speed at which the motors stall
	// Increase/decrease the holding constant to 
	private static final double CAPTURERANGE = 0.7;
	private static final int MAXHEIGHT = 4000; // In ticks

	public Elevator() {
		talon = new WPI_TalonSRX(RobotMap.ELEVATORTALON);
		talonWithEncoder = new WPI_TalonSRX(RobotMap.ELEVATORTALONENC);

		talonWithEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		conversion = new Conversion(82.75, Conversion.Units.IN); // Change the 40 later //
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
		double encoder = getRawEncoder();
		if (encoder / MAXHEIGHT > CAPTURERANGE && speed > 0.05) {
			double captureHeight = MAXHEIGHT * CAPTURERANGE;
			double motorSpeed = Math.min(gaston(MAXHEIGHT, speed, captureHeight), speed);
			
			talon.set(-motorSpeed);
			talonWithEncoder.set(motorSpeed);
		} else {
			talon.set(-speed);
			talonWithEncoder.set(speed);
		}
	}
	
	public void descend(double speed) {
		// If the encoder is reading less than an inch from the bottom
		if (getEncoder() < 15) {
			talon.set(0);
			talonWithEncoder.set(0);
		} else {
			talon.set(-speed);
			talonWithEncoder.set(speed);
		}
	}

	// Gastons equation //
	public double gaston(double destination, double rawSpeed) {
		int encoder = getRawEncoder();
		
		double holdVal = HOLDINGCONSTANT;
		if (encoder > destination) {
			holdVal -= .1;
			rawSpeed /= 2;
		}

		double speed = (((destination - encoder)/destination)*Math.abs(rawSpeed)) + holdVal;
		if (speed < 0) {
			return Math.max(-1, speed);
		} else {
			return Math.min(1, speed);
		}
	}

	// Gaston's equation with custom offset
	public double gaston(double destination, double rawSpeed, double offset) {

		double encoder = getRawEncoder() - offset;
		destination = destination - offset;

		double holdVal = HOLDINGCONSTANT;
		// Add more going down to increase accuracy
		if (encoder > destination) {
			holdVal -= .1;
			rawSpeed /= 2;
		}

		double speed = (((destination - encoder)/destination)*Math.abs(rawSpeed)) + holdVal;

		if (speed < 0) {
			return Math.max(-1, speed);
		} else {
			return Math.min(1, speed);
		}
	}

	public void goTo(double destination, double rawSpeed) {
		// Set the position to the top if its higher than the maxheight
		destination = Math.min(destination, MAXHEIGHT);
		double speed = gaston(destination, rawSpeed);

		SmartDashboard.putNumber("Elevator Speed", speed);

		talon.set(-speed);
		talonWithEncoder.set(speed);
	}

	public void set(double speed) {
		if (speed >= 0) {
			ascend(speed);
		} else {
			descend(speed);
		}
	}

	

	public void initDefaultCommand() {}
}