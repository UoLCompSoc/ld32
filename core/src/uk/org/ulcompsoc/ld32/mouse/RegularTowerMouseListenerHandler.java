package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;

import com.badlogic.ashley.core.Entity;

public class RegularTowerMouseListenerHandler extends BaseTowerMouseListenerHandler {
	public RegularTowerMouseListenerHandler() {
	}

	@Override
	public void handleClick(Entity tower, MouseButtons button, float mouseX, float mouseY) {
		System.out.println("Reg");
	}
}
