package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Number_Of_Balls_2 extends Upgrade {
	
	public Number_Of_Balls_2() {
		dmg = 1;
		time = 1f;
		drops = 1;
		costs = 1;
		simultanousFire = 2;
		stage = 2;
		type = UpgradeRoute.RED;
		name = "Number_Of_Balls_2";
	}
}
