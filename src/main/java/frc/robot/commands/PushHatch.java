/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CommandBase;

public class PushHatch extends Command {
	private Timer timer;
	public PushHatch() {
		timer = new Timer();
		timer.reset();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		CommandBase.hatch.retractPistons();
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		CommandBase.hatch.extendPistons();
		if (timer.get() > 0.5) {
			CommandBase.hatch.retractPistons();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return timer.get() > 0.6;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		timer.stop();
		CommandBase.hatch.retractPistons();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
