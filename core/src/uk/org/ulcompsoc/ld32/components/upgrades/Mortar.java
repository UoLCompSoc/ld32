package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Mortar extends Upgrade {
	
	
	public Mortar() {
		dmg = 1.7f;
		range = 0.8f;
		drops = 1;
		costs = 1;
		simultanousFire = 0;
		stage = 3;
		type = UpgradeRoute.BLUE;
		name = "Mortar";
	}
}
