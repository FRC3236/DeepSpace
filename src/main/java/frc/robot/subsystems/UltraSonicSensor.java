/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.AnalogInput;
import java.math.BigDecimal;

/**
 * Add your docs here.
 */
public class UltraSonicSensor {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  SerialPort UltraSonic_Sensor;
  AnalogInput UltraSonicAnalog;
  AnalogInput BaselineVoltage;

  public String getUltrasonic()
  {
    // Initialize the Ultrasonic Sensor
    try {
      // These values are fairly arbitrary
      UltraSonicAnalog = new AnalogInput(1);
      BaselineVoltage = new AnalogInput(2);

      // Maximum sample rate which is a multiple of 10mS
      AnalogInput.setGlobalSampleRate(44400);

    } 
    catch (RuntimeException ex) {
      // We probably already initialized the sensor
      System.out.println("Could not start Ultrasonic");
    }    
    
    // RoboRIO Anlog supply voltage
    double PSUVoltage = BaselineVoltage.getVoltage();

    // Voltage -> Meter scale in Volt/Meters
    //(~0.976V/M for 5.00V, according to documentation)
    // 1.14045 V/M seems to work better (determined experimentally)
    BigDecimal AnalogOutputScale = new BigDecimal("1.14045");


    // Java doubles are weird; use BigDecimal
    double double_volts = UltraSonicAnalog.getAverageVoltage();
    BigDecimal volts = BigDecimal.valueOf(double_volts);

    // Round value to 2 digits(1cm)
    BigDecimal full_distance = (volts.multiply(AnalogOutputScale));    
    BigDecimal distance = full_distance.setScale(3, BigDecimal.ROUND_CEILING);

    String strDistance = distance+"m"; // Shitty way to convert Double to String
    
    // Provide driver with live updates on SmartDashboard
    SmartDashboard.putString("Output Voltage:", volts.toString());
    SmartDashboard.putNumber("PSU Voltage:", PSUVoltage);
    SmartDashboard.putString("Volts/Meter:", AnalogOutputScale.toString()+"V/M");
    SmartDashboard.putString("Distance:", strDistance);

    return strDistance;
  } 
}
