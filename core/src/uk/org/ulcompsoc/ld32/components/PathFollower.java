package uk.org.ulcompsoc.ld32.components;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;

import com.badlogic.ashley.core.Component;

public class PathFollower extends Component {
	private static final float DEFAULT_WANDER_TIME = 0.75f;

	public float wanderTime = DEFAULT_WANDER_TIME;

	public RingSegment segment;

	public float timeWaited = 0.0f;

	private boolean straightPath = true;
	public boolean continueToNull = false;
	public boolean continueOnce = false;

	public PathFollower(final RingSegment segment) {
		this(segment, DEFAULT_WANDER_TIME);
	}

	public PathFollower(final RingSegment segment, float wanderTime) {
		this.wanderTime = wanderTime;
		initialise(segment);
	}

	public PathFollower continueOnce() {
		continueOnce = true;
		continueToNull = false;

		return this;
	}

	public PathFollower continueToNull() {
		continueOnce = false;
		continueToNull = true;

		return this;
	}

	public boolean shouldContinue() {
		if (continueOnce) {
			continueOnce = continueToNull = false;
			return true;
		}

		return continueToNull;
	}

	// initialise = start wandering the path from segment to segment.next
	// straightPath: true = only translate along phi
	// straightPath: false = translate along phi, then along r

	public PathFollower initialise(final RingSegment segment) {
		return initialise(segment, true);
	}

	public PathFollower initialise(final RingSegment segment, boolean straightPath) {
		timeWaited = 0.0f;

		this.segment = segment;
		this.straightPath = straightPath;

		return this;
	}

	public boolean isStraightPath() {
		return straightPath;
	}
}
