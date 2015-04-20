package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;

import com.badlogic.ashley.core.Entity;

public class UpgradeBallMouseListenerHandler extends ScaleEffectMouseListenerHandler {
	public UpgradeBallMouseListenerHandler() {
	}

	@Override
	public void handleButtonDown(Entity tower, MouseButtons button, float mouseX, float mouseY) {
	}

	@Override
	public void handleMouseEnter(Entity tower, float mouseX, float mouseY) {
		super.handleMouseEnter(tower, mouseX, mouseY);
	}

	@Override
	public void handleMouseIn(Entity tower, float mouseX, float mouseY) {
		super.handleMouseIn(tower, mouseX, mouseY);
	}

	@Override
	public void handleMouseLeave(Entity tower, float mouseX, float mouseY) {
		super.handleMouseLeave(tower, mouseX, mouseY);
	}
}
