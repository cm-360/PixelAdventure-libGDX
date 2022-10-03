package com.github.cm360.pixadv.events;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import com.github.cm360.pixadv.util.Logger;

public class EventManager {

	private Queue<Event> eventQueue;
	private Map<Method, EventListener> eventHandlers;
	
	public EventManager() {
		eventQueue = new LinkedList<Event>();
		eventHandlers = new TreeMap<Method, EventListener>((m1, m2) -> {
			return m1.getAnnotation(EventHandler.class).priority() - m2.getAnnotation(EventHandler.class).priority();
		});
	}
	
	public void registerHandlers(EventListener listener) {
		// Locate all event handler methods
		for (Method method : listener.getClass().getDeclaredMethods()) {
			if (method.getAnnotation(EventHandler.class) != null) {
				if (validateHandlerMethod(method)) {
					eventHandlers.put(method, listener);
				} else {
					// TODO be more descriptive
					Logger.logMessage(Logger.WARNING, "'%s' is not a valid event handler method!", method.getName());
				}
			}
		}
	}
	
	public void tick() {
		while (!eventQueue.isEmpty()) {
			Event event = eventQueue.poll();
			eventHandlers.forEach((handler, listener) -> {
				if (validateEventHandler(event, handler)) {
					try {
						handler.invoke(listener, event);
					} catch (Exception e) {
						// TODO be more descriptive!
						Logger.logException("Caught exception while handling event '%s'!", e, event.getClass());
					}
				}
			});
		}
	}
	
	public void queue(Event event) {
		eventQueue.add(event);
	}
	
	private boolean validateHandlerMethod(Method method) {
		// TODO method parameter checking
		return true;
	}
	
	private boolean validateEventHandler(Event event, Method handler) {
		return handler.getParameters()[0].getType().isAssignableFrom(event.getClass());
	}

}
