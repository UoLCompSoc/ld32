package uk.org.ulcompsoc.ld32.components;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;

import com.badlogic.ashley.core.Component;

public class PathFollower extends Component {
	public final float wanderTime;

	public RingSegment segment;

	public float timeWaited = 0.0f;

	public PathFollower(final RingSegment segment) {
		this(segment, 0.5f);
	}

	public PathFollower(final RingSegment segment, float wanderTime) {
		this.segment = segment;
		this.wanderTime = wanderTime;
	}
}
