package uk.org.ulcompsoc.ld32.systems;

import java.util.Random;

import uk.org.ulcompsoc.ld32.components.Damage;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.upgrades.*;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;

public class TowerSystem extends EntitySystem {
	private Engine engine = null;
	
	public TowerSystem() {
		this(0);
	}
	
	public TowerSystem(int priority) {
		super(priority);
	}
	
	public boolean setRedUpgrade(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		if (tower.redBalls < 5) {
			return false;
		}
		
		tower.redBalls = tower.redBalls - 5;
		if (tower.red == null) {
			tower.red = new Number_Of_Balls_1();
			updateCombos(entity);
			return true;
		}

		switch (tower.red.getStage()) {
			case 1: {
				tower.red = new Number_Of_Balls_2();
				break;
			}
			case 2: {
				tower.red = new Number_Of_Balls_3();
				break;
			}
			default: return false;
		}
		updateCombos(entity);
		return true;
	}
	
	public boolean setBlueUpgrade(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		if (tower.blueBalls < 5) {
			return false;
		}
		tower.blueBalls = tower.blueBalls - 5;
		if (tower.blue == null) {
			tower.blue = new Damage_Plus();
			updateCombos(entity);
			return true;
		}
		switch(tower.blue.getStage()) {
			case 1: {
				tower.blue = new Sniper();
				break;
			}
			case 2: {
				tower.blue = new Mortar();
				break;
			}
			default: return false;
		}
		updateCombos(entity);
		return true;
	}
	
	public boolean setGreenUpgrade(Entity e) {
		Tower tower = e.getComponent(Tower.class);
		
		if(tower.greenBalls < 5) {
			return false;
		}
		tower.greenBalls = tower.greenBalls -5;
		
		if(tower.green == null) {
			tower.green = new Monster_Drops_1();
			updateCombos(e);
			return true;
		}
		switch(tower.green.getStage()) {
			case 1: {
				tower.green = new Monster_Drops_2();
				break;
			}
			case 2: {
				tower.green = new Upgrade_Costs();
				break;
			}
			default: return false;
		}
		updateCombos(e);
		return true;
	}
	
	
	private void updateCombos(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		
		int redStage = tower.red.getStage();
		int greenStage = tower.green.getStage();
		int blueStage = tower.blue.getStage();
		
		tower.combinations.addAll(tower.upgrades.getUpgradesFor(Math.min(redStage,greenStage), UpgradeRoute.REDGREEN));
		tower.combinations.addAll(tower.upgrades.getUpgradesFor(Math.min(redStage,blueStage), UpgradeRoute.REDBLUE));
		tower.combinations.addAll(tower.upgrades.getUpgradesFor(Math.min(greenStage,blueStage), UpgradeRoute.GREENBLUE));
		tower.combinations.addAll(tower.upgrades.getUpgradesFor(Math.min(redStage,Math.min(blueStage, greenStage)), UpgradeRoute.REDGREENBLUE));
		
		if(tower.ascended == null && Math.min(redStage, Math.min(greenStage, blueStage)) >= 4) {
			tower.ascended = new Ascended();
			tower.combinations.add(tower.ascended);
		}
		updateTowerStats(entity);
	}

	private void updateTowerStats(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		Damage damageComp = Mappers.damageMapper.get(entity);
		
		for(Upgrade up : tower.combinations){
			damageComp.useMultiplier(up.getDamage());
			tower.dropRate*= up.getDrops();
			tower.fireDelay*= up.getTimeDelay();
			tower.missileCount= Math.max(tower.missileCount, up.getSimultaneousFire());
		}
	}
	
	public boolean containsUpgrade(Entity entity, Upgrade up) {
		Tower tower = Mappers.towerMapper.get(entity);
		if(tower.combinations.contains(up)) {
			return true;
		}
		
		switch(up.getType()) {
			case RED: {
				if(tower.red.getStage() >= up.getStage()) {
					return true;
				}
			}
			case GREEN: {
				if(tower.green.getStage() >= up.getStage()) {
					return true;
				}
			}
			case BLUE: {
				if(tower.blue.getStage() >= up.getStage()) {
					return true;
				}
			}
			default: return false;
		}
	}

	public boolean pongBonus(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		if(tower.pongBonusCounter >= 10) {
			return false;
		}
		
		Damage damageComp = Mappers.damageMapper.get(entity);
		Random rand = new Random();
		switch(rand.nextInt(3)) {
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
		return true;
	}
}
