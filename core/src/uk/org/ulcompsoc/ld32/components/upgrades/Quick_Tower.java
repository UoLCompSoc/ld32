package uk.org.ulcompsoc.ld32.components.upgrades;

public class Quick_Tower extends Upgrade {
	public Quick_Tower() {
		this.dmg = 1.0f;
		this.costs = 0.8f;
		this.drops = 1f;
		this.simultanousFire = 0;
		this.stage = 2;
		this.range = 1.2f;
		this.type = UpgradeRoute.GREENBLUE;
		name = "Quick_Tower";
	}
}
