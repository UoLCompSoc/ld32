package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.LD32;
import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.components.Drop;
import uk.org.ulcompsoc.ld32.components.Fade;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.systems.TowerSystem;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
			if (!engine.getSystem(TowerSystem.class).handleUpgrade(tower, upgradeColour)) {
				engine.addEntity(makeInvalidAnimationEntity(mouseX, mouseY));
			}

			thisEntity.add(new Doomed());
		}
	}

	public void notifyGroupDying() {
		this.dying = true;
	}

	private Entity makeInvalidAnimationEntity(float x, float y) {
		final Entity invalid = new Entity();

		final float fadeLength = 1.0f;

		invalid.add(Position.fromEuclidean(x, y));
		invalid.add(new Fade(1.0f, true));

		final TextureRegion[] regions = LD32.textureManager.animationRegionMap.get(TextureName.INVALID_ACTION);
		final Renderable r = new Renderable(new Animation(fadeLength / regions.length, regions)).setScale(0.3f);
		invalid.add(r);

		return invalid;
	}
}
