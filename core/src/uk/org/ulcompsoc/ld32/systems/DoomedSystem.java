package uk.org.ulcompsoc.ld32.systems;

import java.util.Random;

import uk.org.ulcompsoc.ld32.components.CanItDrop;
import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class DoomedSystem extends IteratingSystem {
	private Engine engine = null;
	private final Random random = new Random();

	@SuppressWarnings("unchecked")
	public DoomedSystem(int priority) {
		super(Family.all(Doomed.class).get(), priority);
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
		CanItDrop canItDrop = Mappers.dropMapper.get(entity);

		if (canItDrop != null) {
			// do something here, it can drop
		}

		engine.removeEntity(entity);
	}

	private boolean shouldItDrop(float chance, float booster) {
		if (booster > 0) {
			if (chance * booster >= 1.0f) {
				int temp = (int) (chance * booster);
				if (random.nextInt(temp) + random.nextFloat() < chance * booster) {
					return true;
				}

			}
		}
		return false;
	}

}
