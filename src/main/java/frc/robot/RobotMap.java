/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;
  public static int LEFTXBOX = 0; 
  public static int RIGHTXBOX = 1; 

  public static int LEFTTALONA = 0;
  public static int LEFTTALONB = 1; 
  public static int RIGHTTALONA = 2; 
  public static int RIGHTTALONB = 3; 


  public static int ELEVATORTALON = 4;
  public static int ELEVATORTALONENC = 5;
  
  public static int HATCHLEVELONE = 500;
  public static int HATCHLEVELTWO = 1200;
  public static int HATCHLEVELTHREE = 3000;

  public static int CARGOLEVELONE = 600;
  public static int CARGOLEVELTWO = 1000;
  public static int CARGOLEVELTHREE = 2200;
  public static int CARGOLEVELSHIP = 3500;
  public static final int ACTUATOR = 7;




  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
