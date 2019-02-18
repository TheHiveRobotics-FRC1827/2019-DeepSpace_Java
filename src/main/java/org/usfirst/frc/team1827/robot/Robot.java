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
	DoubleSolenoid exampleDouble = new DoubleSolenoid(0, 1);

	Boolean exampleDoubleBoolean = true;
	
	public void piVision()
	{
		/*
		NetworkTable table = NetworkTable.getTable("SmartDashboard");

		NetworkTable.setClientMode();
		NetworkTable.setTeam(1827);
		NetworkTable.initialize();

		MjpegServer inputStream = new MjpegServer("MJPEG Server", 1185);

		//UsbCamera camera = new UsbCamera("USB Camera", 0);
		//camera.setResolution(640, 480);

		UsbCamera camera = new UsbCamera("Coprocessor Camera", 0);
		inputStream.setSource(camera);
		camera.setResolution(640, 480);

		CvSink imageSink = new CvSink("CV Image Grbber");
		imageSink.setSource(camera);

		CvSource imageSource = new CvSource("CV Image Source", VideoMode.PixelFormat.kMJPEG, 640, 480, 30);
		MjpegServer cvStream = new MjpegServer("CV Image Stream", 1186);
		cvStream.setSource(imageSource);

		Mat inputImage =new Mat();
		Mat hsv = new Mat();
		int i=0;
		while(true){
			i++;
			System.out.println(i);
			long frameTime = imageSink.grabFrame(inputImage);

			if(frameTime==0) continue;

			Imgproc.cvtColor(inputImage, hsv, Imgproc.COLOR_BGR2HSV);

			imageSource.putFrame(hsv);
		}
		*/

	}

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

		//piVision();
		
	}

	@Override
	public void teleopPeriodic() {
		double speed = 1;
		//System.out.println(joystick.getY(Hand.kRight));
		//exampleDouble.
		// Jackson: not sure exactly how to properly say
		// "while button is held, value is this, otherwise, value is something else"
		// but I'm pretty sure you can't use while loops in Teleop or it'll lock it up
		// because Teleop is a loop itself

		// if button is pressed
		if(joystick.getAButtonReleased())
		{
			System.out.println("1: ");
			// set to forward
			if(exampleDoubleBoolean==true){
				exampleDouble.set(DoubleSolenoid.Value.kForward);
				exampleDouble.set(DoubleSolenoid.Value.kOff);
				exampleDoubleBoolean = false;
			}else{
				exampleDouble.set(DoubleSolenoid.Value.kReverse);
				exampleDouble.set(DoubleSolenoid.Value.kOff);
				exampleDoubleBoolean = true;
			}

		}


		
		if(joystick.getY(Hand.kLeft)*-1>.15) {
			frontLeft.set(1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 2));
			//frontRight.set(speed);
			rearLeft.set(1*Math.pow((Math.abs(joystick.getY(Hand.kLeft))), 2));
			//rearRight.set(speed);
		}
		else if(joystick.getY(Hand.kLeft)*-1<-.15) {
			frontLeft.set(-1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 2));
			//frontRight.set(-1*speed);
			rearLeft.set(-1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 2));
			//rearRight.set(-1*speed);
			
		}
		else {
			frontLeft.set(0);
			//frontRight.set(0);
			rearLeft.set(0);
			//rearRight.set(0);
		}
		
		
		if(joystick.getY(Hand.kRight)*-1>.15) {
			//frontLeft.set(speed);
			frontRight.set(-1*(Math.pow(joystick.getY(Hand.kRight), 2)));
			//rearLeft.set(speed);
			rearRight.set(-1*(Math.pow(joystick.getY(Hand.kRight), 2)));
		}
		else if(joystick.getY(Hand.kRight)*-1<-.15) {
			//frontLeft.set(-1*speed);
			frontRight.set(1*(Math.pow((joystick.getY(Hand.kRight)), 2)));
			//rearLeft.set(-1*speed);
			rearRight.set(1*(Math.pow(joystick.getY(Hand.kRight), 2)));
			
		}
		else {
			//frontLeft.set(0);
			frontRight.set(0);
			//rearLeft.set(0);
			rearRight.set(0);
		}
		// Commenting out this arm code since there's 3
		// arm motors and we need to redo this logic -Jackson
		/* if (joystick.getTriggerAxis(Hand.kRight)==1) {
			arm.set(speed);
			
		}
		else if (joystick.getTriggerAxis(Hand.kLeft)==1) {
			arm.set(-1*speed);
		}
		else {
			arm.set(0);
		} */
		
		/*if(joystick.getY(Hand.kLeft)*-1>.3) {
			frontLeft.set(speed);
			frontRight.set(speed);
			rearLeft.set(speed);
			rearRight.set(speed);
		}
		else if(joystick.getY(Hand.kLeft)*-1<-.3) {
			frontLeft.set(-1*speed);
			frontRight.set(-1*speed);
			rearLeft.set(-1*speed);
			rearRight.set(-1*speed);
			
		}
		else if(joystick.getX(Hand.kRight)*-1>.3) {
			frontLeft.set(speed);
			frontRight.set(-1*speed);
			rearLeft.set(speed);
			rearRight.set(-1*speed);
		}
		else if(joystick.getX(Hand.kRight)*-1>.3) {
			frontLeft.set(-1*speed);
			frontRight.set(speed);
			rearLeft.set(-1*speed);
			rearRight.set(speed);
		}
		else {
			frontLeft.set(0);
			frontRight.set(0);
			rearLeft.set(0);
			rearRight.set(0);
		}*/

	}
	
	@Override
	public void autonomousPeriodic() {
		

	}
}
