package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Damage_Plus extends Upgrade{
	
	
	public Damage_Plus() {
		dmg = 1.3f;
		range = 1;
		drops = 1;
		costs = 1;
		simultanousFire = 0;
		stage = 1;
		type = UpgradeRoute.BLUE;
		name= "Damage_Plus";
	}
}
