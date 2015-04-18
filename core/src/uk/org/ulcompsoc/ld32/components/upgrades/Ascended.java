package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Ascended {
	public final float dmg;
	public final float time;
	public final float drops;
	public final float costs;
	public final int simultanousFire;
	public final int stage;
	public UpgradeRoute type;
	
	public Ascended(){
		this.dmg = 2.0f;
		this.time = 2.0f;
		this.drops = 2.0f;
		this.costs = 2.0f;
		this.simultanousFire = 0;
		this.stage = 4;
		this.type = UpgradeRoute.ASCENDED;
	}
}
