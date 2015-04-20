package uk.org.ulcompsoc.ld32.components.upgrades;

public class Ball_Damages_Enemies extends Upgrade {
	public Ball_Damages_Enemies() {
		this.costs = 1.1f;
		this.dmg = 1.3f;
		this.drops = 1.0f;
		this.simultanousFire = 0;
		this.stage = 3;
		this.range = 1.1f;
		this.type = UpgradeRoute.REDBLUE;
		name = "Ball_Damage_Enemies";
	}
}
