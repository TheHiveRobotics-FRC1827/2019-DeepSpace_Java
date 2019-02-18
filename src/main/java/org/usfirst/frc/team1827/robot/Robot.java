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
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
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
	SpeedController armBallMotor = new VictorSP(kArmBallMotor);
	SpeedController armLiftMotorRight = new VictorSP(kArmLiftMotorRight);
	SpeedController armLiftMotorLeft = new VictorSP(kArmLiftMotorLeft);
	//Solenoid armSol = new Solenoid(7,4); //creates a Solenoid object in slot 7, channel 4.
	DoubleSolenoid solenoidContr = new DoubleSolenoid(0, 1);

	Boolean exampleDoubleBoolean = true;
	
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
		//CameraServer server = CameraServer.getInstance();
		//server.startAutomaticCapture();

	}
	
	@Override
	public void robotInit() {
		directUSBVision();	
	}

	@Override
	public void teleopPeriodic() {
		Timer timer = new Timer();

		if(joystick.getBumperReleased(Hand.kLeft)){
			timer.reset();
			while(timer.get()<2){
				armLiftMotorLeft.set(-1);
				armLiftMotorRight.set(-1);
			}
		}

		if(joystick.getBumperReleased(Hand.kRight)){
			timer.reset();
			while(timer.get()<2){
				armLiftMotorLeft.set(1);
				armLiftMotorRight.set(1);
			}
		}

		// if button is pressed
		if(joystick.getAButtonReleased())
		{
			System.out.println("Solenoid Control");

			if(exampleDoubleBoolean==true){

				timer.reset();

				while(timer.get()<1)
					solenoidContr.set(DoubleSolenoid.Value.kForward);

				solenoidContr.set(DoubleSolenoid.Value.kOff);
				exampleDoubleBoolean = false;

			}else{
				timer.reset();
				
				while(timer.get()<1)
					solenoidContr.set(DoubleSolenoid.Value.kReverse);

				solenoidContr.set(DoubleSolenoid.Value.kOff);
				exampleDoubleBoolean = true;
			}

		}


		
		if(joystick.getY(Hand.kLeft)*-1>.15) {
			frontLeft.set(1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 1/2));
			rearLeft.set(1*Math.pow((Math.abs(joystick.getY(Hand.kLeft))), 1/2));
		}
		else if(joystick.getY(Hand.kLeft)*-1<-.15) {
			frontLeft.set(-1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 1/2));
			rearLeft.set(-1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 1/2));		
		}
		else {
			frontLeft.set(0);
			rearLeft.set(0);
		}
		
		
		if(joystick.getY(Hand.kRight)*-1>.15) {
			frontRight.set(-1*(Math.pow(joystick.getY(Hand.kRight), 1/2)));
			rearRight.set(-1*(Math.pow(joystick.getY(Hand.kRight), 1/2)));
		}
		else if(joystick.getY(Hand.kRight)*-1<-.15) {
			frontRight.set(1*(Math.pow((joystick.getY(Hand.kRight)), 2)));
			rearRight.set(1*(Math.pow(joystick.getY(Hand.kRight), 2)));
		}
		else {
			frontRight.set(0);
			rearRight.set(0);
		}
	}
	
	@Override
	public void autonomousPeriodic() {
		

	}
}
