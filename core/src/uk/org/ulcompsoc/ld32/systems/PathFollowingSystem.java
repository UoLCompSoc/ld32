package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.components.PathFollower;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.util.LDUtil;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;

public class PathFollowingSystem extends IntervalIteratingSystem {
	public final static float DEFAULT_INTERVAL = 0.01f;
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
			p.setPolar(pf.segment.next.middleR, pf.segment.next.middlePhi);

			if (pf.shouldContinue()) {
				final RingSegment next = pf.segment.next;

				if (next != null) {
					pf.initialise(next);
					return;
				} else {
					pf.continueOnce = pf.continueToNull = false;
				}
			}

			entity.remove(PathFollower.class);
		} else {
			if (pf.isStraightPath()) {
				final float newPhi = pf.segment.middlePhi + LDUtil.smoothStep(0.0f, pf.wanderTime, pf.timeWaited)
				        * Math.abs(pf.segment.next.middlePhi - pf.segment.middlePhi);
				//System.out.println("Phi: " + newPhi);
				p.setPolar(pf.segment.next.middleR, newPhi);
			} else {
				if (pf.timeWaited / pf.wanderTime >= 0.75f) {
					p.setPolar(LDUtil.smoothStep(0.0f, pf.wanderTime / 2.0f, pf.timeWaited - 0.25f)
					        * pf.segment.next.middleR, pf.segment.next.middlePhi);
				} else {
					p.setPolar(pf.segment.middleR, LDUtil.smoothStep(0.0f, pf.wanderTime / 2.0f, pf.timeWaited)
					        * pf.segment.next.middlePhi);
				}
			}
		}
	}
}
