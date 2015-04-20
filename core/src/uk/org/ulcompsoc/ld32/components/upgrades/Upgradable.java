package uk.org.ulcompsoc.ld32.components.upgrades;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

import com.badlogic.ashley.core.Component;

public class Upgradable extends Component {
	public Set<Upgrade> upgrades;
	public float upgradeStatus;

	public Upgradable() {
		upgrades = new HashSet<Upgrade>();
		upgrades.add(new Ascended());
		upgrades.add(new BaseUpgrade());
		
		upgrades.add(new Damage_Plus_1());
		upgrades.add(new Damage_Plus_2());
		upgrades.add(new Damage_Plus_3());

		upgrades.add(new Fire_Delay_1());
		upgrades.add(new Fire_Delay_2());
		upgrades.add(new Fire_Delay_3());
		
		upgrades.add(new Range_Increase_1());
		upgrades.add(new Range_Increase_2());
		upgrades.add(new Range_Increase_3());

		upgrades.add(new Mortar());
		upgrades.add(new Quick_Tower());
		upgrades.add(new Sniper());
	}

	/**
	 * Returns all the upgrades with a stage <= the stage given of the type
	 * give.
	 * 
	 * @param stage
	 * @param type
	 * @return
	 */
	public List<Upgrade> getUpgradesFor(int stage, UpgradeRoute type) {
		ArrayList<Upgrade> ups = new ArrayList<Upgrade>();
		for (Upgrade u : upgrades) {
			if (u.getStage() <= stage && u.getType() == type) {
				ups.add(u);
			}
		}
		return ups;
	}

	public List<Upgrade> getUpgradesFor(int stage) {
		ArrayList<Upgrade> ups = new ArrayList<Upgrade>();
		for (Upgrade u : upgrades) {
			if (u.getStage() <= stage) {
				ups.add(u);
			}
		}
		return ups;
	}

	public List<Upgrade> getUpgradesFor(UpgradeRoute type) {
		ArrayList<Upgrade> ups = new ArrayList<Upgrade>();
		for (Upgrade u : upgrades) {
			if (u.getType() == type) {
				ups.add(u);
			}
		}
		return ups;
	}

	public Set<Upgrade> getAllUpgrades() {
		return upgrades;
	}

	public void addUpgrade(Upgrade u) {
		upgrades.add(u);
	}

	public void removeUpgrade(Upgrade u) {
		upgrades.remove(u);
	}
}
