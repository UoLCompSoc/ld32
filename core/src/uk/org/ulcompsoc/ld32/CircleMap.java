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
		for (int i = 0; i < ringCount; ++i) {
			// percentage decrease of size
			final float pctage = ((float) i / (float) ringCount);
			final float sizeModifier = (1.0f - pctage);
			final int segmentCount = 5;

			final float ringRadius = sizeModifier * radius;
			final float segmentSize = (float) Math.PI * 2 / segmentCount;
			final float ringHeight = radius / ringCount;

			// rings need to be linked together somehow

			rings.add(new Ring(radius * sizeModifier, ringHeight, segmentCount));
			final RingSegment[] currentRing = rings.get(i).segments;
			System.out.format("Generated ring, radius = %f, height = %f, segSize = %f\n", ringRadius, ringHeight,
			        segmentSize);

			for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
				currentRing[segmentIndex] = new RingSegment(segmentIndex * segmentSize, segmentSize);

				if (segmentIndex != 0) {
					currentRing[segmentIndex].previous = currentRing[segmentIndex - 1];
				} else if (i != 0) {
					currentRing[segmentIndex].previous = rings.get(i - 1).segments[segmentCount - 1];
				}
			}
		}
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
		public final float widthInRadians;
		public final float startPhi;

		public RingSegment previous = null;

		public RingSegment(float startPhi, float widthInRadians) {
			this.startPhi = startPhi;
			this.widthInRadians = widthInRadians;
		}
	}
}
