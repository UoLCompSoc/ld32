package uk.org.ulcompsoc.ld32.util;

public class LDUtil {
	private LDUtil() {
	}

	public static final float PI = (float) Math.PI;

	public static float smoothStep(float edge0, float edge1, float t) {
		t = (t - edge0) / (edge1 - edge0);
		// sorry about the nested ternary :P
		t = (t < 0.0f ? 0.0f : (t > 1.0f ? 1.0f : t));

		return t * t * t * (t * (t * 6 - 15) + 10);
	}
}
