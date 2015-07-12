package org.szkiler.hsa.core.service.impl;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import org.springframework.beans.factory.annotation.Autowired;
import org.szkiler.hsa.core.data.VideoStorage;
import org.szkiler.hsa.core.service.CaptureService;

public class RobotCaptureService implements CaptureService{

	@Autowired
	private VideoStorage storage;
	@Autowired
	private Robot robot;
	
	private boolean pause=true;
	
	public Image captureScreen() {
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		return robot.createScreenCapture(screenRect);
	}

	public void start() {
		pause = false;
		while(!pause) {
			storage.getCapturedVideo();
		}
	}

	public void stop() {
		pause = true;
	}
	
	

}
