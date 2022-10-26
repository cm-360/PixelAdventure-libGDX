package com.github.cm360.pixadv.modules.builtin.events.system;

import java.io.File;

import com.github.cm360.pixadv.events.Event;

public class UniverseLoadEvent extends Event {

	protected File dir;
	
	public UniverseLoadEvent(File dir) {
		this.dir = dir;
	}
	
	public File getDirectory() {
		return dir;
	}

}
