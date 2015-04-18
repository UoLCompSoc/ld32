package uk.org.ulcompsoc.ld32.components;

import java.util.ArrayList;
import java.util.List;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;

import com.badlogic.ashley.core.Component;

public class Tower extends Component {
	private static final float DFLT_RANGE = 10.0f; // starting range
	private static final float DFLT_FIRE_DELAY = 0.5f; // default fire delay
	private static final float DFLT_MONSTER_DROP_RATE = 0.1f;

	public float range;
	public float fireDelay;
	public float dropRate;

	public int redBalls;
	public int blueBalls; // heeeeeeeyooooo :D
	public int greenBalls;

	public List<RingSegment> listOfPointsToScan;

	public Tower() {
		this.range = Tower.DFLT_RANGE;
		this.fireDelay = Tower.DFLT_FIRE_DELAY;
		this.dropRate = Tower.DFLT_MONSTER_DROP_RATE;

		this.redBalls = 0;
		this.blueBalls = 0;
		this.greenBalls = 0;

		listOfPointsToScan = new ArrayList<RingSegment>();
	}
}
