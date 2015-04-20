package uk.org.ulcompsoc.ld32.components.upgrades;

public class Sniper extends Upgrade {
	public Sniper() {
		dmg = 1.2f;
		range = 1.3f;
		drops = 1;
		costs = 1.2f;
		simultanousFire = 0;
		stage = 2;
		type = UpgradeRoute.REDBLUE;
		name = "Sniper";
	}
}
