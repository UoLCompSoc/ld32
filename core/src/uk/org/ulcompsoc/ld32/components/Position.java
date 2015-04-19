package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class Position extends Component {
	private Vector2 position = new Vector2(0.0f, 0.0f);

	private boolean isPolarDirty = false;
	private float r = 0.0f;
	private float phi = 0.0f;

	private void calculateEuclideanFromPolar() {
		if (isPolarDirty) {
			position.x = r * (float) Math.cos(phi);
			position.y = r * (float) Math.sin(phi);

			isPolarDirty = false;
		}
	}

	public float getX() {
		calculateEuclideanFromPolar();
		return position.x;
	}

	public float getY() {
		calculateEuclideanFromPolar();
		return position.y;
	}

	public void setX(float x) {
		position.x = x;

		recalculatePolarFromEuclidean();
	}

	public void setY(float y) {
		position.y = y;

		recalculatePolarFromEuclidean();
	}

	public void moveX(float x) {
		position.x += x;

		recalculatePolarFromEuclidean();
	}

	public void moveY(float y) {
		position.y += y;

		recalculatePolarFromEuclidean();
	}

	private void recalculatePolarFromEuclidean() {
		r = (float) Math.sqrt(position.x * position.x + position.y * position.y);
		phi = (float) Math.atan2(position.y, position.x);
	}

	public Position translatePolarDistance(float r) {
		this.r += r;
		isPolarDirty = true;
		return this;
	}

	public Position movePolarAngle(float phi) {
		this.phi += phi;

		if (this.phi >= (float) Math.PI * 2.0) {
			this.phi -= Math.PI * 2.0;
		}

		isPolarDirty = true;

		return this;
	}

	/**
	 * @return the value of r, a distance used in polar cooridinates. sqrt is
	 *         slow, consider using {@link #getRSquared()} if possible.
	 */
	public float getR() {
		return r;
	}

	public float getPhi() {
		return phi;
	}

	public Position setPolar(float r, float phi) {
		this.r = r;
		this.phi = phi;

		isPolarDirty = true;

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
