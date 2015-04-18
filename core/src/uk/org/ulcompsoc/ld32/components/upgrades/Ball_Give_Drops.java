package uk.org.ulcompsoc.ld32.components.upgrades;

public class Ball_Give_Drops extends Upgrade{
	
	public Ball_Give_Drops(){
		this.costs = 0.95f;
		this.dmg = 0.95f;
		this.drops = 1.15f;
		this.simultanousFire = 1;
		this.stage = 2;
		this.time = 1.05f;
		this.type = UpgradeRoute.REDGREEN;
	}
}
