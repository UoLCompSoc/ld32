package uk.org.ulcompsoc.ld32.components;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;

import com.badlogic.ashley.core.Component;

public class PathFollower extends Component {
	private static final float DEFAULT_WANDER_TIME = 0.25f;

	public float wanderTime = DEFAULT_WANDER_TIME;

	public RingSegment segment;

	public float timeWaited = 0.0f;

	private boolean straightPath = true;

	public PathFollower(final RingSegment segment) {
		this(segment, DEFAULT_WANDER_TIME);
	}

	public PathFollower(final RingSegment segment, float wanderTime) {
		this.wanderTime = wanderTime;
		initialise(segment);
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
