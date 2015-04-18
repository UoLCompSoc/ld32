package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Sniper extends Upgrade {

	
	public Sniper() {
		dmg = 1.2f;
		time = 0.8f;
		drops = 1;
		costs = 1;
		simultanousFire = 0;
		stage = 2;
		type = UpgradeRoute.BLUE;
	}
}
