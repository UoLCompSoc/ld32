package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.components.Damage;
import uk.org.ulcompsoc.ld32.components.MouseListener;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgradable;
import uk.org.ulcompsoc.ld32.util.Mappers;
import uk.org.ulcompsoc.ld32.util.TextureManager;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

public class EmptyTowerMouseListenerHandler extends BaseTowerMouseListenerHandler {
	private final TextureManager textureManager;

	public EmptyTowerMouseListenerHandler(final TextureManager textureManager) {
		this.textureManager = textureManager;
	}

	@Override
	public void handleClick(Entity tower, MouseButtons button, float mouseX, float mouseY) {
		final float scale = Mappers.renderableMapper.get(tower).baseScale;
		final Renderable towerRen = new Renderable(new TextureRegion(
		        textureManager.nameMap.get(TextureName.BASIC_TOWER))).setScale(scale);
		final Position towerPos = Mappers.positionMapper.get(tower);

		tower.add(new Tower(new Upgradable()));
		tower.add(new Damage(Tower.DFLT_DMG));
		tower.add(new Upgradable());

		tower.remove(Renderable.class);
		tower.remove(MouseListener.class);
		tower.add(towerRen);
		tower.add(new MouseListener(new RegularTowerMouseListenerHandler(), new Circle(towerPos.getX(),
		        towerPos.getY(), towerRen.getHeight())));
	}
}
