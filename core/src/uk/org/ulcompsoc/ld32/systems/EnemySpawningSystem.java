package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.CircleMap;
import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.components.PathFollower;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.Color;

public class EnemySpawningSystem extends IntervalSystem {
	private final CircleMap map;
	private Engine engine = null;

	public EnemySpawningSystem(int priority, float interval, final CircleMap map) {
		super(interval, priority);
		this.map = map;
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
	protected void updateInterval() {
		engine.addEntity(generateEnemy());
	}

	private Entity generateEnemy() {
		final Entity entity = new Entity();
		final RingSegment firstSegment = map.getFirstSegment();

		final Color color = (Math.random() < 0.5 ? Color.RED : Color.GREEN);

		entity.add(Position.fromPolar(firstSegment.middleR, firstSegment.middlePhi));
		entity.add(new PathFollower(firstSegment).continueToNull().killWhenDone());
		entity.add(new Renderable(color, 16.0f));

		return entity;
	}
}
