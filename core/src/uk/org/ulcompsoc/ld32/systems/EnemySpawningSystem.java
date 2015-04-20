package uk.org.ulcompsoc.ld32.systems;

import java.util.Random;

import uk.org.ulcompsoc.ld32.CircleMap;
import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.components.CanItDrop;
import uk.org.ulcompsoc.ld32.components.DeathAnimation;
import uk.org.ulcompsoc.ld32.components.Killable;
import uk.org.ulcompsoc.ld32.components.PathFollower;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.enemies.Antiproton;
import uk.org.ulcompsoc.ld32.components.enemies.Positron;
import uk.org.ulcompsoc.ld32.util.TextureManager;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemySpawningSystem extends IntervalSystem {
	public static final float MAX_SPAWN_TIME = 10.0f;
	public static final float MIN_SPAWN_TIME = 1.0f;

	private final CircleMap map;
	private Engine engine = null;

	private final TextureRegion greyEnemy;

	private final Random random = new Random();

	private final float interval;

	private float timeElapsed = 0.0f;
	// TODO: change this after spawn rate is calculated correctly. 
	private float spawnTime = MAX_SPAWN_TIME / 4.0f;

	public EnemySpawningSystem(int priority, float interval, final CircleMap map, final TextureManager textureManager) {
		super(interval, priority);
		this.interval = interval;
		this.map = map;

		this.greyEnemy = new TextureRegion(textureManager.nameMap.get(TextureName.ENEMY_GREY));
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
		timeElapsed += interval;

		if (timeElapsed >= spawnTime) {
			timeElapsed -= spawnTime;
			spawnTime = calculateSpawnRate(interval);
			Gdx.app.log("SPAWN_TIME", "Enemy spawn time is now: " + spawnTime);

			engine.addEntity(generateEnemy());
		}
	}

	private Entity generateEnemy() {
		final Entity entity = new Entity();
		final RingSegment firstSegment = map.getFirstSegment();

		final EnemyType type = EnemyType.getRandomType(random);

		entity.add(Position.fromPolar(firstSegment.middleR, firstSegment.middlePhi));
		entity.add(new PathFollower(firstSegment).continueToNull().killWhenDone());

		Renderable r = new Renderable(greyEnemy).setScale(0.25f).setColor(type.renderColor);
		entity.add(r);
		entity.add(new Positron());
		// entity.add(new Enemy());
		entity.add(new Antiproton());
		entity.add(new Killable(5)); // TODO GIVE PROPER HEALTH
		entity.add(new CanItDrop());
		entity.add(new DeathAnimation(new Animation(0.5f, greyEnemy)));

		entity.add(new SphericalBound(r.getWidth() / 2));

		// entity.add(new SphericalBound(5f));

		return entity;
	}

	@SuppressWarnings("unchecked")
	public float calculateSpawnRate(float deltaTime) {
		final float min = MIN_SPAWN_TIME;
		final float max = MAX_SPAWN_TIME;

		final int numTowers = engine.getEntitiesFor(Family.all(Tower.class).get()).size();

		float spawnRate = (float) Math.max(min, (Math.sqrt(deltaTime) * 0.5) * (1 + (numTowers * 0.1f)));
		spawnRate = Math.min(spawnRate, max);

		return spawnRate;
	}

	public static enum EnemyType {
		RED(Color.RED.cpy()), //
		GREEN(Color.GREEN.cpy()), //
		BLUE(Color.BLUE.cpy()), //
		YELLOW(Color.YELLOW.cpy()), //
		PURPLE(Color.PURPLE.cpy());

		public final Color renderColor;

		private EnemyType(final Color color) {
			this.renderColor = color;
		}

		public static EnemyType getRandomType(final Random random) {
			return EnemyType.values()[random.nextInt(EnemyType.values().length)];
		}
	}
}
