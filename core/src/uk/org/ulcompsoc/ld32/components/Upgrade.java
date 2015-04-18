package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Upgrade extends Component {

	public static enum UpgradeType {
		BASIC_TOWER, DAMAGE_PLUS, SNIPER, MORTAR, // pure blue
		BALL_NUMBER_PLUS_1, BALL_NUMBER_PLUS_2, BALL_NUMBER_PLUS_3,
		// pure red - since there are three stages of the number of Balls I
		// thought its worth keeping it that way
		MONSTER_DROPS_1, MONSTER_DROPS_2, MONSTER_DROPS_3
	}

	public Upgrade(UpgradeType upgradeType) {
		setUpgrade(upgradeType);
	}

	private void setUpgrade(UpgradeType upgradeType) {
		switch (upgradeType) {

		}
	}
}
