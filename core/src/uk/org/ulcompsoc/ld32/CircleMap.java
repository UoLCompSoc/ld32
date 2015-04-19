package uk.org.ulcompsoc.ld32;

import java.util.ArrayList;
import java.util.List;

public class CircleMap {
	public final float radius;
	public final int ringCount;

	public final List<Ring> rings = new ArrayList<Ring>();

	public CircleMap(float radius, int ringCount) {
		this.ringCount = ringCount;
		this.radius = radius;

		generate();
	}

	private void generate() {
		final float ringHeight = radius / ringCount;
		final int segmentCount = 5;

		for (int i = 0; i < ringCount; ++i) {
			// percentage decrease of size
			final float pctage = ((float) i / (float) ringCount);
			final float sizeModifier = (1.0f - pctage);

			final float ringRadius = sizeModifier * radius;
			final float segmentSize = (float) Math.PI * 2 / segmentCount;

			// rings need to be linked together somehow

			rings.add(new Ring(radius * sizeModifier, ringHeight, segmentCount));
			final RingSegment[] currentRing = rings.get(i).segments;
			// System.out.format("Generated ring, radius = %f, height = %f, segSize = %f\n",
			// ringRadius, ringHeight,
			// segmentSize);

			for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
				final RingSegment newSegment = new RingSegment(ringRadius - ringHeight / 2.0f, segmentIndex
				        * segmentSize, segmentSize);

				setEuclideanCoordinates(newSegment);
				if (segmentIndex != 0) {
					newSegment.previous = currentRing[segmentIndex - 1];
				} else if (i != 0) {
					newSegment.previous = rings.get(i - 1).segments[segmentCount - 1];
				}

				currentRing[segmentIndex] = newSegment;
			}
		}

		for (int ringIndex = 0; ringIndex < rings.size(); ++ringIndex) {
			final Ring ring = rings.get(ringIndex);

			for (int segmentIndex = 0; segmentIndex < ring.segments.length; ++segmentIndex) {
				if (segmentIndex == ring.segments.length - 1) {
					if (ringIndex != rings.size() - 1) {
						ring.segments[segmentIndex].next = rings.get(ringIndex + 1).segments[0];
						ring.segments[segmentIndex].isTrivialTransition = false;
					}
				} else {
					ring.segments[segmentIndex].next = ring.segments[segmentIndex + 1];
				}
			}
		}
	}

	private void setEuclideanCoordinates(RingSegment segment) {
		float x1 = (float) (segment.middleR * Math.cos(segment.startPhi + segment.widthInRadians));
		float y1 = (float) (segment.middleR * Math.sin(segment.startPhi + segment.widthInRadians));
		float x2 = (float) (segment.middleR * Math.cos(segment.startPhi));
		float y2 = (float) (segment.middleR * Math.sin(segment.startPhi));

		segment.euclideanPositions[0] = x1;
		segment.euclideanPositions[1] = y1;
		segment.euclideanPositions[2] = x2;
		segment.euclideanPositions[3] = y2;

	}

	public RingSegment getFirstSegment() {
		return rings.get(0).segments[0];
	}

	public static class Ring {
		public final float radius;
		public final RingSegment[] segments;
		public final float height;

		public Ring(float radius, float height, int segmentCount) {
			this.radius = radius;
			this.height = height;

			segments = new RingSegment[segmentCount];
		}
	}

	public static class RingSegment {
		public final float middleR;
		public final float middlePhi;

		public final float widthInRadians;
		public final float startPhi;

		public boolean isTrivialTransition;

		// x1, y1, x2, y2
		public final float[] euclideanPositions = new float[4];

		public RingSegment previous = null, next = null;

		public RingSegment(float middleR, float startPhi, float widthInRadians) {
			this(middleR, startPhi, widthInRadians, true);
		}

		public RingSegment(float middleR, float startPhi, float widthInRadians, boolean isTrivialTransition) {
			this.middleR = middleR;
			this.startPhi = startPhi;
			this.widthInRadians = widthInRadians;
			this.middlePhi = this.startPhi + this.widthInRadians / 2.0f;
			this.isTrivialTransition = isTrivialTransition;
		}
	}
}
