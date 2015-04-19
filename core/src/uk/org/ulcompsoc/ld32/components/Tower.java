package uk.org.ulcompsoc.ld32.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.components.upgrades.*;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class Tower extends Component {
	private static final float DFLT_RANGE = 100.0f; // starting range
	private static final float DFLT_FIRE_DELAY = 1f; // default fire delay
	private static final float DFLT_MONSTER_DROP_RATE = 5f; //the chance for a monster to drop currency
	public static final float DFLT_DMG = 3.0f; // base damge of the tower
	private static final int DFLT_MISSLE_COUNT = 1; // how many bullets/misslies the tower fires of at once or with a slight delay between.

	public float range;
	public float fireDelay;
	public float dropRate;
	public float missileCount;
	
	//attributes associated with firing
	public float elapsedTime;
	
	public int redBalls;
	public int blueBalls; // heeeeeeeyooooo :D
	public int greenBalls;

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
		this.redBalls = 0;
		this.blueBalls = 0;
		this.greenBalls = 0;

		red = null;
		blue = null;
		green = null;
		
		this.elapsedTime = 0;

		combinations = new HashSet<Upgrade>();
		listOfPointsToScan = new ArrayList<RingSegment>();
		upgrades =ups;
	}
	
	public void TimePassed(float deltaTime){
		this.elapsedTime+=deltaTime;
	}
	
	public Boolean isReadyToFire() {
		if(this.elapsedTime >= this.fireDelay){
			return true;
		} else {
			return false;
		}
	}
	
	public void shotHasBeenFired(){
		this.elapsedTime=0;
	}
}

