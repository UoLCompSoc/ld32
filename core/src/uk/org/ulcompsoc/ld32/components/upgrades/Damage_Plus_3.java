package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Damage_Plus_3 extends Upgrade {
	
	public Damage_Plus_3() {
		dmg = 1.7f;
		range = 1;
		drops = 1;
		costs = 1f;
		simultanousFire = 0;
		stage = 3;
		type = UpgradeRoute.BLUE;
		name = "Damage_Plus_3";
	}
}
