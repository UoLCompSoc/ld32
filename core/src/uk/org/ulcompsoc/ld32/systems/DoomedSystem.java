package uk.org.ulcompsoc.ld32.systems;

import java.util.Random;

import uk.org.ulcompsoc.ld32.LD32;
import uk.org.ulcompsoc.ld32.components.CanItDrop;
import uk.org.ulcompsoc.ld32.components.DeathAnimation;
import uk.org.ulcompsoc.ld32.components.DoomNotifier;
import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.components.Drop;
import uk.org.ulcompsoc.ld32.components.Drop.Colour;
import uk.org.ulcompsoc.ld32.components.EntityLink;
import uk.org.ulcompsoc.ld32.components.Fade;
import uk.org.ulcompsoc.ld32.components.Player;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Wallet;
import uk.org.ulcompsoc.ld32.components.enemies.Enemy;
import uk.org.ulcompsoc.ld32.util.Mappers;
import uk.org.ulcompsoc.ld32.util.TextureManager;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class DoomedSystem extends IteratingSystem {
	private final Random random = new Random();

	private Engine engine = null;

	private final Entity player;

	private final TextureManager textureManager;
	private TextureRegion ballRegion;

	private final LD32 ld32;

	@SuppressWarnings("unchecked")
	public DoomedSystem(int priority, final Entity player, final LD32 ld32) {
		super(Family.all(Doomed.class).get(), priority);

		this.player = player;
		this.textureManager = LD32.textureManager;
		this.ballRegion = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_GREY));

		this.ld32 = ld32;
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
		handleDrop(entity, deltaTime);
		handleBonus(entity, deltaTime);
		handleNotifyDeath(entity, deltaTime);
		handleDeathAnimation(entity, deltaTime);
		handleLink(entity, deltaTime);
		handleScore(entity, deltaTime);

		engine.removeEntity(entity);
	}

	private void handleDeathAnimation(final Entity entity, float deltaTime) {
		final DeathAnimation da = Mappers.deathAnimationMapper.get(entity);
		final Position p = Mappers.positionMapper.get(entity);
		final Renderable origR = Mappers.renderableMapper.get(entity);

		if (da != null) {
			final Entity e = new Entity();
			final Renderable r = new Renderable(da.animation).setScale(origR.scale).setColor(origR.color);

			e.add(Position.fromPolar(p.getR(), p.getPhi()));
			e.add(r);
			e.add(new Fade(da.animation.getAnimationDuration(), true));

			engine.addEntity(e);
		}
	}

	private void handleBonus(final Entity entity, float deltaTime) {
		final Drop drop = Mappers.dropMapper.get(entity);

		if (drop != null) {
			final Wallet wallet = Mappers.walletMapper.get(player);

			switch (drop.colour) {
			case BLUE:
				++wallet.blue;
				break;

			case GREEN:
				++wallet.green;
				break;

			case RED:
				++wallet.red;
				break;

			default:
				throw new GdxRuntimeException("Unhandled drop type in handleBonus in DoomedSystem.");
			}
		}
	}

	private void handleNotifyDeath(final Entity entity, float deltaTime) {
		final DoomNotifier dn = Mappers.doomNotifierMapper.get(entity);

		if (dn != null) {
			dn.notifyOfDeath(entity);
		}
	}

	private void handleLink(final Entity entity, float deltaTime) {
		final EntityLink link = Mappers.entityLinkMapper.get(entity);

		if (link != null) {
			for (Entity e : link.children) {
				engine.removeEntity(e);
			}

			link.children.clear();
		}
	}

	/**
	 * Handles dropping an item by entities with CanItDrop components
	 * 
	 * @param entity
	 * @param deltaTime
	 */
	private void handleDrop(Entity entity, float deltaTime) {
		final CanItDrop canItDrop = Mappers.canItDropMapper.get(entity);
		final Position position = Mappers.positionMapper.get(entity);

		if (canItDrop != null) {
			final boolean redDrop = shouldItDrop(canItDrop.redDropChance, CanItDrop.RED_BOOSTER);

			if (!redDrop) {
				CanItDrop.RED_BOOSTER += CanItDrop.DFLT_BOOSTER_INCREASE;
			} else {
				CanItDrop.RED_BOOSTER = 0;
				Entity toAdd = new Entity();
				toAdd.add(new Drop(Colour.RED));
				toAdd.add(position);
				toAdd.add(new Renderable(ballRegion).setColor(Color.RED));
				toAdd.add(new Fade().doomAfterFade());
				engine.addEntity(toAdd);
			}

			final boolean blueDrop = shouldItDrop(canItDrop.blueDropChance, CanItDrop.BLUE_BOOSTER);

			if (!blueDrop) {
				CanItDrop.BLUE_BOOSTER += CanItDrop.DFLT_BOOSTER_INCREASE;
			} else {
				CanItDrop.BLUE_BOOSTER = 0;
				Entity toAdd2 = new Entity();
				toAdd2.add(new Drop(Colour.BLUE));
				toAdd2.add(position);
				toAdd2.add(new Renderable(ballRegion).setColor(Color.BLUE));
				toAdd2.add(new Fade().doomAfterFade());
				engine.addEntity(toAdd2);
			}

			final boolean greenDrop = shouldItDrop(canItDrop.greenDropChance, CanItDrop.GREEN_BOOSTER);

			if (!greenDrop) {
				CanItDrop.GREEN_BOOSTER += CanItDrop.DFLT_BOOSTER_INCREASE;
			} else {
				CanItDrop.GREEN_BOOSTER = 0;
				Entity toAdd3 = new Entity();
				toAdd3.add(new Drop(Colour.GREEN));
				toAdd3.add(position);
				toAdd3.add(new Renderable(ballRegion).setColor(Color.GREEN));
				toAdd3.add(new Fade().doomAfterFade());
				engine.addEntity(toAdd3);
			}

			final boolean atomDrop = shouldItDrop(canItDrop.atomDropChance, CanItDrop.ATOM_BOOSTER);

			// atom booster is changed based on the number of ascended towers
			if (atomDrop) {
				engine.addEntity(ld32.makeAtom());
			}
		}
	}

	/**
	 * Increments the player score by the enemy which was doomed
	 * 
	 * @param entity
	 * @param deltaTime
	 */
	private void handleScore(Entity entity, float deltaTime) {
		if (Mappers.enemyMapper.has(entity)) {
			Enemy enemy = Mappers.enemyMapper.get(entity);

			Player.score += enemy.score;
		}
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
				} else {
					return false;
				}
			} else {
				// if chance with booster is below 1.0f
				topri = random.nextFloat();
				// System.out.println("the requirement:"+topri+" dropchance:"+(chance+booster));
				if (topri < chance + booster) {
					return true;
				} else {
					return false;
				}
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
				} else {
					return false;
				}
			} else {
				// if chance is below 1.0f
				topri = random.nextFloat();
				// System.out.println("the requirement:"+topri+" dropchance:"+chance);
				if (topri < chance) {
					return true;
				} else {
					return false;
				}
			}
		}
	}
}
