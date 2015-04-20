package uk.org.ulcompsoc.ld32.components.upgrades;

public class Double_Shot extends Upgrade {
	public Double_Shot(){
		this.costs = 0.95f;
		this.dmg = 1.15f;
		this.drops = 1.05f;
		this.simultanousFire = 0;
		this.stage = 3;
		this.time = 1.0f;
		this.type = UpgradeRoute.GREENBLUE;
		name = "Double_Shot";
	}
}
