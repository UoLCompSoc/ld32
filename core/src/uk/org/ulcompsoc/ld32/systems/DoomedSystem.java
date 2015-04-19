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
			boolean redDrop = shouldItDrop(canItDrop.redDropChance, CanItDrop.RED_BOOSTER);
			if(!redDrop){
				CanItDrop.RED_BOOSTER+=CanItDrop.DFLT_BOOSTER_INCREASE;
			} else {
				//i guess this is where the entity Drop is created
				CanItDrop.RED_BOOSTER = 0;
			}
		}

		engine.removeEntity(entity);
	}

	private boolean shouldItDrop(float chance, float booster) {
		//if booster doesn't equal 0
		if (booster > 0) {
			//if chance with booster will go beyond 1.0f - we want to create infinite upgrades
			if (chance * booster >= 1.0f) {
				int temp = (int) (chance * booster);
				if (random.nextInt(temp) + random.nextFloat() < chance*booster) {
					return true;
				} else return false;

			} else {
				// if chance with booster is below 1.0f
				if (random.nextFloat() < chance * booster) {
					return true;
				} else return false;
			}
		//if booster equals zero
		} else {
			//if chance is bigger than 1.0f
			if (chance >= 1.0f) {
				int temp2 = (int) chance;
				if (random.nextInt(temp2) + random.nextFloat() < chance) {
					return true;
				} else return false;
			} else {
				//if chance is below 1.0f
				if(random.nextFloat()< chance){
					return true;
				} else return false;
			}
		}
	}
}
