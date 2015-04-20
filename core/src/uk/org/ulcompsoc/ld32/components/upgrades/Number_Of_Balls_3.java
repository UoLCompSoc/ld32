package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Number_Of_Balls_3 extends Upgrade {
	public Number_Of_Balls_3() {
		dmg = 1;
		range = 1;
		drops = 1;
		costs = 1;
		simultanousFire = 3;
		stage = 3;
		type = UpgradeRoute.RED;
		name = "Number_Of_Balls_3";
	}
}
