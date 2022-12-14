package com.github.cm360.pixadv.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.cm360.pixadv.events.EventManager.SyncType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

	int priority() default 0;
	
	SyncType sync();

}
