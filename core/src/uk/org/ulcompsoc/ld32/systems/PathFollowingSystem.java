package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.components.Nexus;
import uk.org.ulcompsoc.ld32.components.PathFollower;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.util.LDUtil;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;

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
			if (pf.segment.next == null) {
				Gdx.app.log("AAH", "Bad next!");
				return;
			}

			p.setPolar(pf.segment.next.middleR, pf.segment.next.middlePhi);

			if (pf.checkAndResetContinue()) {
				final RingSegment next = pf.segment.next;

				if (next != null) {
					pf.initialise(next);
					return;
				} else {
					pf.continueOnce = pf.continueToNull = false;
				}
			}

			if (pf.shouldKillWhenDone()) {
				Nexus.reduceHealth(Mappers.damageMapper.get(entity).getDamageDealt());
				entity.add(new Doomed());
			}

			entity.remove(PathFollower.class);
		} else {
			if (pf.segment.next == null) {
				if (pf.shouldKillWhenDone()) {
					Nexus.reduceHealth(Mappers.damageMapper.get(entity).getDamageDealt());
					entity.add(new Doomed());
				}

				entity.remove(PathFollower.class);
				return;
			}

			final float startR = pf.segment.middleR;
			final float destinationR = pf.segment.next.middleR;

			final float startPhi = pf.segment.middlePhi;
			// if the next phi is smaller then we've done a full loop
			// so the destination needs another 2 *pi so we don't go backwards
			final float destinationPhi = (pf.segment.next.middlePhi < pf.segment.middlePhi ? pf.segment.next.middlePhi
			        + 2 * LDUtil.PI : pf.segment.next.middlePhi);

			if (pf.isStraightPath()) {
				final float newPhi = startPhi + LDUtil.smoothStep(0.0f, pf.wanderTime, pf.timeWaited)
				        * Math.abs(destinationPhi - startPhi) * pf.transitionDirection;
				// System.out.println("Phi: " + newPhi);
				p.setPolar(destinationR, newPhi);
			} else {
				if (pf.timeWaited / pf.wanderTime >= 0.5f) {
					// if we're over halfway through the total transition, move
					// along R

					// note (destinationR - startR) is brittle with regards to
					// directions, we assume we always move inwards on R
					p.setPolar(
					        startR
					                + LDUtil.smoothStep(0.0f, pf.wanderTime / 2.0f, pf.timeWaited
					                        - (pf.wanderTime / 2.0f)) * (destinationR - startR), destinationPhi);
				} else {
					// for the first half of the transition, move along phi
					final float newPhi = startPhi + LDUtil.smoothStep(0.0f, pf.wanderTime / 2.0f, pf.timeWaited)
					        * Math.abs(destinationPhi - startPhi) * pf.transitionDirection;
					p.setPolar(startR, newPhi);
				}
			}
		}
	}
}
