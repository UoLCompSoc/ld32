package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Upgrade_Costs extends Upgrade{
	
	public Upgrade_Costs(){
		this.dmg = 1.0f;
		this.time = 1.0f;
		this.drops = 1.1f;
		this.costs = 0.7f;
		this.simultanousFire = 0;
		this.stage = 3;
	}
}