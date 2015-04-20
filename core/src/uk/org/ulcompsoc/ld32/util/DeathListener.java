package uk.org.ulcompsoc.ld32.util;

import com.badlogic.ashley.core.Entity;

public interface DeathListener {
	public void notifyOfDeath(Entity entity);
}
