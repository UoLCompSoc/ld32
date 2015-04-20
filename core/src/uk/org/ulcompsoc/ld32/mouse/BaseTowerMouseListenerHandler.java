package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.systems.GUIRenderSystem;

import com.badlogic.ashley.core.Entity;

public abstract class BaseTowerMouseListenerHandler extends ScaleEffectMouseListenerHandler {

	@Override
	public void handleMouseEnter(Entity thisEntity, float mouseX, float mouseY) {
		super.handleMouseEnter(thisEntity, mouseX, mouseY);
		GUIRenderSystem.selectedTowerEntity = thisEntity;
	}

	@Override
	public void handleMouseLeave(Entity thisEntity, float mouseX, float mouseY) {
		super.handleMouseLeave(thisEntity, mouseX, mouseY);
		GUIRenderSystem.selectedTowerEntity = null;
	}
}
