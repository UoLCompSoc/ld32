package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Fire_Delay_2 extends Upgrade {
	
	public Fire_Delay_2() {
		this.costs = 0.8f;
		this.dmg = 1f;
		this.drops = 1f;
		this.simultanousFire = 0;
		this.stage = 2;
		this.range = 1.1f;
		this.type = UpgradeRoute.GREEN;
		name = "Fire_Delay_2";
	}
}
