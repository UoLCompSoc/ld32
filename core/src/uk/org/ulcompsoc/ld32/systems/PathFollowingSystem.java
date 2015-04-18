package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.components.PathFollower;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;

public class PathFollowingSystem extends IntervalIteratingSystem {
	public final static float DEFAULT_INTERVAL = 0.05f;
	private final float interval;

	@SuppressWarnings("unchecked")
	public PathFollowingSystem(int priority) {
		super(Family.all(Position.class, PathFollower.class).get(), DEFAULT_INTERVAL, priority);

		this.interval = DEFAULT_INTERVAL;
	}

	@Override
	protected void processEntity(Entity entity) {
		final Position p = Mappers.positionMapper.get(entity);
		final PathFollower pf = Mappers.pathFollowerMapper.get(entity);

		pf.timeWaited += interval;

		if (pf.timeWaited >= pf.wanderTime) {
			pf.timeWaited -= pf.wanderTime;

			if (pf.segment != null) {
				pf.segment = pf.segment.next;
			}

			if (pf.segment != null) {
				p.setPolar(pf.segment.middleR, pf.segment.startPhi + pf.segment.widthInRadians / 2);
			} else {
				entity.add(new Doomed());
			}
		}
	}
}
