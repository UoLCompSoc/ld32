package uk.org.ulcompsoc.ld32.components.upgrades;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade.UpgradeRoute;

import com.badlogic.ashley.core.Component;

public class Upgradable extends Component{
	private static final float DFLT_UPGRADE_STATUS = 0.0f;
	public Set<Upgrade> upgrades;
	public float upgradeStatus;
	
	public Upgradable(){
		upgrades = new HashSet<Upgrade>();
	}
	
	/**
	 * Returns all the upgrades with a stage <= the stage given of the
	 * type give.
	 * @param stage
	 * @param type
	 * @return
	 */
	public List<Upgrade> getUpgradesFor(int stage, UpgradeRoute type) {
		ArrayList<Upgrade> ups = new ArrayList<Upgrade>();
		for(Upgrade u : upgrades) {
			if(u.getStage() <= stage && u.getType() == type) {
				ups.add(u);
			}
		}
		return ups;
	}
	
	public List<Upgrade> getUpgradesFor(int stage) {
		ArrayList<Upgrade> ups = new ArrayList<Upgrade>();
		for(Upgrade u : upgrades) {
			if(u.getStage() <= stage) {
				ups.add(u);
			}
		}
		return ups;
	}
	
	public List<Upgrade> getUpgradesFor(UpgradeRoute type) {
		ArrayList<Upgrade> ups = new ArrayList<Upgrade>();
		for(Upgrade u : upgrades) {
			if(u.getType() == type) {
				ups.add(u);
			}
		}
		return ups;
	}
	
	public Set<Upgrade> getAllUpgrades() {
		return upgrades;
	}
}
