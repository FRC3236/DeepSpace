	public TeleopDefault() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		// Delete me
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double forwardSpeed, lateralSpeed; 
		forwardSpeed = CommandBase.controls.Driver.getY(Hand.kLeft);
		lateralSpeed = CommandBase.controls.Driver.getX(Hand.kRight);
		
		CommandBase.visionRocket.GetContourPairs();

		CommandBase.drivetrain.Drive(lateralSpeed - forwardSpeed, lateralSpeed + forwardSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}