package uk.org.ulcompsoc.ld32.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.components.upgrade.Upgrade;
import uk.org.ulcompsoc.ld32.components.upgrade.Upgrade.UpgradeType;

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

	public Tower() {
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
	}
	
	// set the upgrade of the blue component
	public boolean setBlueUpgrade() {
		if(blueBalls < 5) {
			return false;
		}
		blueBalls = blueBalls -5;
		if(blue == null) {
			blue = new Upgrade(UpgradeType.DAMAGE_PLUS);
			return true;
		}
		
		switch(blue.getUpgradeType()) {
			case DAMAGE_PLUS: {
				blue = new Upgrade(Upgrade.UpgradeType.SNIPER);
				break;
			}
			
			case SNIPER: {
				blue = new Upgrade(UpgradeType.MORTAR);
				break;
			}
			
			case MORTAR: {
				blue = new Upgrade(UpgradeType.DOUBLE_SHOT);
				break;
			}
			
			default: return false;
		}
		return true;
	}
	
	public boolean setRedUpgrade() {
		if(redBalls < 5) {
			return false;
		}
		
		redBalls = redBalls -5;
		
		if(red == null) {
			red = new Upgrade(Upgrade.UpgradeType.BALL_NUMBER_PLUS_1);
			return true;
		}
		
		switch(red.getUpgradeType()) {
			case BALL_NUMBER_PLUS_1: {
				red = new Upgrade(Upgrade.UpgradeType.BALL_NUMBER_PLUS_2);
				break;
			}
			
			default: return false;
		}
		
		return false;
	}
	
	public void updateCombos() {
		int redStage = red.stage;
		int blueStage = blue.stage;
		int greenStage = green.stage;
		
		Upgrade.UpgradeType[] blueRedCombs = {Upgrade.UpgradeType.BALL_CHARGE, 
				Upgrade.UpgradeType.BALL_EFFECT_PLUS, Upgrade.UpgradeType.BALL_DAMAGES_ENEMIES};
		
		Upgrade.UpgradeType[] redGreenCombos = {Upgrade.UpgradeType.BALLS_GIVE_DROPS_3, 
				Upgrade.UpgradeType.BALL_SPLITS, Upgrade.UpgradeType.ALL_IN_AOE};
		
		Upgrade.UpgradeType[] blueGreenCombos = {Upgrade.UpgradeType.DOUBLE_SHOT, Upgrade.UpgradeType.QUICK_TOWER};
		
		int blueRedStage = Math.min(redStage, blueStage);
		int redGreenStage = Math.min(redStage, greenStage);
		int blueGreenStage = Math.min(blueStage, greenStage);
			
		for(Upgrade.UpgradeType ut : Upgrade.getAllUpgradesForStage(redGreenStage, true) {
			if(redGreenCombos.)
		}
	}
}
