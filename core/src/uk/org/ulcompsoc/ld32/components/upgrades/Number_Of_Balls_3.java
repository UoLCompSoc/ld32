package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Number_Of_Balls_3 extends Upgrade {
	
	public final float dmg;
	public final float time;
	public final float drops;
	public final float costs;
	public final int simultanousFire;
	public final int stage;
	public UpgradeRoute type;
	
	public Number_Of_Balls_3() {
		dmg = 1;
		time = 1;
		drops = 1;
		costs = 1;
		simultanousFire = 3;
		stage = 3;
		type = UpgradeRoute.RED;
	}
}
