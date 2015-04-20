package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Ball_Damages_Enemies extends Upgrade {
	
	public Ball_Damages_Enemies() {
		dmg = 1;
		range = 1;
		drops = 1;
		costs = 1;
		simultanousFire = 0;
		stage = 2;
		type = UpgradeRoute.REDGREEN;
		name = "Ball_Damages_Enemies";
	}
}
