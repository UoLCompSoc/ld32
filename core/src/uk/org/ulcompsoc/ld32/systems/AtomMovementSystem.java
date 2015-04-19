package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Atom;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Velocity;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Samy Narrainen on 18/04/2015.
 */
public class AtomMovementSystem extends IteratingSystem {

	/**
	 * Defines the external limits for the Atom
	 */
	private Circle outerBorder = null;

	@SuppressWarnings("unchecked")
	public AtomMovementSystem(int priority, Circle outerBorder) {
		this(Family.all(Position.class, Velocity.class, Atom.class, SphericalBound.class).get(), outerBorder, priority);
	}

	protected AtomMovementSystem(Family family, Circle outerBorder, int priority) {
		super(family, priority);

		this.outerBorder = outerBorder;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		Position p = Mappers.positionMapper.get(entity);
		Vector2 v = Mappers.velMapper.get(entity).velocity;

		p.setX(p.getX() + v.x);
		p.setY(p.getY() + v.y);

	}
}
