package uk.org.ulcompsoc.ld32.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector3;
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
	private final float BORDER_MULTIPLIER = 2.0f;

	private Engine engine;

	@SuppressWarnings("unchecked")
	public AtomMovementSystem(int priority, Circle outerBorder) {
		this(Family.all(Position.class, Velocity.class, Atom.class, SphericalBound.class).get(), outerBorder, priority);
	}

	protected AtomMovementSystem(Family family, Circle outerBorder, int priority) {
		super(family, priority);

		this.outerBorder = outerBorder;
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
	public void processEntity(Entity entity, float deltaTime) {
		Position p = Mappers.positionMapper.get(entity);
		Vector2 v = Mappers.velocityMapper.get(entity).velocity;

		p.setX(p.getX() + v.x);
		p.setY(p.getY() + v.y);

		if(p.getR() > outerBorder.radius  * BORDER_MULTIPLIER || p.getY() > outerBorder.radius * BORDER_MULTIPLIER) {
			engine.removeEntity(entity);
			System.out.println("AtomMovement: removed atom.");
		}

	}
}
