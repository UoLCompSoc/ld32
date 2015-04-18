package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Mortar extends Upgrade {
	
	public final float dmg;
	public final float time;
	public final float drops;
	public final float costs;
	public final int simultanousFire;
	public final int stage;
	public UpgradeRoute type;
	
	public Mortar() {
		dmg = 1.3f;
		time = 0.7f;
		drops = 1;
		costs = 1;
		simultanousFire = 0;
		stage = 3;
		type = UpgradeRoute.BLUE;
	}
}
