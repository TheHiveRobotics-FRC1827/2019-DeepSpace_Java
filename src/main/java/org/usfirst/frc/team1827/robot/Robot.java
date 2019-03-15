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
	
	//Solenoid armSol = new Solenoid(7,4); //creates a Solenoid object in slot 7, channel 4.
	
	//DoubleSolenoid pusher = new DoubleSolenoid(6, 7);
	//Solenoid climber = new Solenoid(5);
	Compressor c = new Compressor(0);
	

	Boolean exampleDoubleBoolean = true;
	Boolean pusherSolenoidCheck = true;
	Boolean climberBoolean = true;


	
	final double bumperTimerConstant = 1;
	final double solenoidFliperTimerConstant=1;
	final double solenoidPusherTimerConstant=1;
	final double climberTimer=1;
	double driveScaling = 2;

	KickerPistons kickerPistonControl = new KickerPistons();
	Lifter lifterArm = new Lifter();
	HiveRobotCamera robotCamera = new HiveRobotCamera();


	@Override
	public void robotInit()
	{
		robotCamera.initialize();
		kickerPistonControl.retract();
		lifterArm.stop();
	}

	@Override
	public void teleopInit()
	{
		kickerPistonControl.retract();
		lifterArm.stop();
	}

	@Override
	public void autonomousInit() {
		kickerPistonControl.retract();
		lifterArm.stop();
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
		// TODO should this be in its own class or done directly in teleop?
		if(joystick.getBumper(Hand.kLeft))
        {
            lifterArm.lift();
        }
        else if(joystick.getBumper(Hand.kRight))
        {            
            lifterArm.goDown();
        }
        else
        {
            lifterArm.stop();
        }		

		//kReverse = pistons out
		//kForward = pistons in
		kickerPistonControl.kick(joystick.getAButtonPressed());

		tankDriveControl(joystick.getY(Hand.kLeft), Math.abs(joystick.getY(Hand.kRight)));
	}
	
	@Override
	public void autonomousPeriodic()
	{	
		// TODO should this be in its own class or done directly in teleop?
		if(joystick.getBumper(Hand.kLeft))
        {
            lifterArm.lift();
        }
        else if(joystick.getBumper(Hand.kRight))
        {            
            lifterArm.goDown();
        }
        else
        {
            lifterArm.stop();
        }		

		//kReverse = pistons out
		//kForward = pistons in
		kickerPistonControl.kick(joystick.getAButtonPressed());

		tankDriveControl(joystick.getY(Hand.kLeft), Math.abs(joystick.getY(Hand.kRight)));
	}
}
