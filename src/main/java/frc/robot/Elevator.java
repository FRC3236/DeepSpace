package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import org.team3236.Conversion;

public class Elevator extends Subsystem {

	private WPI_TalonSRX talon, talonWithEncoder, talon2, talonWithEncoder2;
	public Conversion conversion;

	private static final double MAXHEIGHT = 4000; // In ticks!

	public Elevator() {
		//In code for PowerUp, there was one talon MotorA and MotorB (which acted as an inverse)
		//In the original elevator code, talonWithEncoder acts as an inverse to regular talon so would the second motor be that or should i add another talon like in here?

		talon = new WPI_TalonSRX(RobotMap.ELEVATORTALON); //motor1
		
		talon2 = new WPI_TalonSRX(RobotMap.ELEVATORTALON2);
		
		talonWithEncoder = new WPI_TalonSRX(RobotMap.ELEVATORTALONENC); //motor1 encoder

		talonWithEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		talonWithEncoder2 = new WPI_TalonSRX(RobotMap.ELEVATORTALONENC2)
		
		//talonWithEncoder2.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		//FIGURE OUT IF YOU NEED THIS
		RobotMap.ELE
		conversion = new Conversion(40, Conversion.Units.IN); // Change the 40 later //
	}

	public void zeroEncoder() {
		talonWithEncoder.setSelectedSensorPosition(0);
		talonWithEncoder2.setSelectedSensorPosition(0);
	}

	public int getRawEncoder() {
		return -talonWithEncoder.getSelectedSensorPosition();
	}

	public double getEncoder() {
		return conversion.getInches(getRawEncoder());
	}

	public void ascend(double speed) {
		talon.set(speed);
		talon2.set(-speed);
		talonWithEncoder.set(-speed);
		talonWithEncoder2.set(speed);
	}

	public void descend(double speed) {
		// If the encoder is reading less than an inch from the bottom
		if (getEncoder() < 1) {
			talon.set(0);
			talon2.set(0);
			talonWithEncoder.set(0);
			talonWithEncoder2.set(0);
		} 
		else {
			talon.set(-speed);
			talon2.set(speed);
			talonWithEncoder.set(speed);
			talonWithEncoder2.set(-speed);
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