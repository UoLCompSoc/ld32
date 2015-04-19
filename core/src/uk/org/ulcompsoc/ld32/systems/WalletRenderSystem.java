package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Wallet;
import uk.org.ulcompsoc.ld32.util.Mappers;
import uk.org.ulcompsoc.ld32.util.TextureManager;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class WalletRenderSystem extends IteratingSystem {
	private final Batch batch;
	private final OrthographicCamera camera;

	private final Vector3 basePosition = new Vector3(0.0f, 0.0f, 0.0f);
	private Vector3 p = new Vector3(0.0f, 0.0f, 0.0f);

	private TextureRegion red = null;
	private TextureRegion blue = null;
	private TextureRegion green = null;

	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @param priority
	 * @param batch
	 * @param camera
	 * @param textureManager
	 * @param x in screen coords
	 * @param y in screen coords
	 */
	public WalletRenderSystem(int priority, final Batch batch, final OrthographicCamera camera,
	        final TextureManager textureManager, float x, float y) {
		super(Family.all(Wallet.class).get(), priority);
		this.batch = batch;
		this.camera = camera;

		this.basePosition.x = x;
		this.basePosition.y = y;

		this.red = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_R));
		this.blue = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_B));
		this.green = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_G));
		// do blue
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final Wallet wallet = Mappers.walletMapper.get(entity);

		p = camera.unproject(basePosition);

		batch.begin();
		batch.draw(red, p.x, p.y, red.getRegionWidth(), red.getRegionHeight());
		batch.draw(blue, p.x + red.getRegionWidth(), p.y, blue.getRegionWidth(), blue.getRegionHeight());
		batch.draw(green, p.x + red.getRegionWidth() + blue.getRegionWidth(), p.y, green.getRegionWidth(),
		        blue.getRegionHeight());
		batch.end();

		int redCount = wallet.red;
		int blueCount = wallet.blue;
		int greenCount = wallet.green; // might move it out
		// once for each of red, green, blue:
		// draw sprite - red at (x,y), green a bit to the right, blue right of
		// that
		// use red.getRegionWidth etc to get size

		// later we'll need numbers there too but that's another story, for now
		// just do sprites.
	}
}
