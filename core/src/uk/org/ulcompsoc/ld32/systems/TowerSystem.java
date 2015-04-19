package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Damage;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.upgrades.*;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class TowerSystem extends EntitySystem {
	private Engine engine = null;
	
	public TowerSystem() {
		this(0);
	}
	public TowerSystem(int priority) {
		super(priority);
	}
	public boolean setRedUpgrade(Entity e) {
		Tower tower = e.getComponent(Tower.class);
		if (tower.redBalls < 5) {
			return false;
		}
		
		tower.redBalls = tower.redBalls - 5;
		if (tower.red == null) {
			tower.red = new Number_Of_Balls_1();
			updateCombos(e);
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
		updateCombos(e);
		return true;
	}
	
	public boolean setBlueUpgrade(Entity e) {
		Tower tower = e.getComponent(Tower.class);
		if (tower.blueBalls < 5) {
			return false;
		}
		tower.blueBalls = tower.blueBalls - 5;
		if (tower.blue == null) {
			tower.blue = new Damage_Plus();
			updateCombos(e);
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
		updateCombos(e);
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
	
	
	private void updateCombos(Entity e) {
		Tower tower = e.getComponent(Tower.class);
		
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
		updateTowerStats(e);
	}

	private void updateTowerStats(Entity e) {
		Tower tower = e.getComponent(Tower.class);
		Damage damageComp = e.getComponent(Damage.class);
		
		for(Upgrade up : tower.combinations){
			damageComp.useMultiplier(up.getDamage());
			tower.dropRate*= up.getDrops();
			tower.fireDelay*= up.getTimeDelay();
			tower.missileCount= Math.max(tower.missileCount, up.getSimultaneousFire());
		}
	}
	
	public void TimePassed(Entity e, float deltaTime){
		Tower tower = e.getComponent(Tower.class);
		tower.elapsedTime+=deltaTime;
	}
	
	public Boolean isReadyToFire(Entity e) {
		Tower tower = e.getComponent(Tower.class);

		if(tower.elapsedTime >= tower.fireDelay){
			return true;
		} else {
			return false;
		}
	}
	
	public void shotHasBeenFired(Entity e){
		Tower tower = e.getComponent(Tower.class);
		tower.elapsedTime=0;
	}

	public boolean containsUpgrade(Entity e, Upgrade up) {
		Tower tower = e.getComponent(Tower.class);
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

	public float getDamageDealt(Entity e) {
		return e.getComponent(Damage.class).getDamageDealt();
	}
}