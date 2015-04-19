package uk.org.ulcompsoc.ld32.systems;

import java.util.Random;

import uk.org.ulcompsoc.ld32.components.CanItDrop;
import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.components.Drop;
import uk.org.ulcompsoc.ld32.components.Drop.Colour;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.util.Mappers;
import uk.org.ulcompsoc.ld32.util.TextureManager;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DoomedSystem extends IteratingSystem {
	private Engine engine = null;
	private final Random random = new Random();
	final TextureManager textureManager;

	private TextureRegion red;
	private TextureRegion blue;
	private TextureRegion green;
	
	@SuppressWarnings("unchecked")
	public DoomedSystem(int priority,final TextureManager textureManager) {
		super(Family.all(Doomed.class).get(), priority);
		this.textureManager = textureManager;
		
		this.red = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_R));
		this.blue = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_B));
		this.green = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_G));
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
		Position position = Mappers.positionMapper.get(entity);
		if (canItDrop != null) {
			boolean redDrop = shouldItDrop(canItDrop.redDropChance, CanItDrop.RED_BOOSTER);
			if (!redDrop) {
				CanItDrop.RED_BOOSTER += CanItDrop.DFLT_BOOSTER_INCREASE;
			} else {
				CanItDrop.RED_BOOSTER = 0;
				Entity toAdd = new Entity();
				toAdd.add(new Drop(Colour.RED));
				toAdd.add(position);
				toAdd.add(new Renderable(red));
				engine.addEntity(toAdd);
			}
			boolean blueDrop = shouldItDrop(canItDrop.blueDropChance, CanItDrop.RED_BOOSTER);
			if (!blueDrop) {
				CanItDrop.BLUE_BOOSTER += CanItDrop.DFLT_BOOSTER_INCREASE;
			} else {
				CanItDrop.BLUE_BOOSTER = 0;
				Entity toAdd2 = new Entity();
				toAdd2.add(new Drop(Colour.BLUE));
				toAdd2.add(position);
				toAdd2.add(new Renderable(blue));
				engine.addEntity(toAdd2);
			}
			boolean greenDrop = shouldItDrop(canItDrop.greenDropChance, CanItDrop.GREEN_BOOSTER);
			if (!greenDrop) {
				CanItDrop.GREEN_BOOSTER += CanItDrop.DFLT_BOOSTER_INCREASE;
			} else {
				CanItDrop.GREEN_BOOSTER = 0;
				Entity toAdd3 = new Entity();
				toAdd3.add(new Drop(Colour.GREEN));
				toAdd3.add(position);
				toAdd3.add(new Renderable(green));
				engine.addEntity(toAdd3);

			}
		}

		engine.removeEntity(entity);
	}

	private boolean shouldItDrop(float chance, float booster) {
		// if booster doesn't equal 0
		int toprint;
		float topri;
		if (booster > 0) {
			// if chance with booster will go beyond 1.0f - we want to create
			// infinite upgrades
			if (chance + booster >= 1.0f) {
				int temp = (int) (chance + booster);
				toprint = random.nextInt(temp);
				topri = random.nextFloat();
				// System.out.println("the requirement:"+(toprint+topri)+" , dropchance: "+(chance+booster));
				if (toprint + topri < chance + booster) {
					return true;
				} else
					return false;

			} else {
				// if chance with booster is below 1.0f
				topri = random.nextFloat();
				// System.out.println("the requirement:"+topri+" dropchance:"+(chance+booster));
				if (topri < chance + booster) {
					return true;
				} else
					return false;
			}
			// if booster equals zero
		} else {
			// if chance is bigger than 1.0f
			if (chance >= 1.0f) {
				int temp2 = (int) chance;
				toprint = random.nextInt(temp2);
				topri = random.nextFloat();
				// System.out.println("the requirement:"+(topri+toprint)+" dropchance:"+chance);
				if (toprint + topri < chance) {
					return true;
				} else
					return false;
			} else {
				// if chance is below 1.0f
				topri = random.nextFloat();
				// System.out.println("the requirement:"+topri+" dropchance:"+chance);
				if (topri < chance) {
					return true;
				} else
					return false;
			}
		}
	}
}
