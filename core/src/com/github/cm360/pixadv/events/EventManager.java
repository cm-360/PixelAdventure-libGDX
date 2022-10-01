package com.github.cm360.pixadv.events;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class EventManager {

	private Queue<Event> eventQueue;
	private Set<EventHandler<? extends Event>> eventHandlers;
	
	public EventManager() {
		eventQueue = new LinkedList<Event>();
		eventHandlers = new TreeSet<EventHandler<?>>();
	}
	
	public void registerHandler(EventHandler<?> handler) {
		eventHandlers.add(handler);
	}
	
	public void tick() {
		while (!eventQueue.isEmpty()) {
			Event event = eventQueue.poll();
			for (EventHandler<?> handler : eventHandlers) {
				if (handler.getClass().getTypeParameters()[0].getClass() == event.getClass()) {
					
				}
			}
		}
	}
	
	public void queue(Event event) {
		eventQueue.add(event);
	}

}
