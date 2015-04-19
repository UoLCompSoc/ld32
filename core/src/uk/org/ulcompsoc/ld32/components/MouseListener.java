package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Circle;

public class MouseListener extends Component {
	public final MouseListenerHandler handler;
	public final Circle region;

	public MouseListener(final MouseListenerHandler handler, final Circle region) {
		this.handler = handler;
		this.region = region;
	}

	public static interface MouseListenerHandler {
		public void handleClick(int button, float mouseX, float mouseY);

		public void handleMouseEnter(float mouseX, float mouseY);

		public void handleMouseLeave(float mouseX, float mouseY);
	}
}
