package org.usfirst.frc.team1827.robot;
import edu.wpi.first.cameraserver.CameraServer;

public class HiveRobotCamera
{
	CameraServer server;
	
    public HiveRobotCamera()
    {        
		server = CameraServer.getInstance();		
	}
	
	public void initialize()
	{
		// From example project "Simple Vision":
		/**
		 * Uses the CameraServer class to automatically capture video from a USB webcam
		 * and send it to the FRC dashboard without doing any vision processing. This
		 * is the easiest way to get camera images to the dashboard. Just add this to
		 * the robotInit() method in your program.
		 */
		server.startAutomaticCapture();
	}
}