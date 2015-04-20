package uk.org.ulcompsoc.ld32.components.upgrades;

public class Fire_Delay extends Upgrade {

	public Fire_Delay(){
		this.costs = 0.6f;
		this.dmg = 1.05f;
		this.drops = 1.05f;
		this.simultanousFire = 0;
		this.stage = 3;
		this.range = 1.3f;
		this.type = UpgradeRoute.GREENBLUE;
		name = "FireDelay";
	}
	
}
