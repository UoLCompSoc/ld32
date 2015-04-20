package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.LD32;
import uk.org.ulcompsoc.ld32.components.Damage;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Projectile;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Rotatable;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.Velocity;
import uk.org.ulcompsoc.ld32.components.enemies.Antineutron;
import uk.org.ulcompsoc.ld32.components.enemies.Antiproton;
import uk.org.ulcompsoc.ld32.components.enemies.Positron;
import uk.org.ulcompsoc.ld32.util.Mappers;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Samy Narrainen on 19/04/2015. Description: Provides a basic system
 * for an entity to fire a single projectile at a single detected enemy.
 */
public class BasicFiringSystem extends IteratingSystem {
	private Engine engine = null;
	private TextureRegion ammoSprite = null;

	@SuppressWarnings("unchecked")
	public BasicFiringSystem(int priority) {
		super(Family.all(Tower.class, Position.class).get(), priority);

		this.ammoSprite = new TextureRegion(LD32.textureManager.nameMap.get(TextureName.AMMO));
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
		// The tower we're checking for
		Tower tower = Mappers.towerMapper.get(entity);
		Damage damageComp = Mappers.damageMapper.get(entity);

		// The position of the tower
		Position towerPos = Mappers.positionMapper.get(entity);

		// Increment known time for the tower
		tower.TimePassed(deltaTime);

		if (tower.isReadyToFire()) {
			/**
			 * Look for an enemy to fire at!
			 */

			// Get all enemies in the engine
			@SuppressWarnings("unchecked")
			ImmutableArray<Entity> enemies = engine.getEntitiesFor(Family.all(Position.class, SphericalBound.class)
			        .one(Antiproton.class, Antineutron.class, Positron.class).get());

			// Iterate finding one to fire at
			for (int i = 0; i < enemies.size(); i++) {
				// The known position of the enemy
				Position enemyPos = Mappers.positionMapper.get(enemies.get(i));
				// The bound, or radius of the enemy.
				SphericalBound enemyRadius = Mappers.sphericalBoundsMapper.get(enemies.get(i));

				// Representation of the towers attack range
				Circle towerRange = new Circle(towerPos.getX(), towerPos.getY(), tower.range);

				// Representation of the enemies visible range
				Circle enemyRange = new Circle(enemyPos.getX(), enemyPos.getY(), enemyRadius.radius);

				// The tower is able to see the enemy, fire!!!
				if (enemyRange.overlaps(towerRange) && tower.isReadyToFire()) {
					// Create a projectile to send towards the enemy.
					// TODO IMPLEMENT A POOLEDENGINE FOR THIS?
					Entity projectile = new Entity();

					projectile.add(new Projectile(damageComp.getDamageDealt()));
					projectile.add(Position.fromEuclidean(towerPos.getX(), towerPos.getY()));
					Renderable r = new Renderable(ammoSprite).setScale(0.5f);
					projectile.add(r);
					projectile.add(new SphericalBound(r.getWidth() / 2));
					projectile.add(new Rotatable().animateRotation(1.0f));

					float deltaX = (float) ((enemyPos.getR() * Math.cos(enemyPos.getPhi()) - towerPos.getX()));
					float deltaY = (float) ((enemyPos.getR() * Math.sin(enemyPos.getPhi())) - towerPos.getY());

					// System.out.println("original x = " + enemyPos.getX() +
					// " ## new x = " + deltaX);

					float radPrime = (float) (Math.atan2(deltaY, deltaX) * (180.0 / Math.PI));

					float degrees = (radPrime + 360) % 360;

					// System.out.println(degrees);

					float x = (float) (Math.cos(Math.toRadians(degrees)));
					float y = (float) (Math.sin(Math.toRadians(degrees)));
					// System.out.println("x: " + x + "####" + "y: " + y);

					projectile.add(new Velocity(x, y));

					engine.addEntity(projectile);

					// Update the tower to be fired
					tower.shotHasBeenFired();
				}
			}
		}
	}
}