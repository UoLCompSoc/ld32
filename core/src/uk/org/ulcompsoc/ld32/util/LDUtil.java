package uk.org.ulcompsoc.ld32.util;

public class LDUtil {
	private LDUtil() {
	}

	public static final float PI = (float) Math.PI;
	public static final float E = (float) Math.E;

	public static float smoothStep(float edge0, float edge1, float t) {
		t = (t - edge0) / (edge1 - edge0);
		// sorry about the nested ternary :P
		t = (t < 0.0f ? 0.0f : (t > 1.0f ? 1.0f : t));

		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	// used in normalCurve
	private static final float SQRT2PI = (float) Math.sqrt(2.0 * Math.PI);

	public static float normalCurve(float edge0, float edge1, float t) {
		return normalCurve(edge0, edge1, t, false);
	}

	public static float normalCurve(float edge0, float edge1, float t, boolean capAfterEdge1) {
		final float mu = 2.0f;
		final float sigma = 0.75f;
		final float sigmaSquared = sigma * sigma;

		t = (t - edge0) / (edge1 - edge0);

		if (capAfterEdge1) {
			final float cap = mu + sigma * 0.4f;
			t = (t < 0.0f ? 0.0f : (t > cap ? cap : t));
		} else {
			t = (t < 0.0f ? 0.0f : t);
		}

		final double exponent = -(((t - mu) * (t - mu)) / (2 * sigmaSquared));
		return (1 / (sigma * SQRT2PI)) * (float) Math.pow(Math.E, exponent);
	}
}
