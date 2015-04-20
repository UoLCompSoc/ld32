package uk.org.ulcompsoc.ld32.components.upgrades;

public class Sniper extends Upgrade {
	public Sniper() {
		dmg = 1.5f;
		range = 1.5f;
		drops = 1;
		costs = 1.2f;
		simultanousFire = 0;
		stage = 2;
		type = UpgradeRoute.BLUE;
		name = "Sniper";
	}
}
