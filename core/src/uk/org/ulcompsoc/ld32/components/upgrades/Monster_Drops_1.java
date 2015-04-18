package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Monster_Drops_1 {
	public final float dmg;
	public final float time;
	public final float drops;
	public final float costs;
	public final int simultanousFire;
	public final int stage;
	public UpgradeRoute type;
	
	public Monster_Drops_1(){
		dmg = 1.0f;
		time = 1.0f;
		drops = 1.1f;
		costs = 0.95f;
		simultanousFire = 0;
		stage = 1;
		type = UpgradeRoute.GREEN;
	}
}
