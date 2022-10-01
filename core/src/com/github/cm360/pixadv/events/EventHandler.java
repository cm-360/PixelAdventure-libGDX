package com.github.cm360.pixadv.events;

public interface EventHandler<T extends Event> {

	public void process(T event);
	
	public int getPriority();

}
