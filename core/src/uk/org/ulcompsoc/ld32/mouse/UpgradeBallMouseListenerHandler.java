package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.components.Drop;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.systems.TowerSystem;
import uk.org.ulcompsoc.ld32.util.DeathListener;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public class UpgradeBallMouseListenerHandler extends ScaleEffectMouseListenerHandler {
	private final Engine engine;

	private final Entity tower;

	private final Drop.Colour upgradeColour;
	private final DeathListener deathListener;

	private boolean dying = false;

	public UpgradeBallMouseListenerHandler(final Engine engine, final Entity tower, final Drop.Colour upgradeColour,
	        final DeathListener deathListener) {
		this.engine = engine;
		this.tower = tower;
		this.upgradeColour = upgradeColour;
		this.deathListener = deathListener;
	}

	@Override
	public void handleButtonDown(Entity thisEntity, MouseButtons button, float mouseX, float mouseY) {
		if (!dying) {
			deathListener.notifyOfDeath(thisEntity);

			engine.getSystem(TowerSystem.class).handleUpgrade(tower, upgradeColour);
		}
	}

	public void notifyGroupDying() {
		this.dying = true;
	}
}
