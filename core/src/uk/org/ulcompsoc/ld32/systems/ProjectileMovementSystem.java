package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Velocity;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Samy on 19/04/2015. Description: Provides the movement for
 * Projectiles, potential overlap with AtomMovementSystem...
 */
public class ProjectileMovementSystem extends IteratingSystem {

	// TODO Probably want to add a boundary for the projectiles where they
	// disappear after reaching

	@SuppressWarnings("unchecked")
	public ProjectileMovementSystem(int priority) {
		this(Family.all(Position.class, Velocity.class).get(), priority);
	}

	protected ProjectileMovementSystem(Family family, int priority) {
		super(family, priority);
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		Position p = Mappers.positionMapper.get(entity);
		Vector2 v = Mappers.velocityMapper.get(entity).velocity;

		p.setX(p.getX() + v.x);
		p.setY(p.getY() + v.y);

	}

}
