package uk.org.ulcompsoc.ld32.systems;

import java.util.ArrayList;
import java.util.List;

import uk.org.ulcompsoc.ld32.components.Atom;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Samy on 20/04/2015.
 */
public class AtomCollisionSystem extends EntitySystem {
	private Engine engine = null;
	boolean processing = false;

	public AtomCollisionSystem(int priority) {
		super(priority);
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
		ImmutableArray<Entity> towers = engine.getEntitiesFor(Family.all(Tower.class, Position.class, Renderable.class)
		        .get());

		List<Circle> towerBounds = new ArrayList<Circle>();

		for (int i = 0; i < towers.size(); i++) {
			final float x = Mappers.positionMapper.get(towers.get(i)).getX();
			final float y = Mappers.positionMapper.get(towers.get(i)).getY();
			towerBounds.add(i, new Circle(x, y, Mappers.renderableMapper.get(towers.get(i)).getWidth() / 2));
		}

		ImmutableArray<Entity> atoms = engine.getEntitiesFor(Family.all(Atom.class, Position.class, Renderable.class)
		        .get());

		List<Circle> atomBounds = new ArrayList<Circle>();

		for (int i = 0; i < atoms.size(); i++) {
			final float x = Mappers.positionMapper.get(atoms.get(i)).getX();
			final float y = Mappers.positionMapper.get(atoms.get(i)).getY();
			atomBounds.add(i, new Circle(x, y, Mappers.renderableMapper.get(atoms.get(i)).getWidth() / 2));
		}

		for (int i = 0; i < towerBounds.size(); i++) {

			for (int j = 0; j < atomBounds.size(); j++) {

				if (towerBounds.get(i).overlaps(atomBounds.get(j))) {

					Tower tower = Mappers.towerMapper.get(towers.get(i));

					if (tower.canUpgrade())
						TowerSystem.pongBonus(towers.get(i));

				}
			}
		}

	}

}
