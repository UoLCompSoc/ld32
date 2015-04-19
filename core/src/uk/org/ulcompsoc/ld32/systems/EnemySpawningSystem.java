package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.CircleMap;
import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.components.*;
import uk.org.ulcompsoc.ld32.util.TextureManager;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemySpawningSystem extends IntervalSystem {
	private final CircleMap map;
	private Engine engine = null;

	private final TextureRegion redEnemy, blueEnemy, greenEnemy;

	public EnemySpawningSystem(int priority, float interval, final CircleMap map, final TextureManager textureManager) {
		super(interval, priority);
		this.map = map;

		this.redEnemy = new TextureRegion(textureManager.nameMap.get(TextureName.ENEMY_R));
		this.blueEnemy = new TextureRegion(textureManager.nameMap.get(TextureName.ENEMY_G));
		this.greenEnemy = new TextureRegion(textureManager.nameMap.get(TextureName.ENEMY_B));
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

		final double rand = Math.random();
		final TextureRegion img = (rand < 0.33 ? redEnemy : (rand < 0.66 ? blueEnemy : greenEnemy));

		entity.add(Position.fromPolar(firstSegment.middleR, firstSegment.middlePhi));
		entity.add(new PathFollower(firstSegment).continueToNull().killWhenDone());
		entity.add(new Renderable(img));
		entity.add(new Positron());
		entity.add(new SphericalBound(5f));
		entity.add(new Scalable(0.25f));

		return entity;
	}
}
