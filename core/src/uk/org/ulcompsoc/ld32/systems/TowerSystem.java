package uk.org.ulcompsoc.ld32.systems;

import java.util.Random;

import uk.org.ulcompsoc.ld32.components.Damage;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.Wallet;
import uk.org.ulcompsoc.ld32.components.upgrades.*;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;

//TODO ACCESS COLOURED BALLS FROM WALLET/PADDLE
public class TowerSystem extends EntitySystem {
	private Engine engine = null;
	
	public Wallet wallet;
	
	public TowerSystem(Wallet w) {
		this(0, w);
	}
	
	public TowerSystem(int priority, Wallet w) {
		super(priority);
		wallet = w;
	}
	
	public boolean setRedUpgrade(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		if (wallet.red < 5) {
			return false;
		}
		
		wallet.red = wallet.red - 5;
		switch (tower.red.getStage()) {
			case 0: {
				tower.red = new Number_Of_Balls_1();
				break;
			}
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
		updateTowerStats(entity);
		return true;
	}
	
	public boolean setBlueUpgrade(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		if (wallet.blue < 5) {
			return false;
		}
		wallet.blue = wallet.blue - 5;
		switch(tower.blue.getStage()) {
			case 0: {
				tower.blue = new Damage_Plus();
				break;
			}
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
		updateTowerStats(entity);
		return true;
	}
	
	public boolean setGreenUpgrade(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		
		if(wallet.green < 5) {
			return false;
		}
		wallet.green = wallet.green -5;
		switch(tower.green.getStage()) {
			case 0: {
				tower.green = new Monster_Drops_1();
				break;
			}
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
		updateCombos(entity);
		updateTowerStats(entity);
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

		if(tower.ascended.getStage() == 0 && Math.min(redStage, Math.min(greenStage, blueStage)) >= 3) {
			tower.ascended = new Ascended();
		}
	}

	private void updateTowerStats(Entity entity) {
		Tower tower = Mappers.towerMapper.get(entity);
		Damage damageComp = Mappers.damageMapper.get(entity);
		
		Upgrade[] baseUpgrades = {tower.red, tower.green, tower.blue, tower.ascended};
		
		for(Upgrade up : baseUpgrades) {
			damageComp.useMultiplier(up.getDamage());
			tower.dropRate*= up.getDrops();
			tower.fireDelay*= up.getTimeDelay();
			tower.missileCount= Math.max(tower.missileCount, up.getSimultaneousFire());
		}
		
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

	public static boolean pongBonus(Entity entity) {
		System.out.println("DETECTED");
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
