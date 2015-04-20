package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Range_Increase_1 extends Upgrade {
	
	public Range_Increase_1() {
		this.costs = 1f;
		this.dmg = 1f;
		this.drops = 1f;
		this.simultanousFire = 0;
		this.stage = 1;
		this.range = 1.2f;
		this.type = UpgradeRoute.RED;
		name = "Range_Increase_!";
	}
}
