package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.components.Drop;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.util.DeathListener;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public class RegularTowerMouseListenerHandler extends ScaleEffectMouseListenerHandler implements DeathListener {
	private final Engine engine;

	private boolean childrenAlive = false;
	private Entity[] children = new Entity[3];

	public RegularTowerMouseListenerHandler(final Engine engine) {
		this.engine = engine;
	}

	@Override
	public void handleButtonDown(Entity tower, MouseButtons button, float mouseX, float mouseY) {
		// if (!childrenAlive) {
		// childrenAlive = true;
		// int index = 0;
		// for (Drop.Colour color : Drop.Colour.values()) {
		// children[index] = generateSelectionEntity(color);
		// }
		// }
	}

	private Entity generateSelectionEntity(Drop.Colour colour) {
		return new Entity();
	}

	@Override
	public void notifyOfDeath(Entity entity) {
		childrenAlive = false;
		for (int i = 0; i < children.length; ++i) {
			children[i].add(new Doomed());
			children[i] = null;
		}
	}
}
