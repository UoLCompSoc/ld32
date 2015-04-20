package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.components.Drop;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.systems.TowerSystem;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public class UpgradeBallMouseListenerHandler extends ScaleEffectMouseListenerHandler {
	private final Engine engine;

	private final Entity tower;

	private final Drop.Colour upgradeColour;

	private boolean dying = false;

	public UpgradeBallMouseListenerHandler(final Engine engine, final Entity tower, final Drop.Colour upgradeColour) {
		this.engine = engine;
		this.tower = tower;
		this.upgradeColour = upgradeColour;
	}

	@Override
	public void handleButtonDown(Entity thisEntity, MouseButtons button, float mouseX, float mouseY) {
		if (!dying) {
			engine.getSystem(TowerSystem.class).handleUpgrade(tower, upgradeColour);

			thisEntity.add(new Doomed());
		}
	}

	public void notifyGroupDying() {
		this.dying = true;
	}
}
