package uk.org.ulcompsoc.ld32.components.upgrades;

public class Ball_Give_Drops extends Upgrade {

	public Ball_Give_Drops() {
		this.costs = 0.95f;
		this.dmg = 1f;
		this.drops = 1.4f;
		this.simultanousFire = 1;
		this.stage = 2;
		this.range = 1.2f;
		this.type = UpgradeRoute.REDGREEN;
		name = "Ball_Give_Drops";
	}
}
