package com.github.cm360.pixadv.events;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import com.github.cm360.pixadv.util.Logger;

public class EventManager {

	public enum SyncType {
		PHYSICS, RENDER, ASYNC
	}

	private Map<SyncType, Queue<Event>> eventQueueMap;
	private Map<SyncType, Map<Method, EventListener>> eventHandlersMap;
	
	private Thread asyncEventThread;
	
	public EventManager() {
		// Create sync type maps
		eventQueueMap = new HashMap<SyncType, Queue<Event>>();
		eventHandlersMap = new HashMap<SyncType, Map<Method, EventListener>>();
		// Create event queues and handler lists
		for (SyncType sync : SyncType.values()) {
			eventQueueMap.put(sync, new LinkedList<Event>());
			eventHandlersMap.put(sync, new TreeMap<Method, EventListener>((m1, m2) -> {
				return m1.getAnnotation(EventHandler.class).priority() - m2.getAnnotation(EventHandler.class).priority();
			}));
		}
		// Create async event handling thread
		asyncEventThread = new Thread(null, () -> {
			while (!asyncEventThread.isInterrupted()) {
				tick(SyncType.ASYNC);
			}
		}, "events");
		asyncEventThread.start();
	}
	
	public void registerHandlers(EventListener listener) {
		// Locate all event handler methods
		for (Method method : listener.getClass().getDeclaredMethods()) {
			EventHandler handlerAnnotation = method.getAnnotation(EventHandler.class);
			if (handlerAnnotation != null) {
				if (validateHandlerMethod(method)) {
					eventHandlersMap.get(handlerAnnotation.sync()).put(method, listener);
				} else {
					// TODO be more descriptive
					Logger.logMessage(Logger.WARNING, "'%s' is not a valid event handler method!", method.getName());
				}
			}
		}
	}
	
	public void tick(SyncType sync) {
		// Get correct queue and handlers
		Queue<Event> eventQueue = eventQueueMap.get(sync);
		Map<Method, EventListener> eventHandlers = eventHandlersMap.get(sync);
		// Process queue
		while (!eventQueue.isEmpty()) {
			Event event = eventQueue.poll();
			for (Method handler : eventHandlers.keySet()) {
				if (validateEventHandler(event, handler)) {
					try {
						handler.invoke(eventHandlers.get(handler), event);
						if (event.isCancelled())
							break;
					} catch (Exception e) {
						// TODO be more descriptive!
						Logger.logException("Caught exception while handling event '%s'!", e, event.getClass());
					}
				}
			}
		}
	}
	
	public void queue(Event event, SyncType sync) {
		eventQueueMap.get(sync).add(event);
	}
	
	private boolean validateHandlerMethod(Method method) {
		// TODO method parameter checking
		return true;
	}
	
	private boolean validateEventHandler(Event event, Method handler) {
		return handler.getParameters()[0].getType().isAssignableFrom(event.getClass());
	}
	
	public void exit() {
		asyncEventThread.interrupt();
	}

}
