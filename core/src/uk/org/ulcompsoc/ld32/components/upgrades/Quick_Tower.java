package uk.org.ulcompsoc.ld32.components.upgrades;

public class Quick_Tower extends Upgrade{
	
	public Quick_Tower(){
		this.dmg = 1.0f;
		this.costs = 0.95f;
		this.drops = 0.95f;
		this.simultanousFire = 0;
		this.stage = 2;
		this.time = 1.2f;
		this.type = UpgradeRoute.GREENBLUE;
	}
	
}
