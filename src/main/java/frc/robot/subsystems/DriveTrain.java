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
    try {
      UltraSonicAnalog = new AnalogInput(1);
      BaselineVoltage = new AnalogInput(2);

      AnalogInput.setGlobalSampleRate(44400);

    } catch (RuntimeException ex) {
      System.out.println("Could not start Ultrasonic");
    }    
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
    UltraSonic_Sensor.reset();
  }

  public String getUltrasonic(){
    double PSUVoltage = BaselineVoltage.getVoltage();
    SmartDashboard.putNumber("PSU Voltage:", PSUVoltage);

    BigDecimal AnalogOutputScale = new BigDecimal((PSUVoltage/1024)*200);
    SmartDashboard.putString("Volts/Meter:", AnalogOutputScale.toString()+"V/M");

    double double_volts = UltraSonicAnalog.getVoltage();
    BigDecimal volts = BigDecimal.valueOf(double_volts);

    SmartDashboard.putString("Volts:", volts.toString());

    BigDecimal distance = (volts.multiply(AnalogOutputScale));    
    String strDistance = distance+"m"; // Shitty way to convert Double to String TODO: Make this prettier
    SmartDashboard.putString("Distance:", strDistance);
    return strDistance;
  } 
}

