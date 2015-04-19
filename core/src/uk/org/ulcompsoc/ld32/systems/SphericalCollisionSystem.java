package uk.org.ulcompsoc.ld32.systems;

import java.util.ArrayList;

import uk.org.ulcompsoc.ld32.components.*;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
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
				Entity other = entities.get(j);
				Circle otherCircle = bounds.get(j);

				// Collision
				if (oneCircle.overlaps(otherCircle)) {

					Atom atom = Mappers.atomMapper.get(entities.get(j));

					/**
					 * Atom collision
					 */
					if (atom != null) {
						// System.out.println("atom found");

						Position atomPos = Mappers.positionMapper.get(entities.get(j));
						Position paddlePos = Mappers.positionMapper.get(entities.get(i));
						Vector2 v = Mappers.velMapper.get(entities.get(j)).velocity;

						// float oneRadius =
						// Mappers.sphericalBoundsMapper.get(other).radius;
						// float otherRadius =
						// Mappers.sphericalBoundsMapper.get(other).radius;
						//
						// if(oneRadius > otherRadius) {
						// System.out.println("LO");
						// } else {
						// System.out.println("NO");
						// }
						//
						//

						// /if(p.getX() > outerBorder.radius / 2 && p.getY() >
						// outerBorder.radius / 2) {

						float deltaX = (float) ((paddlePos.getR() * Math.cos(paddlePos.getPhi()) - atomPos.getX()));
						float deltaY = (float) ((paddlePos.getR() * Math.sin(paddlePos.getPhi())) - atomPos.getY());

						float radPrime = (float) (Math.atan2(deltaY, deltaX) * (180.0/Math.PI));

						float degrees = (radPrime + 360) % 360;

						//System.out.println(degrees);

						float x = (float) (Math.cos(Math.toRadians(degrees)));
						float y = (float) (Math.sin(Math.toRadians(degrees)));

						v.x = x;
						v.y = y;

/*

						if (p.getX() > 0 && p.getY() > 0) {
							System.out.println("TOP RIGHT");
							v.y = (float) Math.cos(Math.PI * MathUtils.random(.01f, 1.0f));
							v.x = (float) Math.cos(Math.PI * MathUtils.random(.01f, 1.0f));

						} else if (p.getX() < 0 && p.getY() > 0) {
							System.out.println("TOP LEFT");
							v.y = (float) Math.sin(Math.PI * MathUtils.random(.01f, 1.0f));
							v.x = (float) Math.sin(Math.PI * MathUtils.random(.01f, 1.0f));

						} else if (p.getX() > 0 && p.getY() < 0) {
							System.out.println("BOTTOM RIGHT");
							v.y = (float) -Math.sin(Math.PI * MathUtils.random(.01f, 1.0f));
							v.x = (float) -Math.sin(Math.PI * MathUtils.random(.01f, 1.0f));

						} else if (p.getX() < 0 && p.getY() < 0) {
							System.out.println("BOTTOM LEFT");
							v.y = (float) -Math.cos(Math.PI * MathUtils.random(.01f, 1.0f));
							v.x = (float) -Math.cos(Math.PI * MathUtils.random(.01f, 1.0f));
						}
						*/

						/*
						 * 
						 * if(p.getY() > outerBorder.radius) {
						 * 
						 * if (p.getY() > outerBorder.radius) {
						 * 
						 * v.y = -v.y;
						 * 
						 * System.out.println("test"); } else if (p.getX() >
						 * outerBorder.radius / 2) { v.x = -v.x;
						 * System.out.println("test2"); } else if (p.getY() <
						 * -outerBorder.radius / 2) { v.y = -v.y;
						 * System.out.println("reached2"); } else if (p.getX() <
						 * -outerBorder.radius / 2) { v.x = -v.x; }
						 */

					}

					/**
					 * PROJECTILE COLLISION
					 */
					Projectile projectile = Mappers.projectileMapper.get(entities.get(j));

					if(projectile != null) {


						//Check if other is killable
						Killable isEnemyKillable = Mappers.killableMapper.get(entities.get(i));

						//IT'S KILLABLE!
						if(isEnemyKillable != null) {
							isEnemyKillable.removeHealth(projectile.damage);

							//If the enemy has 'death' hp then it's doomed, send it to die
							if(isEnemyKillable.getHealth() <= 0) {
								entities.get(i).add(new Doomed());
								System.out.println("DOOMED");
							}


							engine.removeEntity(entities.get(j));


						}
					}

				}
			}
		}
	}

}
