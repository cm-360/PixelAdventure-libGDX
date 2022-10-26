package com.github.cm360.pixadv.modules.builtin.events.system;

import com.github.cm360.pixadv.events.Event;

public class LoadUniverseEvent extends Event {

	protected String filename;
	
	public LoadUniverseEvent(String filename) {
		this.filename = filename;
	}
	
	public String getFilename() {
		return filename;
	}

}
