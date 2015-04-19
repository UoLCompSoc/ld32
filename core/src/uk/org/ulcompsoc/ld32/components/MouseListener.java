package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;

public class MouseListener extends Component {
	public final MouseListenerHandler handler;
	public final Circle region;

	// used internally by MouseListenerSystem
	public static final float animTime = 0.5f;
	public boolean isIn = false;
	public float timeIn = 0.0f;

	public MouseListener(final MouseListenerHandler handler, final Circle region) {
		this.handler = handler;
		this.region = region;
	}

	public static interface MouseListenerHandler {
		public void handleClick(final Entity tower, MouseButtons button, float mouseX, float mouseY);

		public void handleMouseEnter(final Entity tower, float mouseX, float mouseY);

		public void handleMouseIn(final Entity tower, float mouseX, float mouseY);

		public void handleMouseLeave(final Entity tower, float mouseX, float mouseY);
	}

	public static enum MouseButtons {
		LEFT, MIDDLE, RIGHT;
	}
}
