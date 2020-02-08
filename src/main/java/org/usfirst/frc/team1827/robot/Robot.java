/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1827.robot;
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.*;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.cscore.MjpegServer;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.cameraserver.CameraServer;

// import edu.wpi.first.networktables;
// missing, not sure why. Don't think we need it anyway. -Jackson

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
	private static final int kFrontLeftChannel = 4;
	private static final int kRearLeftChannel = 2;
	private static final int kFrontRightChannel = 3;
	private static final int kRearRightChannel = 1;
	private static final int kArmBallMotor = 9;
	private static final int kArmLiftMotorRight = 8;
	private static final int kArmLiftMotorLeft = 7;
	//private static final int pneumaticTestFwdCh = 0;
	//private static final int pneumaticTestRevCh = 1;
	
	
	SpeedController frontLeft = new Spark(kFrontLeftChannel);
	SpeedController rearLeft = new Spark(kRearLeftChannel);
	SpeedController frontRight = new Spark(kFrontRightChannel);
	SpeedController rearRight = new Spark(kRearRightChannel);
	XboxController joystick = new XboxController(0);

	//IF CONTROLLER DOESN'T WORK, UN-COMMIT THIS DELETE THE xboxcontroller line 
	//Joystick joystick = new Joystick(0);


	SpeedController armBallMotor = new VictorSP(kArmBallMotor);
	SpeedController armLiftMotorRight = new VictorSP(kArmLiftMotorRight);
	SpeedController armLiftMotorLeft = new VictorSP(kArmLiftMotorLeft);
	//Solenoid armSol = new Solenoid(7,4); //creates a Solenoid object in slot 7, channel 4.
	DoubleSolenoid solenoidContrLeft = new DoubleSolenoid(0, 1);
	DoubleSolenoid solenoidContrRight = new DoubleSolenoid(2,3);
	DoubleSolenoid pusher = new DoubleSolenoid(6, 7);
	Solenoid climber = new Solenoid(5);
	Compressor c = new Compressor(0);
	Timer timer = new Timer();
	Timer flapperTimer = new Timer();

	Boolean exampleDoubleBoolean = true;
	Boolean pusherSolenoidCheck = true;
	Boolean climberBoolean = true;


	final double armUpSpeed = 0.45;
	final double bumperTimerConstant = 1;
	final double solenoidFliperTimerConstant=1;
	final double solenoidPusherTimerConstant=1;
	final double climberTimer=1;
	double driveScaling = 2;
	boolean climberState=false;
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
		
		solenoidContrLeft.set(DoubleSolenoid.Value.kReverse);
		solenoidContrRight.set(DoubleSolenoid.Value.kReverse);
		pusher.set(DoubleSolenoid.Value.kReverse);
		climberState=false;
	}

	@Override
	public void teleopInit()
	{
		solenoidContrLeft.set(DoubleSolenoid.Value.kReverse);
		solenoidContrRight.set(DoubleSolenoid.Value.kReverse);
		pusher.set(DoubleSolenoid.Value.kReverse);
		climberState=false;
	}

	@Override
	public void autonomousInit() {
		solenoidContrLeft.set(DoubleSolenoid.Value.kReverse);
		solenoidContrRight.set(DoubleSolenoid.Value.kReverse);
		pusher.set(DoubleSolenoid.Value.kReverse);
		climberState=false;
	}

	@Override
	public void teleopPeriodic() {
	
		if(joystick.getBumper(Hand.kLeft))
		{
			armLiftMotorLeft.set(-armUpSpeed);
			armLiftMotorRight.set(armUpSpeed);
		}
		else if(joystick.getBumper(Hand.kRight))
		{
			armLiftMotorLeft.set(.25);
			armLiftMotorRight.set(-.25);
		}
		else
		{
			armLiftMotorLeft.set(0);
			armLiftMotorRight.set(0);
		}
		

		//kReverse = pistons out
		//kForward = pistons in
		
		if(joystick.getAButtonPressed())
		{
			timer.reset();
			timer.start();
			solenoidContrLeft.set(DoubleSolenoid.Value.kForward);
			solenoidContrRight.set(DoubleSolenoid.Value.kForward);
		}

		if(timer.get() > 1)
		{
			solenoidContrLeft.set(DoubleSolenoid.Value.kReverse);
			solenoidContrRight.set(DoubleSolenoid.Value.kReverse);
		}

		
		if(joystick.getBButtonPressed() && joystick.getBumper(Hand.kLeft)){
			flapperTimer.reset();
			flapperTimer.start();

			pusher.set(DoubleSolenoid.Value.kForward);
		}
		
		if(flapperTimer.get()>.5){
			pusher.set(DoubleSolenoid.Value.kReverse);
		}
		
	
		
		 if(joystick.getXButtonPressed()){
		 	if(climberState){
		 		climberState=false;
		 	}else{
		 		climberState=true;
		 	}
		 }

		 climber.set(climberState);
		
		
		
		/*
		if(joystick.getBumperReleased(Hand.kLeft)){
			System.out.print("Arm Down");

			timer.reset();
			while(timer.get()<bumperTimerConstant){
				armLiftMotorLeft.set(-.5);
				armLiftMotorRight.set(.5);
			}
		}

		if(joystick.getBumperReleased(Hand.kRight)){
			System.out.print("Arm Up");

			timer.reset();
			while(timer.get()<bumperTimerConstant){
				armLiftMotorLeft.set(.5);
				armLiftMotorRight.set(-.5);
			}
		}*/
		/*
		// if button is pressed
		if(joystick.getAButtonReleased())
		{
			System.out.println("Solenoids OUt");

			if(exampleDoubleBoolean==true){

				timer.reset();

				while(timer.get()<solenoidFliperTimerConstant){
					solenoidContrRight.set(DoubleSolenoid.Value.kForward);
					solenoidContrLeft.set(DoubleSolenoid.Value.kForward);
				}
				solenoidContrRight.set(DoubleSolenoid.Value.kOff);
				solenoidContrLeft.set(DoubleSolenoid.Value.kOff);

				exampleDoubleBoolean = false;

			}else{
				timer.reset();
				
				while(timer.get()<solenoidFliperTimerConstant){
					solenoidContrRight.set(DoubleSolenoid.Value.kReverse);
					solenoidContrLeft.set(DoubleSolenoid.Value.kReverse);
				}

				solenoidContrRight.set(DoubleSolenoid.Value.kOff);
				solenoidContrLeft.set(DoubleSolenoid.Value.kOff);
				exampleDoubleBoolean = true;
			}

		}

		// if button is pressed
		if(joystick.getBButtonReleased())
		{
			System.out.println("Flapper");

			if(pusherSolenoidCheck==true){

				timer.reset();

				while(timer.get()<solenoidPusherTimerConstant){
					pusher.set(DoubleSolenoid.Value.kForward);
					
				}
				pusher.set(DoubleSolenoid.Value.kOff);

				pusherSolenoidCheck = false;

			}else{
				timer.reset();
				
				while(timer.get()<solenoidPusherTimerConstant){
					pusher.set(DoubleSolenoid.Value.kReverse);
				}

				pusher.set(DoubleSolenoid.Value.kOff);
				pusherSolenoidCheck = true;
			}

		}

		// if button is pressed
		
		if(joystick.getXButtonPressed())
		{
			System.out.print("climber");
			climber.set(true);
		}else{
			climber.set(false);
		}

		if(joystick.getYButtonPressed() && joystick.getTriggerAxis(Hand.kRight)>.75){
			bumperTimerConstant=bumperTimerConstant+.25;
			System.out.print(bumperTimerConstant);
		}

		if(joystick.getYButtonPressed() && joystick.getTriggerAxis(Hand.kLeft)>.75){
			bumperTimerConstant=bumperTimerConstant-.25;
			System.out.print(bumperTimerConstant);
		}
		*/
		
		
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
		/*
		if(Math.abs(joystick.getY(Hand.kLeft)) > 0.15)
		{
			frontLeft.set(-Math.sqrt(joystick.getY(Hand.kLeft)));
			rearLeft.set(-Math.sqrt(joystick.getY(Hand.kLeft)));
		}
		else
		{
			frontLeft.set(0);
			rearLeft.set(0);
		}
		
		if(Math.abs(joystick.getY(Hand.kRight)) > 0.15)
		{
			frontRight.set(Math.sqrt(joystick.getY(Hand.kRight)));
			rearRight.set(Math.sqrt(joystick.getY(Hand.kRight)));
		}
		else
		{
			frontRight.set(0);
			rearRight.set(0);
		}
		*/
		/*
		if(joystick.getY(Hand.kLeft)*-1>.15) {
			frontLeft.set(1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 1/driveScaling));
			rearLeft.set(1*Math.pow((Math.abs(joystick.getY(Hand.kLeft))), 1/driveScaling));
		}
		else if(joystick.getY(Hand.kLeft)*-1<-.15) {
			frontLeft.set(-1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 1/driveScaling));
			rearLeft.set(-1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 1/driveScaling));		
		}
		else {
			frontLeft.set(0);
			rearLeft.set(0);
		}
		//joystick.ge
		System.out.println(joystick.getY(Hand.kRight));
		if(joystick.getY(Hand.kRight)*-1>.15) {
			System.out.print("Right Forward?");
			// frontRight.set(1);
			// rearRight.set(1);
			frontRight.set(-1*(Math.pow(joystick.getY(Hand.kRight), 1/driveScaling)));
			rearRight.set(-1*(Math.pow(joystick.getY(Hand.kRight), 1/driveScaling)));
		}
		else if(joystick.getY(Hand.kRight)*-1<-.15) {
			System.out.print("Right Back?");
			frontRight.set(1*(Math.pow((joystick.getY(Hand.kRight)), 1/driveScaling)));
			rearRight.set(1*(Math.pow(joystick.getY(Hand.kRight), 1/driveScaling)));
		}
		else{
			frontRight.set(0);
			rearRight.set(0);
		}*/
	}
	
	@Override
	public void autonomousPeriodic() {

		teleopPeriodic();

	}

	public void haloDriving(){
		if(Math.abs(joystick.getY(Hand.kLeft)) > 0.15)
		{
			frontLeft.set(-joystick.getY(Hand.kLeft));
			rearLeft.set(-joystick.getY(Hand.kLeft));
			frontRight.set(joystick.getY(Hand.kLeft));
			rearRight.set(joystick.getY(Hand.kLeft));
		}
		else
		{
			frontLeft.set(0);
			rearLeft.set(0);
			frontRight.set(0);
			frontLeft.set(0);
		}
		
		if(joystick.getY(Hand.kRight) > 0.35)
		{
			frontLeft.set(-joystick.getY(Hand.kRight));
			rearLeft.set(-joystick.getY(Hand.kRight));
			frontRight.set(joystick.getY(Hand.kRight));
			rearRight.set(joystick.getY(Hand.kRight));

		}
		else
		{
			frontLeft.set(0);
			rearLeft.set(0);
			frontRight.set(0);
			frontLeft.set(0);
		}
	}
}
