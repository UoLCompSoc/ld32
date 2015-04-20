package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Monster_Drops_1 extends Upgrade{
	
	public Monster_Drops_1(){
		dmg = 1.0f;
		time = 1.0f;
		drops = 1.1f;
		costs = 0.95f;
		simultanousFire = 0;
		stage = 1;
		type = UpgradeRoute.GREEN;
		name = "Monster_Drops_1";
	}
}
