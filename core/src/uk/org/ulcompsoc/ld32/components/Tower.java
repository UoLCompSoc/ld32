package uk.org.ulcompsoc.ld32.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.components.upgrades.BaseUpgrade;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgradable;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade;

import com.badlogic.ashley.core.Component;

public class Tower extends Component {
	private static final float DFLT_RANGE = 100.0f; // starting range
	private static final float DFLT_FIRE_DELAY = 1f; // default fire delay
	// the chance for a monster to drop currency
	private static final float DFLT_MONSTER_DROP_RATE = 5f;
	public static final float DFLT_DMG = 5.0f; // base damage of the tower
	// how many bullets/missiles the tower fires of at
	// once or with a slight delay between.
	private static final int DFLT_MISSLE_COUNT = 1;

	public float range;
	public float fireDelay;
	public float dropRate;
	public float missileCount;

	// attributes associated with firing
	public float elapsedTime;
	public int pongBonusCounter;

	public List<RingSegment> listOfPointsToScan;
	public Upgrade red;
	public Upgrade blue;
	public Upgrade green;
	public Upgrade ascended;
	public Set<Upgrade> combinations;
	public Upgradable upgrades;

	public Tower(Upgradable ups) {
		this.range = Tower.DFLT_RANGE;
		this.fireDelay = Tower.DFLT_FIRE_DELAY;
		this.dropRate = Tower.DFLT_MONSTER_DROP_RATE;
		this.missileCount = Tower.DFLT_MISSLE_COUNT;
		pongBonusCounter = 0;

		red = new BaseUpgrade();
		blue = new BaseUpgrade();
		green = new BaseUpgrade();
		ascended = new BaseUpgrade();

		this.elapsedTime = 0;

		combinations = new HashSet<Upgrade>();
		listOfPointsToScan = new ArrayList<RingSegment>();
		upgrades = ups;
	}

	public void TimePassed(float deltaTime) {
		this.elapsedTime += deltaTime;
	}

	public Boolean isReadyToFire() {
		if (this.elapsedTime >= this.fireDelay) {
			return true;
		} else {
			return false;
		}
	}

	public void shotHasBeenFired() {
		this.elapsedTime = 0;
	}

	/**
	 * For upgrade cooldown
	 */
	public float upgradeElapsedTime = 0.0f;
	public float upgradeDelay = 3.0f;

	public boolean canUpgrade() {
		if (this.upgradeElapsedTime >= this.upgradeDelay)
			return true;
		else
			return false;
	}

	public void upgraded() {
		this.upgradeElapsedTime = 0;
	}

}
