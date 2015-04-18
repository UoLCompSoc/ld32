package uk.org.ulcompsoc.ld32.components.upgrades;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

public class Monster_Drops_2 {
	public final float dmg;
	public final float time;
	public final float drops;
	public final float costs;
	public final int simultanousFire;
	public final int stage;
	public UpgradeRoute type;
	
	public Monster_Drops_2(){
		this.dmg = 1.0f;
		this.time = 1.0f;
		this.drops = 1.2f;
		this.costs = 0.95f;
		this.simultanousFire = 0;
		this.stage = 2;
	}
}