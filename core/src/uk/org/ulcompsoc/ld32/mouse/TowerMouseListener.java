package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.components.MouseListener;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseListenerHandler;
import uk.org.ulcompsoc.ld32.components.Scalable;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;

public class TowerMouseListener implements MouseListenerHandler {
	public TowerMouseListener() {

	}

	@Override
	public void handleClick(final Entity tower, MouseButtons button, float mouseX, float mouseY) {
		System.out.format("Click (%s) at (%f, %f)\n", button.name(), mouseX, mouseY);
	}

	@Override
	public void handleMouseEnter(final Entity tower, float mouseX, float mouseY) {
		final Scalable sc = Mappers.scalableMapper.get(tower);
		final MouseListener ml = Mappers.mouseListenerMapper.get(tower);

		if (sc != null) {
			sc.timeElapsed = ml.timeIn;
		}
	}

	@Override
	public void handleMouseIn(Entity tower, float mouseX, float mouseY) {
		final Scalable sc = Mappers.scalableMapper.get(tower);
		final MouseListener ml = Mappers.mouseListenerMapper.get(tower);

		if (sc != null) {
			sc.timeElapsed = ml.timeIn;
		}
	}

	@Override
	public void handleMouseLeave(final Entity tower, float mouseX, float mouseY) {
		final Scalable sc = Mappers.scalableMapper.get(tower);

		if (sc != null) {
			sc.timeElapsed = 0.0f;
		}
	}
}
