package uk.org.ulcompsoc.ld32.components;

import java.util.HashMap;
import java.util.Map;

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

	public float clickCooldownRemaining = 0.0f;
	public float clickCooldown = 0.25f;

	/**
	 * Stores the status of the different buttons on a per-frame basis.
	 */
	public Map<MouseButtons, Boolean> buttonMap = new HashMap<MouseButtons, Boolean>();

	public MouseListener(final MouseListenerHandler handler, final Circle region) {
		this.handler = handler;
		this.region = region;

		for (final MouseButtons button : MouseButtons.values()) {
			buttonMap.put(button, false);
		}
	}

	public MouseListener withInitialCooldown(float cooldown) {
		this.clickCooldownRemaining = cooldown;
		return this;
	}

	public static interface MouseListenerHandler {
		public void handleButtonDown(final Entity tower, MouseButtons button, float mouseX, float mouseY);

		public void handleMouseEnter(final Entity tower, float mouseX, float mouseY);

		public void handleMouseIn(final Entity tower, float mouseX, float mouseY);

		public void handleMouseLeave(final Entity tower, float mouseX, float mouseY);
	}

	public static enum MouseButtons {
		LEFT, MIDDLE, RIGHT;
	}
}
