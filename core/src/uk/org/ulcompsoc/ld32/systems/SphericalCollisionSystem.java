package uk.org.ulcompsoc.ld32.systems;

import java.util.ArrayList;

import uk.org.ulcompsoc.ld32.components.Atom;
import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.components.Killable;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Projectile;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Samy Narrainen on 18/04/2015.
 */
public class SphericalCollisionSystem extends EntitySystem {
	private Engine engine = null;
	boolean processing = false;
	private ComponentMapper<Position> posMapper = null;
	private ComponentMapper<Renderable> renderMapper = null;
	private ComponentMapper<SphericalBound> boundMapper = null;
	private Circle outerBorder = null;

	public SphericalCollisionSystem(int priority, Circle outerBorder) {
		super(priority);

		posMapper = Mappers.positionMapper;
		renderMapper = Mappers.renderableMapper;
		boundMapper = Mappers.sphericalBoundsMapper;
		this.outerBorder = outerBorder;
	}

	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		processing = true;
	}

	@Override
	public void removedFromEngine(Engine engine) {
		this.engine = null;
		processing = false;
	}

	@Override
	public boolean checkProcessing() {
		return processing;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(float deltaTime) {
		ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(Position.class, SphericalBound.class,
		        Renderable.class).get());

		ArrayList<Circle> bounds = new ArrayList<Circle>();

		for (int i = 0; i < entities.size(); i++) {
			final float x = posMapper.get(entities.get(i)).getX();
			final float y = posMapper.get(entities.get(i)).getY();
			// Renderable r = renderMapper.get(entities.get(i));
			final SphericalBound sphere = boundMapper.get(entities.get(i));

			bounds.add(i, new Circle(x, y, sphere.radius));
		}

		for (int i = 0; i < bounds.size() - 1; i++) {
			Entity one = entities.get(i);
			Circle oneCircle = bounds.get(i);

			for (int j = i + 1; j < bounds.size(); j++) {
				// Entity other = entities.get(j);
				Circle otherCircle = bounds.get(j);

				// Collision
				if (oneCircle.overlaps(otherCircle)) {

					Atom atom = Mappers.atomMapper.get(entities.get(j));

					/**
					 * Atom collision
					 */
					// Check if the atom has collided with a tower
					Tower tower = Mappers.towerMapper.get(one);

<<<<<<< HEAD
					//yep, it's collided with a tower.

					if(tower != null) {
						//tower.pongBonusCounter();


					if(tower != null && atom != null) {
						TowerSystem.pongBonus(one);
=======
					// yep, it's collided with a tower.
					if (tower != null && atom != null) {
						if(tower.canUpgrade()) {
							TowerSystem.pongBonus(one);
						}
>>>>>>> 1577f5c19a90412f61a3729c554059fb0875c768
					}

					float distance = (float) (Math.sqrt(Math.pow(otherCircle.x - oneCircle.x, 2)
					        + Math.pow(otherCircle.y - oneCircle.y, 2)));

					// If the atom is within roughly the core of the paddle
					if (atom != null && distance > oneCircle.radius / 4 && !atom.primed && Mappers.paddleMapper.has(one)) {
						// System.out.println("atom found");

						// The atom is now housed in the paddle;
						atom.atPaddle = true;

						Position atomPos = Mappers.positionMapper.get(entities.get(j));
						Position paddlePos = Mappers.positionMapper.get(entities.get(i));
						Vector2 v = Mappers.velocityMapper.get(entities.get(j)).velocity;

						float deltaX = (float) ((paddlePos.getR() * Math.cos(paddlePos.getPhi()) - atomPos.getX()));
						float deltaY = (float) ((paddlePos.getR() * Math.sin(paddlePos.getPhi())) - atomPos.getY());

						float radPrime = (float) (Math.atan2(deltaY, deltaX) * (180.0 / Math.PI));

						float degrees = (radPrime + 360) % 360;

						float x = (float) (Math.cos(Math.toRadians(degrees)));
						float y = (float) (Math.sin(Math.toRadians(degrees)));

						v.x = x;
						v.y = y;

					} else if (atom != null && atom.primed && Mappers.paddleMapper.has(one)) {

						/**
						 * ATOM FIRING
						 */

						// Launch in opposite direction of the paddle.
						Position atomPos = Mappers.positionMapper.get(entities.get(j));
						Position paddlePos = Mappers.positionMapper.get(entities.get(i));

						Position oppositePaddlePos = Position.fromPolar(paddlePos.getR(), paddlePos.getPhi());
						oppositePaddlePos.movePolarAngle((float) Math.toRadians(180.0f));// Add
																						 // 180
																						 // to
																						 // get
																						 // opposite

						Vector2 v = Mappers.velocityMapper.get(entities.get(j)).velocity;

						float deltaX = (float) ((oppositePaddlePos.getR() * Math.cos(oppositePaddlePos.getPhi()) - atomPos
						        .getX()));
						float deltaY = (float) ((oppositePaddlePos.getR() * Math.sin(oppositePaddlePos.getPhi())) - atomPos
						        .getY());

						float radPrime = (float) (Math.atan2(deltaY, deltaX) * (180.0 / Math.PI));

						float degrees = (radPrime + 360) % 360;

						float x = (float) (Math.cos(Math.toRadians(degrees)));
						float y = (float) (Math.sin(Math.toRadians(degrees)));

						v.x = x;
						v.y = y;

						atom.primed = false;
					} else if (atom != null) {
						atom.atPaddle = false;
					}

					/**
					 * PROJECTILE COLLISION
					 */
					Projectile projectile = Mappers.projectileMapper.get(entities.get(j));

					if (projectile != null) {

						// Check if other is killable
						Killable isEnemyKillable = Mappers.killableMapper.get(entities.get(i));

						// IT'S KILLABLE!
						if (isEnemyKillable != null) {
							isEnemyKillable.removeHealth(projectile.damage);

							// If the enemy has 'death' hp then it's doomed,
							// send it to die
							if (isEnemyKillable.getHealth() <= 0) {
								entities.get(i).add(new Doomed());
								// System.out.println("DOOMED");
							}

							engine.removeEntity(entities.get(j));

						}
					}

				}
			}
		}
	}
	}
}
