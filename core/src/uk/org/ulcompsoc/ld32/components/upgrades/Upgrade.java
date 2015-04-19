package uk.org.ulcompsoc.ld32.components.upgrades;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Component;

public abstract class Upgrade extends Component{
	
	public static enum UpgradeRoute {
		RED, GREEN, BLUE, ASCENDED, //single
		REDGREEN, REDBLUE, GREENBLUE, //double
		REDGREENBLUE; //triple


	}
	
	protected float dmg;
	protected float time;
	protected float drops;
	protected float costs;
	protected int simultanousFire;
	protected int stage;
	protected UpgradeRoute type;
	
	public Upgrade(){
		
	}
	
		//damage, time, drops, cost, simul, stage
	/*	DAMAGE_PLUS(1.1f, 1.0f,1.0f,1.0f, 0, 1, false), 
		SNIPER(1.2f, 0.8f, 1.0f, 1.0f, 0, 2, false),
		MORTAR(1.3f, 0.7f, 1.0f, 1.0f, 0, 3, false), // pure blue
		
		BALL_DAMAGES_ENEMIES(1.15f, 0.9f, 1.0f, 1.0f, 1, 3, true),
		DOUBLE_SHOT(1.15f, 0.8f, 1.1f, 0.8f, 0, 3, true),
		BALL_EFFECT_PLUS(1.1f, 1.1f, 1.0f, 1.0f, 0, 3, true),
		ALL_IN_AOE(1.05f, 1.05f, 1.05f, 0.95f, 1, 3, true),
		BALLS_GIVE_DROPS_3(1.05f, 1f, 1f,1f, 1, 3, true),
		BALL_SPLITS(1f, 1.05f, 1.1f,0.90f, 1, 3, true),
		FIRE_DELAY(1.1f, 1f, 1.1f, 0.9f, 0, 3, true),
		
		BALL_CHARGE(1.1f, 1.0f, 1.0f, 1.0f, 0, 2, true),
		QUICK_TOWER(1.1f, 1.4f, 1.0f, 1.0f, 0, 2, true),
		BALLS_GIVE_DROPS_2(1.0f, 1.0f, 1.2f, 0.8f, 0, 2, true),
		
		
		BALL_NUMBER_PLUS_1(1.0f, 1.0f, 1.0f, 1.0f, 1, 1, false), 
		BALL_NUMBER_PLUS_2(1.0f, 1.0f, 1.0f, 1.0f, 1, 2, false), 
		BALL_NUMBER_PLUS_3(1.0f, 1.0f, 1.0f, 1.0f, 1, 3, false),

		// pure red - since there are three stages of the number of Balls I
		// thought its worth keeping it that way

		MONSTER_DROPS_1(1.0f, 1.0f, 1.1f, 1.0f, 0, 1, false),
		MONSTER_DROPS_2(1.0f, 1.0f, 1.1f, 1.0f, 0, 2, false),
		UPGRADE_COSTS(1.0f, 1.0f, 1.0f, 0.9f, 0, 3, false), // pure green
		
		ASCENDED_TOWER_UPGRADE(2.0f, 2.0f, 2.0f, 2.0f, 0, 4, false);
		*/
	
	public int getStage() {
		return stage;
	}
	
	public UpgradeRoute getType() {
		return type;
	}
	public float getDamage(){
		return this.dmg;
	}
	public float getCosts(){
		return this.costs;
	}
	public float getDrops(){
		return this.drops;
	}
	public int getSimultaneousFire(){
		return this.simultanousFire;
	}
	public float getTimeDelay(){
		return this.time;
	}
	
}
