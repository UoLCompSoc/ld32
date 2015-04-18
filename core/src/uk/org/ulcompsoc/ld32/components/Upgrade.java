package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Upgrade extends Component {

	public float dmgMultiplier, timeDelayFiringMultiplier, dropsMultiplier, costMultiplier;

	public static enum UpgradeType {
		DAMAGE_PLUS(1.1f, 1.0f, 1.0f, 1.0f, 1), SNIPER(1.2f, 0.8f, 1.0f, 1.0f, 1), MORTAR(1.3f, 0.7f, 1.0f, 1.0f, 1);// pure
		                                                                                                             // blue

		// BALL_NUMBER_PLUS_1, BALL_NUMBER_PLUS_2, BALL_NUMBER_PLUS_3,
		// pure red - since there are three stages of the number of Balls I
		// thought its worth keeping it that way
		// MONSTER_DROPS_1, MONSTER_DROPS_2, UPGRADE_COSTS; // pure green

		public float dmg;
		public float time;
		public float drops;
		public float costs;
		public int numberOfSimoultaniousMissiles;

		UpgradeType(float _dmg, float _time, float _drops, float _costs, int number) {
			this.dmg = _dmg;
			this.time = _time;
			this.drops = _drops;
			this.costs = _costs;
		}
	}

	public Upgrade(UpgradeType upgradeType) {
		setUpgrade(upgradeType);
	}

	// fiddle around with values for the sake of balance
	private void setUpgrade(UpgradeType upgradeType) {
		switch (upgradeType) {
		case DAMAGE_PLUS:
			this.dmgMultiplier = 1.1f;
			this.timeDelayFiringMultiplier = 1.0f;
			this.dropsMultiplier = 1.0f;
			this.costMultiplier = 1.0f;
		case SNIPER:
			this.dmgMultiplier = 1.2f;
			this.timeDelayFiringMultiplier = 0.8f;
			this.dropsMultiplier = 1.0f;
			this.costMultiplier = 1.0f;
		case MORTAR:
			this.dmgMultiplier = 1.3f;
			this.timeDelayFiringMultiplier = 0.7f;
			this.dropsMultiplier = 1.0f;
			this.costMultiplier = 1.0f;

			// case BALL_NUMBER_PLUS_1:
			// this.dmgMultiplier = 1.0f;
			// this.timeDelayFiringMultiplier = 1.0f;
			// this.dropsMultiplier = 1.0f;
			// this.costMultiplier = 1.0f;
			// case BALL_NUMBER_PLUS_2:
			// this.dmgMultiplier = 1.0f;
			// this.timeDelayFiringMultiplier = 1.0f;
			// this.dropsMultiplier = 1.0f;
		}
	}
}
