package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class Position extends Component {
	public Vector2 position = new Vector2(0.0f, 0.0f);

	/**
	 * @return the value of r, a distance used in polar cooridinates. sqrt is
	 *         slow, consider using {@link #getRSquared()} if possible.
	 */
	public float getR() {
		return (float) Math.sqrt(getRSquared());
	}

	/**
	 * @return r^2, a distance used in polar coordinates. saves a sqrt if you
	 *         can do comparisons with the squared value.
	 */
	public float getRSquared() {
		return position.x * position.x + position.y * position.y;
	}

	public float getPhi() {
		return (float) Math.atan2(position.y, position.x);
	}

	public Position setPolar(float r, float phi) {
		position.set(r * (float) Math.cos(phi), r * (float) Math.sin(phi));

		return this;
	}

	private Position() {
		this(0.0f, 0.0f);
	}

	private Position(final Vector2 vec) {
		this(vec.x, vec.y);
	}

	private Position(float x, float y) {
		this.position.set(x, y);
	}

	public static Position fromEuclidean(float x, float y) {
		return new Position(x, y);
	}

	public static Position fromPolar(float r, float phi) {
		return new Position().setPolar(r, phi);
	}
}
