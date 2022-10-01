package com.github.cm360.pixadv.events;

public class Event {

	private boolean cancelled;
	
	public void cancel() {
		cancelled = true;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}

}
