package uk.org.ulcompsoc.ld32.mouse;

import uk.org.ulcompsoc.ld32.LD32;
import uk.org.ulcompsoc.ld32.components.Damage;
import uk.org.ulcompsoc.ld32.components.MouseListener;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.Wallet;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgradable;
import uk.org.ulcompsoc.ld32.systems.TowerSystem;
import uk.org.ulcompsoc.ld32.util.Mappers;
import uk.org.ulcompsoc.ld32.util.TextureManager;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

public class EmptyTowerMouseListenerHandler extends BaseTowerMouseListenerHandler {
	private final TextureManager textureManager;

	private final Engine engine;
	private final Entity player;

	public EmptyTowerMouseListenerHandler(final Engine engine, final Entity player) {
		this.textureManager = LD32.textureManager;

		this.engine = engine;
		this.player = player;
	}

	@Override
	public void handleButtonDown(Entity tower, MouseButtons button, float mouseX, float mouseY) {
		final Wallet wallet = Mappers.walletMapper.get(player);
		int cost = TowerSystem.NEW_TOWER_COST;

		if (wallet.checkBalance(cost, cost, cost)) {
			wallet.sub(cost, cost, cost);
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
			tower.add(new MouseListener(new RegularTowerMouseListenerHandler(engine), new Circle(towerPos.getX(),
			        towerPos.getY(), towerRen.getHeight())).withInitialCooldown(1.0f));
		}
	}
}
