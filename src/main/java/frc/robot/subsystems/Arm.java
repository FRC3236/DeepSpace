/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.*;
import org.team3236.DriveTrainMode;
import org.team3236.Conversion;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

/**
 * Add your docs here.
 */

public class Arm extends Subsystem {
  private WPI_TalonSRX Actuator = new WPI_TalonSRX(RobotMap.ACTUATOR);
  private AnalogPotentiometer potentiometer = new AnalogPotentiometer(0);
  public void setArm(double speed){
    Actuator.set(speed);
  }
  

  @Override
  public void initDefaultCommand() {}
}
