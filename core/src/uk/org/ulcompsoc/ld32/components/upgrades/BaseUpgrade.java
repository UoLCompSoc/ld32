package uk.org.ulcompsoc.ld32.components.upgrades;

public class BaseUpgrade extends Upgrade {
	public BaseUpgrade() {
		dmg = 1;
		range = 1;
		drops = 1;
		costs = 1;
		simultanousFire = 0;
		stage = 0;
		type = UpgradeRoute.NONE;
		name = "BaseUpgrade";
	}
}
