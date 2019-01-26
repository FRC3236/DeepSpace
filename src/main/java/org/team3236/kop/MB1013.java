package org.team3236.kop;

import edu.wpi.first.wpilibj.AnalogInput;
import org.team3236.Conversion;
// MaxBotix MB1013 Ultrasonic Sensor //
public class MB1013 {

	private AnalogInput ultrasonicPort;
	private Conversion conversionFactor;

	// Default constructor, no arguments //
	public MB1013() {
		ultrasonicPort = new AnalogInput(1);

		ultrasonicPort.setAverageBits(50);
		ultrasonicPort.setOversampleBits(3);

		conversionFactor = new Conversion(1, Conversion.Units.M);
		AnalogInput.setGlobalSampleRate(44400);
	}

	// Constructor with custom ports defined //
	public MB1013(int ultrasonic, int baseline) {
		ultrasonicPort = new AnalogInput(ultrasonic);

		AnalogInput.setGlobalSampleRate(44400);
	}

	// Retuns in inches! //
	public double getDistance() {

		double volts = ultrasonicPort.getAverageVoltage();
		//double distance = conversionFactor.getInches(volts);
		double distance = volts * 1.14045;

		return distance;
	}

}