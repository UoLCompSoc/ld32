package uk.org.ulcompsoc.ld32.components.upgrades;

public class Ball_Splits extends Upgrade {

	public Ball_Splits(){
		this.costs = 0.9f;
		this.dmg = 1.05f;
		this.drops = 1.1f;
		this.simultanousFire = 0;
		this.stage = 3;
		this.time = 1.05f;
		this.type = UpgradeRoute.REDGREEN;
	}
}