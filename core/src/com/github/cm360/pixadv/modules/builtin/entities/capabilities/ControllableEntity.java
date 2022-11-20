package com.github.cm360.pixadv.modules.builtin.entities.capabilities;

import java.util.Set;

import com.github.cm360.pixadv.environment.types.entities.Entity;

public interface ControllableEntity extends Entity {

	public enum Input { UP, DOWN, LEFT, RIGHT, JUMP };
	
	public void setInputs(Set<Input> inputs);

}
