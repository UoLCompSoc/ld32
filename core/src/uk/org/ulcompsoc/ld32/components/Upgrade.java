package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Upgrade extends Component {

	
	public float dmgMultiplier, timeDelayFiringMultiplier, dropsMultiplier, costMultiplier, numberOfFiresToIncreaseBy;
	public static enum UpgradeType {

		DAMAGE_PLUS(1.1f, 1.0f,1.0f,1.0f, 0), 
		SNIPER(1.2f, 0.8f, 1.0f, 1.0f, 0),
		MORTAR(1.3f, 0.7f, 1.0f, 1.0f, 0), // pure blue
		
		BALL_NUMBER_PLUS_1(1.0f, 1.0f, 1.0f, 1.0f, 1), 
		BALL_NUMBER_PLUS_2(1.0f, 1.0f, 1.0f, 1.0f, 1), 
		BALL_NUMBER_PLUS_3(1.0f, 1.0f, 1.0f, 1.0f, 1),

		// pure red - since there are three stages of the number of Balls I
		// thought its worth keeping it that way

		MONSTER_DROPS_1(1.0f, 1.0f, 1.1f, 1.0f, 0),
		MONSTER_DROPS_2(1.0f, 1.0f, 1.1f, 1.0f, 0),
		UPGRADE_COSTS(1.0f, 1.0f, 1.0f, 0.9f, 0); // pure green
		
		public float dmg;
		public float time;
		public float drops;
		public float costs;

		public int numberOfSimoultaniousMissiles; // this should be added to the number of missiles fired simoultaiounsly 
		
		UpgradeType(float _dmg, float _time, float _drops, float _costs, int number){
			this.dmg = _dmg;
			this.time = _time;
			this.drops = _drops;
			this.costs = _costs;
		}
	}
	
	private UpgradeType upgradetype;
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
	}
	
	public UpgradeType getUpgradeType(){
		return this.upgradetype;
	}
}
