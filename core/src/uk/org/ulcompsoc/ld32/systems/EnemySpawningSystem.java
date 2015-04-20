package uk.org.ulcompsoc.ld32.systems;

import java.util.Random;

import uk.org.ulcompsoc.ld32.CircleMap;
import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.LD32;
import uk.org.ulcompsoc.ld32.components.CanItDrop;
import uk.org.ulcompsoc.ld32.components.DeathAnimation;
import uk.org.ulcompsoc.ld32.components.Killable;
import uk.org.ulcompsoc.ld32.components.PathFollower;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.enemies.Antineutron;
import uk.org.ulcompsoc.ld32.components.enemies.Antiproton;
import uk.org.ulcompsoc.ld32.components.enemies.Positron;
import uk.org.ulcompsoc.ld32.util.Mappers;
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
	public static final float MIN_SPAWN_TIME = 0.5f;

	private final CircleMap map;
	private Engine engine = null;

	private final TextureRegion greyEnemy;

	private final Random random = new Random();

	private final float interval;

	private float timeElapsed = 0.0f;
	private float totalTimeElapsed = 0.0f;
	// TODO: change this after spawn rate is calculated correctly.
	private float spawnTime;

	public EnemySpawningSystem(int priority, float interval, final CircleMap map) {
		super(interval, priority);
		this.interval = interval;
		this.map = map;
		this.greyEnemy = new TextureRegion(LD32.textureManager.nameMap.get(TextureName.ENEMY_GREY));
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
		System.out.println("interval: " + interval);
		timeElapsed += interval;
		totalTimeElapsed += interval;

		if (timeElapsed >= spawnTime) {
			timeElapsed -= spawnTime;
			spawnTime = 1.0f / calculateSpawnRate(totalTimeElapsed);
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
		
		if(type == EnemyType.YELLOW) {
			Positron pos = new Positron();
			entity.add(pos);
			entity.add(new Killable(pos.health));
			
		} else if(type == EnemyType.BLUE) {
			Antiproton antiP = new Antiproton();
			entity.add(antiP);
			entity.add(new Killable(antiP.health));

		} else if(type == EnemyType.GREEN){
			Antineutron antiN = new Antineutron();
			entity.add(antiN);
			entity.add(new Killable(antiN.health));

		}
		entity.add(new CanItDrop());
		entity.add(new DeathAnimation(new Animation(0.5f, greyEnemy)));

		entity.add(new SphericalBound(r.getWidth() / 2));

		// entity.add(new SphericalBound(5f));

		return entity;
	}

	@SuppressWarnings("unchecked")
	private float calculateSpawnRate(float elapsedTime) {
		System.out.println("ElapsedTime: " + elapsedTime);
		float factor;

		if (elapsedTime < 60.0f) {
			factor = 0.1f;
		} else {
			factor = 0.5f;
		}

		int numTowers = engine.getEntitiesFor(Family.all(Tower.class).get()).size();
		System.out.println("Num towers: " + numTowers);

		float scale = (float) (Math.sqrt(elapsedTime) * factor);
		float percentIncrease = 1 + (numTowers * 0.1f);
		System.out.println("Scale: " + scale + " percentIncrease: " + percentIncrease);

		//float spawnRate = Math.max(MIN_SPAWN_TIME, (scale * percentIncrease));
		float spawnRate = scale * percentIncrease;
		System.out.println("Spawn rate1: " + spawnRate);
		spawnRate = Math.min(spawnRate, MAX_SPAWN_TIME);

		System.out.println("SpawnRate2: " + spawnRate);
		return spawnRate;
	}

	public static enum EnemyType {
	//	RED(Color.RED.cpy()), //
		GREEN(Color.GREEN.cpy()), //
		BLUE(Color.BLUE.cpy()), //
		YELLOW(Color.YELLOW.cpy()); //
	//	PURPLE(Color.PURPLE.cpy());

		public final Color renderColor;

		private EnemyType(final Color color) {
			this.renderColor = color;
		}

		public static EnemyType getRandomType(final Random random) {
			return EnemyType.values()[random.nextInt(EnemyType.values().length)];
		}
	}
}
