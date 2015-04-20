package uk.org.ulcompsoc.ld32.components.upgrades;

public class Double_Shot extends Upgrade {
	public Double_Shot(){
		this.costs = 1f;
		this.dmg = 1.15f;
		this.drops = 1.05f;
		this.simultanousFire = 1;
		this.stage = 3;
		this.range = 1.3f;
		this.type = UpgradeRoute.GREENBLUE;
		name = "Double_Shot";
	}
}
