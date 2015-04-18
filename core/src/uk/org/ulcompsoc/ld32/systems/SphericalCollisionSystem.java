package uk.org.ulcompsoc.ld32.systems;

import java.util.ArrayList;

import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Samy Narrainen on 18/04/2015.
 */
public class SphericalCollisionSystem extends EntitySystem {
	private Engine engine = null;
	boolean processing = false;
	private ComponentMapper<Position> posMapper = null;
	private ComponentMapper<Renderable> renderMapper = null;
	private ComponentMapper<SphericalBound> boundMapper = null;

	public SphericalCollisionSystem(int priority) {
		super(priority);

		posMapper = Mappers.positionMapper;
		renderMapper = Mappers.renderableMapper;
		boundMapper = Mappers.sphericalBoundsMapper;
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
			// Entity one = entities.get(i);
			Circle oneCircle = bounds.get(i);

			for (int j = i + 1; j < bounds.size(); j++) {
				// Entity other = entities.get(j);
				Circle otherCircle = bounds.get(j);

				// Collision
				if (oneCircle.overlaps(otherCircle)) {
					System.out.println("COLLSION DETECTED");
				}
			}
		}
	}
}
