package uk.org.ulcompsoc.ld32.systems;

import java.util.Random;

import uk.org.ulcompsoc.ld32.LD32;
import uk.org.ulcompsoc.ld32.components.CanItDrop;
import uk.org.ulcompsoc.ld32.components.Damage;
import uk.org.ulcompsoc.ld32.components.Drop;
import uk.org.ulcompsoc.ld32.components.MouseListener;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Rotatable;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.Wallet;
import uk.org.ulcompsoc.ld32.components.upgrades.Ascended;
import uk.org.ulcompsoc.ld32.components.upgrades.Damage_Plus_1;
import uk.org.ulcompsoc.ld32.components.upgrades.Damage_Plus_2;
import uk.org.ulcompsoc.ld32.components.upgrades.Damage_Plus_3;
import uk.org.ulcompsoc.ld32.components.upgrades.Fire_Delay_1;
import uk.org.ulcompsoc.ld32.components.upgrades.Fire_Delay_2;
import uk.org.ulcompsoc.ld32.components.upgrades.Fire_Delay_3;
import uk.org.ulcompsoc.ld32.components.upgrades.Range_Increase_1;
import uk.org.ulcompsoc.ld32.components.upgrades.Range_Increase_2;
import uk.org.ulcompsoc.ld32.components.upgrades.Range_Increase_3;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;
import uk.org.ulcompsoc.ld32.mouse.ScaleEffectMouseListenerHandler;
import uk.org.ulcompsoc.ld32.util.Mappers;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class TowerSystem extends EntitySystem {
	private static final int RED_UPGRADE_COST = 5;
	private static final int GREEN_UPGRADE_COST = 5;
	private static final int BLUE_UPGRADE_COST = 5;
	public static  int NEW_TOWER_COST = 1;

	public Wallet wallet;

	private Engine engine = null;

	public TowerSystem(Wallet w) {
		this(0, w);
	}

	public TowerSystem(int priority, Wallet w) {
		super(priority);
		wallet = w;
	}
	
	public static void calculateNewTowerCost(int numTowers) {
		NEW_TOWER_COST = (int) NEW_TOWER_COST + numTowers;

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

	public boolean handleUpgrade(Entity entity, Drop.Colour upgradeColour) {
		boolean ret = false;

		final Tower tower = Mappers.towerMapper.get(entity);

		switch (upgradeColour) {
		case BLUE:
			ret = setBlueUpgrade(entity, tower);
			break;

		case GREEN:
			ret = setGreenUpgrade(entity, tower);
			break;

		case RED:
			ret = setRedUpgrade(entity, tower);
			break;

		default:
			throw new GdxRuntimeException("Unhandled upgrade type.");
		}

		// Gdx.app.log("TOWER_UPGRADE",
		// String.format("Upgrades (r, g, b) = (%d, %d, %d).",
		// tower.red.getStage(),
		// tower.green.getStage(), tower.blue.getStage()));

		if (ret) {
			final Renderable old = Mappers.renderableMapper.get(entity);
			final float scale = old.baseScale;
			final Color color = old.color;

			final TextureName towerType = getUpgradedSprite(tower);
			final TextureRegion region = new TextureRegion(LD32.textureManager.nameMap.get(towerType));
			entity.add(new Renderable(region).setScale(scale).setColor(color));

			if (towerType.equals(TextureName.TOWER_RGB)) {
				// TODO: this probably shouldn't be here
				// when we ascend, increase atom drop chance.
				CanItDrop.ATOM_BOOSTER += CanItDrop.DFLT_BOOSTER_INCREASE;

				final Entity asc = new Entity();
				final Position p = Mappers.positionMapper.get(entity);

				asc.add(Position.fromPolar(p.getR(), p.getPhi()));

				final Renderable ascR = new Renderable(new TextureRegion(
				        LD32.textureManager.nameMap.get(TextureName.TOWER_ASCENDED))).setScale(scale * 0.9f)
				        .withPriority(5000);
				asc.add(ascR);
				asc.add(new MouseListener(new ScaleEffectMouseListenerHandler() {
					@Override
					public void handleButtonDown(Entity thisEntity, MouseButtons button, float mouseX, float mouseY) {

					}
				}, new Circle(p.getX(), p.getY(), ascR.getWidth())));

				asc.add(new Rotatable().animateRotation(1.5f));
				engine.addEntity(asc);
			}
		}

		return ret;
	}

	public boolean canAffordRed() {
		return wallet.checkBalance(RED_UPGRADE_COST, 0, 0);
	}

	public boolean canAffordGreen() {
		return wallet.checkBalance(0, GREEN_UPGRADE_COST, 0);
	}

	public boolean canAffordBlue() {
		return wallet.checkBalance(0, 0, BLUE_UPGRADE_COST);
	}

	public boolean setRedUpgrade(Entity entity) {
		return setRedUpgrade(entity, Mappers.towerMapper.get(entity));
	}

	public boolean setRedUpgrade(Entity entity, Tower tower) {
		if (!canAffordRed()) {
			return false;
		}

		boolean didUpgrade = false;
		switch (tower.red.getStage()) {
		case 0: {
			tower.red = new Range_Increase_1();
			didUpgrade = true;
			break;
		}
		case 1: {
			tower.red = new Range_Increase_2();
			didUpgrade = true;
			break;
		}
		case 2: {
			tower.red = new Range_Increase_3();
			didUpgrade = true;
			break;
		}
		default:
			return false;
		}

		if (didUpgrade) {
			wallet.sub(RED_UPGRADE_COST, 0, 0);
			updateCombos(entity);
			updateTowerStats(entity);
			return true;
		}

		return false;
	}

	public boolean setGreenUpgrade(Entity entity) {
		return setGreenUpgrade(entity, Mappers.towerMapper.get(entity));
	}

	public boolean setGreenUpgrade(Entity entity, Tower tower) {
		if (!canAffordGreen()) {
			return false;
		}

		boolean didUpgrade = false;

		switch (tower.green.getStage()) {
		case 0: {
			tower.green = new Fire_Delay_1();
			didUpgrade = true;
			break;
		}
		case 1: {
			tower.green = new Fire_Delay_2();
			didUpgrade = true;
			break;
		}
		case 2: {
			tower.green = new Fire_Delay_3();
			didUpgrade = true;
			break;
		}
		default:
			return false;
		}

		if (didUpgrade) {
			wallet.sub(0, GREEN_UPGRADE_COST, 0);
			updateCombos(entity);
			updateTowerStats(entity);

			return true;
		}

		return false;
	}

	public boolean setBlueUpgrade(Entity entity) {
		return setBlueUpgrade(entity, Mappers.towerMapper.get(entity));
	}

	public boolean setBlueUpgrade(Entity entity, Tower tower) {
		if (!canAffordBlue()) {
			return false;
		}

		boolean didUpgrade = false;

		switch (tower.blue.getStage()) {
		case 0: {
			tower.blue = new Damage_Plus_1();
			didUpgrade = true;
			break;
		}
		case 1: {
			tower.blue = new Damage_Plus_2();
			didUpgrade = true;
			break;
		}
		case 2: {
			tower.blue = new Damage_Plus_3();
			didUpgrade = true;
			break;
		}
		default:
			return false;
		}

		if (didUpgrade) {
			wallet.sub(0, 0, BLUE_UPGRADE_COST);
			updateCombos(entity);
			updateTowerStats(entity);
			return true;
		}

		return false;
	}

	private TextureName getUpgradedSprite(final Tower tower) {
		// TODO: This assumes we have a max of 3 upgrades on each type
		// TODO: Make it less gross
		final boolean hasRed = tower.red.getStage() == 3;
		final boolean hasGreen = tower.green.getStage() == 3;
		final boolean hasBlue = tower.blue.getStage() == 3;

		if (hasRed && hasGreen && hasBlue) {
			return TextureName.TOWER_RGB;
		} else if (hasRed && hasGreen && !hasBlue) {
			return TextureName.TOWER_RG;
		} else if (hasRed && !hasGreen && hasBlue) {
			return TextureName.TOWER_RB;
		} else if (!hasRed && hasGreen && hasBlue) {
			return TextureName.TOWER_GB;
		} else if (hasRed && !hasGreen && !hasBlue) {
			return TextureName.TOWER_R;
		} else if (!hasRed && hasGreen && !hasBlue) {
			return TextureName.TOWER_G;
		} else if (!hasRed && !hasGreen && hasBlue) {
			return TextureName.TOWER_B;
		} else {
			return TextureName.BASIC_TOWER;
		}
	}

	private void updateCombos(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		int redStage = tower.red.getStage();
		int greenStage = tower.green.getStage();
		int blueStage = tower.blue.getStage();

		tower.combinations.addAll(tower.upgrades.getUpgradesFor(Math.min(redStage, greenStage), UpgradeRoute.REDGREEN));
		tower.combinations.addAll(tower.upgrades.getUpgradesFor(Math.min(redStage, blueStage), UpgradeRoute.REDBLUE));
		tower.combinations
		        .addAll(tower.upgrades.getUpgradesFor(Math.min(greenStage, blueStage), UpgradeRoute.GREENBLUE));
		tower.combinations.addAll(tower.upgrades.getUpgradesFor(Math.min(redStage, Math.min(blueStage, greenStage)),
		        UpgradeRoute.REDGREENBLUE));

		if (tower.ascended.getStage() == 0 && Math.min(redStage, Math.min(greenStage, blueStage)) >= 3) {
			tower.ascended = new Ascended();
		}
	}

	private void updateTowerStats(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		Damage damageComp = Mappers.damageMapper.get(entity);
		resetStats(entity);

		Upgrade[] baseUpgrades = { tower.red, tower.green, tower.blue, tower.ascended };

		for (Upgrade up : baseUpgrades) {
			damageComp.useMultiplier(up.getDamage());
			tower.dropRate *= up.getDrops();
			tower.fireDelay *= up.getCosts();
			tower.range *= up.getRange();
			tower.missileCount = Math.max(tower.missileCount, up.getSimultaneousFire());
		}

		for (Upgrade up : tower.combinations) {
			damageComp.useMultiplier(up.getDamage());
			tower.dropRate *= up.getDrops();
			tower.fireDelay *= up.getCosts();
			tower.missileCount = Math.max(tower.missileCount, up.getSimultaneousFire());
			tower.range *= up.getRange();
		}
	}

	public boolean containsUpgrade(Entity entity, Upgrade up) {
		Tower tower = Mappers.towerMapper.get(entity);
		if (tower.combinations.contains(up)) {
			return true;
		}

		switch (up.getType()) {
		case RED: {
			if (tower.red.getStage() >= up.getStage()) {
				return true;
			}
		}
		case GREEN: {
			if (tower.green.getStage() >= up.getStage()) {
				return true;
			}
		}
		case BLUE: {
			if (tower.blue.getStage() >= up.getStage()) {
				return true;
			}
		}
		default:
			return false;
		}
	}

	public static boolean pongBonus(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		if (tower.pongBonusCounter >= 10) {
			return false;
		}

		Damage damageComp = Mappers.damageMapper.get(entity);
		Random rand = new Random();
		switch (rand.nextInt(3)) {
		case 0: {
			damageComp.useMultiplier(1.1f);
			break;
		}
		case 1: {
			tower.fireDelay = tower.fireDelay * 0.9f;
			break;
		}
		case 2: {
			tower.range = tower.range * 1.1f;
			break;
		}
		}
		tower.pongBonusCounter++;
		tower.upgraded();
		return true;
	}

	public void resetStats(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		Damage damageComp = Mappers.damageMapper.get(entity);
		damageComp.setDamage(damageComp.getOriginalDamage());
		tower.range = Tower.DFLT_RANGE;
		tower.fireDelay = Tower.DFLT_FIRE_DELAY;
		tower.dropRate = Tower.DFLT_MONSTER_DROP_RATE;
		tower.missileCount = Tower.DFLT_MISSLE_COUNT;
	}
}
