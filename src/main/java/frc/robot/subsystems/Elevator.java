/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.TeleopMikeSmith;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;





public class Elevator extends Subsystem {
  
  private WPI_TalonSRX ElevatorTalonEncoder, ElevatorTalonNoEncoder;


public Elevator() {
   ElevatorTalonEncoder = new WPI_TalonSRX(RobotMap.ELEVATORTALONENCODER);
   ElevatorTalonNoEncoder = new WPI_TalonSRX(RobotMap.ELEVATORTALONNOENCODER);

  ElevatorTalonEncoder.setSelectedSensorPosition(0);
}
  public int getRawEncoderPos(){
 int encPos = -ElevatorTalonEncoder.getSelectedSensorPosition();

  return encPos;

  
  
  }
  public void setMotors(double speed){
    ElevatorTalonNoEncoder.set(speed);
  ElevatorTalonEncoder.set(-speed);

}


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
