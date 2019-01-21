package org.team3236.kop;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.StopBits;

public class Ultrasonic {
	private SerialPort serialPort;
	private Thread rangingThread;
	private String distance = "0";

	public Ultrasonic(int port) {
		try {
			serialPort = new SerialPort(port, SerialPort.Port.kOnboard, 8, Parity.kNone, StopBits.kOne);
		} catch (RuntimeException ex) {
			throw ex;
		}

		serialPort.setTimeout(2);
		serialPort.reset();

		// Start the thread //
		rangingThread = new Thread(this::rangingLoop);
		rangingThread.start();
		
	}

	public double getDistance() {
		return 0.0;
	}

	// Method to run in the thread //
	private void rangingLoop() {
		while (true) {
			String response;
			try {
				response = serialPort.readString(5);
				//System.out.println("Serial port: " + response);
			} catch (StringIndexOutOfBoundsException ex) {
				ex.printStackTrace();
				continue;
			}

		}
	}
}