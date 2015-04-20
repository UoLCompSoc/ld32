package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.components.Drop;
import uk.org.ulcompsoc.ld32.components.Drop.Colour;
import uk.org.ulcompsoc.ld32.components.MouseListener;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.util.DeathListener;
import uk.org.ulcompsoc.ld32.util.Mappers;
import uk.org.ulcompsoc.ld32.util.TextureManager;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

public class RegularTowerMouseListenerHandler extends ScaleEffectMouseListenerHandler implements DeathListener {
	private final Engine engine;
	private final TextureManager textureManager;

	private boolean childrenAlive = false;
	private Entity[] children = new Entity[3];

	public RegularTowerMouseListenerHandler(final Engine engine, final TextureManager textureManager) {
		this.engine = engine;
		this.textureManager = textureManager;
	}

	@Override
	public void handleButtonDown(Entity tower, MouseButtons button, float mouseX, float mouseY) {
		if (!childrenAlive) {
			final Position towerPos = Mappers.positionMapper.get(tower);
			final Renderable towerRen = Mappers.renderableMapper.get(tower);

			childrenAlive = true;
			int index = 0;
			for (Drop.Colour color : Drop.Colour.values()) {
				children[index] = generateSelectionEntity(towerPos, towerRen, color);
				engine.addEntity(children[index]);
				++index;
			}
		}
	}

	private Entity generateSelectionEntity(final Position towerPos, final Renderable towerRen, Drop.Colour colour) {
		final Entity e = new Entity();

		final Renderable r = new Renderable(
		        new TextureRegion(textureManager.nameMap.get(TextureName.UPGRADE_BALL_GREY))).setColor(
		        colour.renderColor).setScale(0.5f);

		final float xMod = (colour == Colour.RED ? -1.5f : colour == Colour.GREEN ? 0.0f : 1.5f);
		final float xOffset = r.getMaximumWidth() * xMod;

		final Position p = Position.fromEuclidean(towerPos.getX() + xOffset,
		        towerPos.getY() + towerRen.getMaximumHeight() / 2.0f - Math.abs(xMod) * 0.75f);

		e.add(p);
		e.add(r);
		e.add(new MouseListener(new UpgradeBallMouseListenerHandler(e, this), new Circle(p.getX(), p.getY(), r
		        .getWidth() * 0.95f)).withInitialCooldown(0.75f));

		return e;
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
