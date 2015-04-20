package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Damage_Plus_2 extends Upgrade {
	
	public Damage_Plus_2() {
		dmg = 1.5f;
		range = 1;
		drops = 1;
		costs = 1f;
		simultanousFire = 0;
		stage = 2;
		type = UpgradeRoute.BLUE;
		name = "Damage_Plus_2";
	}
}
