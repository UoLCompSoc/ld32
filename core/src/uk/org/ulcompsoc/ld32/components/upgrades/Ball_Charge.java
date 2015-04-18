package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Ball_Charge extends Upgrade {
	
	public Ball_Charge() {
		dmg = 1.1f;
		time = 1;
		drops = 1;
		costs = 1;
		simultanousFire = 0;
		stage = 2;
		type = UpgradeRoute.REDBLUE;
	}
}
