package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.components.MouseListener;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseListenerHandler;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;

public abstract class ScaleEffectMouseListenerHandler implements MouseListenerHandler {
	@Override
	abstract public void handleButtonDown(final Entity thisEntity, MouseButtons button, float mouseX, float mouseY);

	@Override
	public void handleMouseEnter(final Entity thisEntity, float mouseX, float mouseY) {
		final MouseListener ml = Mappers.mouseListenerMapper.get(thisEntity);
		final Renderable sc = Mappers.renderableMapper.get(thisEntity);

		if (sc != null) {
			sc.scaleAnimTimeElapsed = ml.timeIn;
		}
	}

	@Override
	public void handleMouseIn(Entity thisEntity, float mouseX, float mouseY) {
		final Renderable sc = Mappers.renderableMapper.get(thisEntity);
		final MouseListener ml = Mappers.mouseListenerMapper.get(thisEntity);

		if (sc != null) {
			sc.scaleAnimTimeElapsed = ml.timeIn;
		}
	}

	@Override
	public void handleMouseLeave(final Entity thisEntity, float mouseX, float mouseY) {
		final Renderable sc = Mappers.renderableMapper.get(thisEntity);

		if (sc != null) {
			sc.scaleAnimTimeElapsed = 0.0f;
		}
	}
}
