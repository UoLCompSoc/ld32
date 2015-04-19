package uk.org.ulcompsoc.ld32.components;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;

import com.badlogic.ashley.core.Component;

public class PathFollower extends Component {
	private static final float DEFAULT_WANDER_TIME = 1.0f;

	public float wanderTime = DEFAULT_WANDER_TIME;

	public RingSegment segment;

	public float timeWaited = 0.0f;

	public float transitionDirection = 1.0f;

	private boolean straightPath = true;
	public boolean continueToNull = false;
	public boolean continueOnce = false;

	public boolean shouldKillWhenDone = false;

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

	/**
	 * Returns true if the path should continue to segment.next after the
	 * transition is finished.
	 * 
	 * If the segment should only continue once, will reset the continuity
	 * variables so that future calls to this function return false.
	 * 
	 * @return true if the path should continue after the transition is finished
	 */
	public boolean checkAndResetContinue() {
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
		return initialise(segment, segment.isTrivialTransition);
	}

	public PathFollower initialise(final RingSegment segment, boolean straightPath) {
		timeWaited = 0.0f;

		this.segment = segment;
		this.straightPath = straightPath;

		if (segment.next != null) {
			// if (segment.middlePhi <= segment.next.middlePhi) {
			// this.transitionDirection = 1.0f;
			// } else {
			// this.transitionDirection = -1.0f;
			// }
		}

		return this;
	}

	public boolean isStraightPath() {
		return straightPath;
	}

	public boolean shouldKillWhenDone() {
		return shouldKillWhenDone;
	}

	public PathFollower killWhenDone() {
		shouldKillWhenDone = true;
		return this;
	}
}
