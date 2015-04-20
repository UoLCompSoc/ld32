package uk.org.ulcompsoc.ld32.components.upgrades;

public class Ball_Effect extends Upgrade {

	public Ball_Effect(){
		this.costs = 1.0f;
		this.dmg = 1.3f;
		this.drops = 1.3f;
		this.simultanousFire = 1;
		this.stage = 3;
		this.range = 1.2f;
		this.type = UpgradeRoute.REDBLUE;
		name = "Ball_Effect";
	}
}
