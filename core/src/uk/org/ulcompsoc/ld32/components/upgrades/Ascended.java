package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Ascended extends Upgrade{
	
	public Ascended(){
		this.dmg = 2.0f;
		this.time = 2.0f;
		this.drops = 2.0f;
		this.costs = 2.0f;
		this.simultanousFire = 0;
		this.stage = 4;
		this.type = UpgradeRoute.ASCENDED;
		name = "Ascended";
	}
}
