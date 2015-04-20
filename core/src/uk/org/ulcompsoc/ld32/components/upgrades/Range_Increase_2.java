package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Range_Increase_2 extends Upgrade {
	
	public Range_Increase_2() {
		this.costs = 0.95f;
		this.dmg = 1.1f;
		this.drops = 1f;
		this.simultanousFire = 0;
		this.stage = 1;
		this.range = 1.30f;
		this.type = UpgradeRoute.RED;
		name = "Range_Increase_2";
	}
}
