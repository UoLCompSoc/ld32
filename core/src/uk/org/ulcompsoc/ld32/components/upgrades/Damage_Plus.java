package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Damage_Plus extends Upgrade{
	
	public final float dmg;
	public final float time;
	public final float drops;
	public final float costs;
	public final int simultanousFire;
	public final int stage;
	public UpgradeRoute type;
	
	public Damage_Plus() {
		dmg = 1.1f;
		time = 1;
		drops = 1;
		costs = 1;
		simultanousFire = 0;
		stage = 1;
		type = UpgradeRoute.BLUE;
	}
}
