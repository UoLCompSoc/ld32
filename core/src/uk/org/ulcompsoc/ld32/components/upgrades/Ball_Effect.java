package uk.org.ulcompsoc.ld32.components.upgrades;

public class Ball_Effect extends Upgrade {

	public Ball_Effect(){
		this.costs = 1.0f;
		this.dmg = 1.05f;
		this.drops = 1.0f;
		this.simultanousFire = 1;
		this.stage = 3;
		this.time = 1.1f;
		this.type = UpgradeRoute.REDBLUE;
	}
}
