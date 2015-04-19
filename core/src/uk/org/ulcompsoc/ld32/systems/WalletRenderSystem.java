package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Wallet;
import uk.org.ulcompsoc.ld32.util.Mappers;
import uk.org.ulcompsoc.ld32.util.TextureManager;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WalletRenderSystem extends IteratingSystem {
	private final Batch batch;

	private float x;
	private float y;

	private TextureRegion red = null;
	private TextureRegion blue = null;
	private TextureRegion green = null;

	@SuppressWarnings("unchecked")
	public WalletRenderSystem(final Batch batch, final TextureManager textureManager, float x, float y, int priority) {
		super(Family.all(Wallet.class).get(), priority);
		this.batch = batch;
		this.x = x;
		this.x = y;

		this.red = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_R));
		this.blue = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_B));
		this.green = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_G));
		// do blue
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final Wallet wallet = Mappers.walletMapper.get(entity);
		
		batch.draw(red, x, y,red.getRegionWidth(), red.getRegionHeight());
		batch.draw(blue, x+3.0f, y+1.0f, blue.getRegionWidth(), blue.getRegionHeight());
		batch.draw(green,x-2.5f, y+0.9f, green.getRegionWidth(), blue.getRegionHeight());
		
		int redCount = wallet.redCurrency;
		int blueCount = wallet.blueCurrency;
		int greenCount = wallet.greenCurrency; // might move it  out
		// once for each of red, green, blue:
		// draw sprite - red at (x,y), green a bit to the right, blue right of
		// that
		// use red.getRegionWidth etc to get size

		// later we'll need numbers there too but that's another story, for now
		// just do sprites.
	}

}
