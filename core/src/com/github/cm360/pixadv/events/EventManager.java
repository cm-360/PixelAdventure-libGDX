package com.github.cm360.pixadv.events;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class EventManager {

	private Queue<Event> eventQueue;
	private Set<Method> eventHandlers;
	
	public EventManager() {
		eventQueue = new LinkedList<Event>();
		eventHandlers = new TreeSet<Method>((m1, m2) -> {
			return m1.getAnnotation(EventHandler.class).priority() - m2.getAnnotation(EventHandler.class).priority();
		});
	}
	
	public void registerHandlers(EventListener listener) {
		// Locate all event handler methods
		for (Method method : listener.getClass().getDeclaredMethods()) {
			if (method.getAnnotation(EventHandler.class) != null) {
				// TODO method parameter checking
				eventHandlers.add(method);
			}
		}
	}
	
	public void tick() {
		while (!eventQueue.isEmpty()) {
			Event event = eventQueue.poll();
			eventHandlers.forEach(handler -> {
				if (checkParameters(handler.getParameters(), new Class<?>[] { event.getClass() })) {
					// TODO invoke handler
					System.out.println("invoke!");
				}
			});
		}
	}
	
	public void queue(Event event) {
		eventQueue.add(event);
	}
	
	private boolean checkParameters(Parameter[] given, Class<?>[] expected) {
		// Check paramter lengths
		if (given.length == expected.length) {
			// Check parameter types
			for (int i = 0; i < expected.length; i++) {
				if (!expected[i].isAssignableFrom(given[i].getType())) {
					return false; // Parameter type is not assignable
				}
			}
			return true;
		} else {
			return false;
		}
	}

}
