package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Range_Increase_3 extends Upgrade {
	
	public Range_Increase_3() {
		this.costs = 0.9f;
		this.dmg = 1.1f;
		this.drops = 1f;
		this.simultanousFire = 0;
		this.stage = 3;
		this.range = 1.5f;
		this.type = UpgradeRoute.RED;
		name = "Range_Increase_3";
	}
}
