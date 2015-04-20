package uk.org.ulcompsoc.ld32.components.upgrades;

public class Mortar extends Upgrade {
	public Mortar() {
		dmg = 1.3f;
		range = 1.1f;
		drops = 1;
		costs = 1.3f;
		simultanousFire = 0;
		stage = 3;
		type = UpgradeRoute.REDBLUE;
		name = "Mortar";
	}
}
