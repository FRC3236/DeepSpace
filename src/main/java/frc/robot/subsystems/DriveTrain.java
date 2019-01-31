/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import java.math.BigDecimal;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */

public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX LeftTalonA = new WPI_TalonSRX(RobotMap.LEFTTALONA);
  private WPI_TalonSRX LeftTalonB = new WPI_TalonSRX(RobotMap.LEFTTALONB);
  private WPI_TalonSRX RightTalonA = new WPI_TalonSRX(RobotMap.RIGHTTALONA);
  private WPI_TalonSRX RightTalonB = new WPI_TalonSRX(RobotMap.RIGHTTALONB);
  SerialPort UltraSonic_Sensor;
  AnalogInput UltraSonicAnalog;
  AnalogInput BaselineVoltage;

  public void Initialize() {
    
}

  public void InvertTalons()
  {
    RightTalonA.setInverted(true);
    RightTalonB.setInverted(true);
  }

  public void SetLeft(double speed){
    LeftTalonA.set(speed);
    LeftTalonB.set(speed);
  }

  public void SetRight(double speed){
    RightTalonA.set(speed);
    RightTalonB.set(speed);
  }

  public void Drive(double leftSpeed, double rightSpeed){
    SetLeft(leftSpeed);
    SetRight(rightSpeed);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void resetUltrasonic()
  {

  }

  public void getUltrasonic()
  {
    try {
      UltraSonicAnalog = new AnalogInput(1);
      UltraSonicAnalog.setOversampleBits(3);
      UltraSonicAnalog.setAverageBits(50);
      BaselineVoltage = new AnalogInput(2);

      AnalogInput.setGlobalSampleRate(44400);

    } catch (RuntimeException ex) {
      System.out.println("Could not start Ultrasonic");
    }    
    double PSUVoltage = BaselineVoltage.getVoltage();
    SmartDashboard.putNumber("PSU Voltage:", PSUVoltage);

    // Voltage -> Meter scale in Volt/Meters
    //(~0.976V/M for 5.00V)
    //BigDecimal AnalogOutputScale = new BigDecimal((PSUVoltage/1024)*200);
    BigDecimal AnalogOutputScale = new BigDecimal("1.14045"); // Determined experimentally
    SmartDashboard.putString("Volts/Meter:", AnalogOutputScale.toString()+"V/M");

    double double_volts = UltraSonicAnalog.getAverageVoltage();
    BigDecimal volts = BigDecimal.valueOf(double_volts);

    SmartDashboard.putString("Volts:", volts.toString());
    BigDecimal full_distance = (volts.multiply(AnalogOutputScale));    
    BigDecimal distance = full_distance.setScale(3, BigDecimal.ROUND_CEILING);

    String strDistance = distance+"m"; // Shitty way to convert Double to String TODO: Make this prettier
    SmartDashboard.putString("Distance:", strDistance);
  } 
}

