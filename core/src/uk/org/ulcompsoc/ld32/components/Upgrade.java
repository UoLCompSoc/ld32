package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Upgrade extends Component{

	
	public float dmgMultiplier, timeDelayFiringMultiplier, dropsMultiplier, costMultiplier; 
	int numberOfFiresToIncreaseBy, stage;
	public static enum UpgradeType {

		DAMAGE_PLUS(1.1f, 1.0f,1.0f,1.0f, 0, 1), 
		SNIPER(1.2f, 0.8f, 1.0f, 1.0f, 0, 2),
		MORTAR(1.3f, 0.7f, 1.0f, 1.0f, 0, 3), // pure blue
		
		BALL_DAMAGES_ENEMIES(1.15f, 0.9f, 1.0f, 1.0f, 1, 3),
		DOUBLE_SHOT(1.15f, 0.8f, 1.1f, 0.8f, 0, 3),
		BALL_EFFECT_PLUS(1.1f, 1.1f, 1.0f, 1.0f, 0, 3),
		ALL_IN_AOE(1.05f, 1.05f, 1.05f, 0.95f, 1, 3),
		BALLS_GIVE_DROPS_3(1.05f, 1f, 1f,1f, 1, 3),
		BALL_SPLITS(1f, 1.05f, 1.1f,0.90f, 1, 3),
		FIRE_DELAY(1.1f, 1f, 1.1f, 0.9f, 0, 3),
		
		BALL_CHARGE(1.1f, 1.0f, 1.0f, 1.0f, 0, 2),
		QUICK_TOWER(1.1f, 1.4f, 1.0f, 1.0f, 0, 2),
		BALLS_GIVE_DROPS_2(1.0f, 1.0f, 1.2f, 0.8f, 0, 2),
		
		
		BALL_NUMBER_PLUS_1(1.0f, 1.0f, 1.0f, 1.0f, 1, 1), 
		BALL_NUMBER_PLUS_2(1.0f, 1.0f, 1.0f, 1.0f, 1, 2), 
		BALL_NUMBER_PLUS_3(1.0f, 1.0f, 1.0f, 1.0f, 1, 3),

		// pure red - since there are three stages of the number of Balls I
		// thought its worth keeping it that way

		MONSTER_DROPS_1(1.0f, 1.0f, 1.1f, 1.0f, 0, 1),
		MONSTER_DROPS_2(1.0f, 1.0f, 1.1f, 1.0f, 0, 2),
		UPGRADE_COSTS(1.0f, 1.0f, 1.0f, 0.9f, 0, 3), // pure green
		
		ASCENDED_TOWER_UPGRADE(2.0f, 2.0f, 2.0f, 2.0f, 0, 4);
		
		public final float dmg;
		public final float time;
		public final float drops;
		public final float costs;

		public int numberOfSimoultaniousMissiles; // this should be addedto the number of missles fired simoultaiounsly 
		
		public int stage;
		UpgradeType(float _dmg, float _time, float _drops, float _costs, int _number, int _stage){
			this.dmg = _dmg;
			this.time = _time;
			this.drops = _drops;
			this.costs = _costs;
			this.stage = _stage;
		}
	}
	
	private UpgradeType upgradetype;
	
	//Constructor 
	public Upgrade(UpgradeType upgradeType) {
		setUpgrade(upgradeType);
	}

	// fiddle around with values for the sake of balance
	private void setUpgrade(UpgradeType upgradeType) {
		this.dmgMultiplier = upgradeType.dmg;
		this.costMultiplier = upgradeType.costs;
		this.dropsMultiplier = upgradeType.drops;
		this.timeDelayFiringMultiplier = upgradeType.time;
		this.numberOfFiresToIncreaseBy = upgradeType.numberOfSimoultaniousMissiles;
		this.upgradetype = upgradeType;
		this.stage = upgradeType.stage;
		
	}
	
	public UpgradeType getUpgradeType(){
		return this.upgradetype;
	}
}
