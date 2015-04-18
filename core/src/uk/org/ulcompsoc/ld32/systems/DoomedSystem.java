package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Doomed;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class DoomedSystem extends IteratingSystem {
	private Engine engine = null;

	@SuppressWarnings("unchecked")
	public DoomedSystem(int priority) {
		super(Family.all(Doomed.class).get(), priority);
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.engine = engine;
	}

	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		this.engine = null;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		engine.removeEntity(entity);
	}
}
