/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1827.robot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.cameraserver.CameraServer;

public class Robot extends TimedRobot {
	private static final int kFrontLeftChannel = 6;
	private static final int kRearLeftChannel = 5;
	private static final int kFrontRightChannel = 4;
	private static final int kRearRightChannel = 3;
	private static final int kArmBallMotor = 9;
	private static final int kArmLiftMotorRight = 8;
	private static final int kArmLiftMotorLeft = 7;
	private static final int pneumaticTestFwdCh = 0;
	private static final int pneumaticTestRevCh = 1;
	
	
	SpeedController frontLeft = new PWMVictorSPX(kFrontLeftChannel);
	SpeedController rearLeft = new PWMVictorSPX(kRearLeftChannel);
	SpeedController frontRight = new VictorSP(kFrontRightChannel);
	SpeedController rearRight = new VictorSP(kRearRightChannel);
	XboxController joystick = new XboxController(0);

	//IF CONTROLLER DOESN'T WORK, UN-COMMIT THIS DELETE THE xboxcontroller line 
	//Joystick joystick = new Joystick(0);


	SpeedController armBallMotor = new VictorSP(kArmBallMotor);
	SpeedController armLiftMotorRight = new VictorSP(kArmLiftMotorRight);
	SpeedController armLiftMotorLeft = new VictorSP(kArmLiftMotorLeft);
	//Solenoid armSol = new Solenoid(7,4); //creates a Solenoid object in slot 7, channel 4.
	
	DoubleSolenoid pusher = new DoubleSolenoid(6, 7);
	Solenoid climber = new Solenoid(5);
	Compressor c = new Compressor(0);
	

	Boolean exampleDoubleBoolean = true;
	Boolean pusherSolenoidCheck = true;
	Boolean climberBoolean = true;


	final double armUpSpeed = 0.75;
	final double armDownSpeed = 0.25;
	final double bumperTimerConstant = 1;
	final double solenoidFliperTimerConstant=1;
	final double solenoidPusherTimerConstant=1;
	final double climberTimer=1;
	double driveScaling = 2;

	KickerPistons kickerPistonControl = new KickerPistons();

	public void directUSBVision()
	{
		// From example project "Simple Vision":
		/**
		 * Uses the CameraServer class to automatically capture video from a USB webcam
		 * and send it to the FRC dashboard without doing any vision processing. This
		 * is the easiest way to get camera images to the dashboard. Just add this to
		 * the robotInit() method in your program.
		 */
		//CameraServer.getInstance().startAutomaticCapture();
		CameraServer server = CameraServer.getInstance();
		server.startAutomaticCapture();

	}


	
	@Override
	public void robotInit() {
		directUSBVision();
		//c.setClosedLoopControl(true);
		
		// solenoidContrLeft.set(DoubleSolenoid.Value.kReverse);
		// solenoidContrRight.set(DoubleSolenoid.Value.kReverse);
		kickerPistonControl.retract();
	}

	@Override
	public void teleopInit()
	{
		kickerPistonControl.retract();
	}

	@Override
	public void autonomousInit() {
		kickerPistonControl.retract();
	}

	public void armLiftControl(boolean upButton, boolean downButton)
	{
		if(upButton)
		{
			armLiftMotorLeft.set(-armUpSpeed);
			armLiftMotorRight.set(armUpSpeed);
		}
		else if(downButton)
		{
			armLiftMotorLeft.set(armDownSpeed);
			armLiftMotorRight.set(-armDownSpeed);
		}
		else
		{
			armLiftMotorLeft.set(0);
			armLiftMotorRight.set(0);
		}

	}

	

	public void tankDriveControl(double leftJoystickValue, double rightJoystickValue)
	{
		if(Math.abs(joystick.getY(Hand.kLeft)) > 0.15)
		{
			frontLeft.set(-joystick.getY(Hand.kLeft));
			rearLeft.set(-joystick.getY(Hand.kLeft));
		}
		else
		{
			frontLeft.set(0);
			rearLeft.set(0);
		}
		
		if(Math.abs(joystick.getY(Hand.kRight)) > 0.15)
		{
			frontRight.set(joystick.getY(Hand.kRight));
			rearRight.set(joystick.getY(Hand.kRight));
		}
		else
		{
			frontRight.set(0);
			rearRight.set(0);
		}
	}

	@Override
	public void teleopPeriodic()
	{
		armLiftControl(joystick.getBumper(Hand.kLeft), joystick.getBumper(Hand.kRight));		

		//kReverse = pistons out
		//kForward = pistons in
		kickerPistonControl.kick(joystick.getAButtonPressed());

		tankDriveControl(joystick.getY(Hand.kLeft), Math.abs(joystick.getY(Hand.kRight)));
	}
	
	@Override
	public void autonomousPeriodic()
	{
		armLiftControl(joystick.getBumper(Hand.kLeft), joystick.getBumper(Hand.kRight));		

		//kReverse = pistons out
		//kForward = pistons in
		kickerPistonControl.kick(joystick.getAButtonPressed());

		tankDriveControl(joystick.getY(Hand.kLeft), Math.abs(joystick.getY(Hand.kRight)));
	}
}
