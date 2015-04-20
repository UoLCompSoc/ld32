package uk.org.ulcompsoc.ld32.components.upgrades;

public class Ball_Splits extends Upgrade {
	public Ball_Splits() {
		this.costs = 1.1f;
		this.dmg = 1.1f;
		this.drops = 1.1f;
		this.simultanousFire = 1;
		this.stage = 3;
		this.range = 1.3f;
		this.type = UpgradeRoute.REDGREEN;
		name = "Ball_Splits";
	}
}
