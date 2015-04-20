package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.components.MouseListener;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseListenerHandler;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;

public abstract class BaseTowerMouseListenerHandler implements MouseListenerHandler {
	@Override
	abstract public void handleClick(final Entity tower, MouseButtons button, float mouseX, float mouseY);

	@Override
	public void handleMouseEnter(final Entity tower, float mouseX, float mouseY) {
		final MouseListener ml = Mappers.mouseListenerMapper.get(tower);
		final Renderable sc = Mappers.renderableMapper.get(tower);

		if (sc != null) {
			sc.scaleAnimTimeElapsed = ml.timeIn;
		}
	}

	@Override
	public void handleMouseIn(Entity tower, float mouseX, float mouseY) {
		final Renderable sc = Mappers.renderableMapper.get(tower);
		final MouseListener ml = Mappers.mouseListenerMapper.get(tower);

		if (sc != null) {
			sc.scaleAnimTimeElapsed = ml.timeIn;
		}
	}

	@Override
	public void handleMouseLeave(final Entity tower, float mouseX, float mouseY) {
		final Renderable sc = Mappers.renderableMapper.get(tower);

		if (sc != null) {
			sc.scaleAnimTimeElapsed = 0.0f;
		}
	}
}
