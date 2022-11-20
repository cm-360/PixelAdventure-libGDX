package com.github.cm360.pixadv.modules.builtin.entities.capabilities;

import com.github.cm360.pixadv.environment.types.entities.Entity;

public interface FlyingEntity extends Entity {

	public void setFlying(boolean flying);
	
	public boolean isFlying();
	
	public void setFlightSpeed(double flightSpeed);
	
	public double getFlightSpeed();
	
	public double getFlightDecayX();
	
	public double getFlightDecayY();

}
