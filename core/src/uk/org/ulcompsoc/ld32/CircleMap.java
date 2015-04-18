package uk.org.ulcompsoc.ld32;

import java.util.ArrayList;
import java.util.List;

public class CircleMap {
	public final float radius;
	public final int ringCount;

	public final List<MapSegment[]> rings = new ArrayList<MapSegment[]>();

	public CircleMap(float radius, int ringCount) {
		this.ringCount = ringCount;
		this.radius = radius;

		generate();
	}

	private void generate() {
		final float biggestCircumference = 2.0f * (float) Math.PI * radius;

		for (int i = 0; i < ringCount; ++i) {
			// percentage decrease of size
			final float sizeModifier = (1.0f - ((float) i / (float) ringCount));
			final int segmentCount = 5;

			final float ringCircumference = sizeModifier * biggestCircumference;
			final float segmentSize = ringCircumference / segmentCount;

			// rings need to be linked together somehow

			rings.add(new MapSegment[segmentCount]);

			for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
				rings.get(i)[segmentIndex] = new MapSegment(segmentIndex * segmentSize, segmentSize);
			}
		}
	}

	public static class MapSegment {
		public final float widthInRadians;
		public final float startPhi;

		public MapSegment(float startPhi, float widthInRadians) {
			this.startPhi = startPhi;
			this.widthInRadians = widthInRadians;
		}
	}
}
