package org.szkiler.hsa.core.data;

import org.openimaj.video.Video;
import org.springframework.beans.factory.annotation.Autowired;

public class MemoryVideoStorage implements VideoStorage{

	@Autowired
	private Video capturedVideo;

	public Video getCapturedVideo() {
		return capturedVideo;
	}
	
}
