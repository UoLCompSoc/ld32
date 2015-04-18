package uk.org.ulcompsoc.ld32.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;
import uk.org.ulcompsoc.ld32.components.upgrades.*;


import com.badlogic.ashley.core.Component;

public class Tower extends Component {
	private static final float DFLT_RANGE = 10.0f; // starting range
	private static final float DFLT_FIRE_DELAY = 0.5f; // default fire delay
	private static final float DFLT_MONSTER_DROP_RATE = 0.1f;

	public float range;
	public float fireDelay;
	public float dropRate;

	public int redBalls;
	public int blueBalls; // heeeeeeeyooooo :D
	public int greenBalls;

	public List<RingSegment> listOfPointsToScan;
	private Upgrade red;
	private Upgrade blue;
	private Upgrade green;
	private Set<Upgrade> combinations;
	private Upgradable upgrades;
	
	public Tower(Upgradable ups) {
		this.range = Tower.DFLT_RANGE;
		this.fireDelay = Tower.DFLT_FIRE_DELAY;
		this.dropRate = Tower.DFLT_MONSTER_DROP_RATE;

		this.redBalls = 0;
		this.blueBalls = 0;
		this.greenBalls = 0;

		red = null;
		blue = null;
		green = null;

		combinations = new HashSet<Upgrade>();
		listOfPointsToScan = new ArrayList<RingSegment>();
		upgrades =ups;
	}

	// set the upgrade of the blue component
	public boolean setBlueUpgrade() {
		if (blueBalls < 5) {
			return false;
		}
		blueBalls = blueBalls - 5;
		if (blue == null) {
			 blue = new Damage_Plus();
			return true;
		}
		
		switch(blue.getStage()) {
			case 1: {
				blue = new Sniper();
				break;
			}
			case 2: {
				blue = new Mortar();
				break;
			}
			default: return false;
		}
		return true;
	}

	public boolean setRedUpgrade() {
		if (redBalls < 5) {
			return false;
		}

		redBalls = redBalls - 5;

		if (red == null) {
			red = new Number_Of_Balls_1();
			return true;
		}

		switch (red.getStage()) {
			case 1: {
				red = new Number_Of_Balls_2();
				break;
			}
			case 2: {
				red = new Number_Of_Balls_3();
				break;
			}
			default: return false;
				
			}
		return true;
	}

	public void updateCombos() {
		int redStage = red.getStage();
		int greenStage = green.getStage();
		int blueStage = blue.getStage();
		
		combinations.addAll(upgrades.getUpgradesFor(Math.min(redStage,greenStage), UpgradeRoute.REDGREEN));
		combinations.addAll(upgrades.getUpgradesFor(Math.min(redStage,blueStage), UpgradeRoute.REDBLUE));
		combinations.addAll(upgrades.getUpgradesFor(Math.min(greenStage,blueStage), UpgradeRoute.GREENBLUE));
		combinations.addAll(upgrades.getUpgradesFor(Math.min(redStage,Math.min(blueStage, greenStage)), UpgradeRoute.REDGREENBLUE));
		
	}
}
