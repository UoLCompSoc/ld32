package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Wallet;
import uk.org.ulcompsoc.ld32.util.Mappers;
import uk.org.ulcompsoc.ld32.util.TextureManager;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GUIRenderSystem extends IteratingSystem {
	final Batch batch;
	final TextureManager textureManager;
	private OrthographicCamera camera;

	private final TextureRegion frame = null;

	private static final float DFLT_POSITION_OF_THE_FRAME_X = 0;
	private static final float DFLT_POSITION_OF_THE_FRANE_Y = 0;

	@SuppressWarnings("unchecked")
	public GUIRenderSystem(final Batch batch, final TextureManager textureManager, final OrthographicCamera cam,
	        int priority) {
		super(Family.all(Wallet.class).get(), priority);
		this.batch = batch;
		this.textureManager = textureManager;
		this.camera = cam;
		// this.frame = new
		// TextureRegion(textureManager.nameMap.get(TextureName.));
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Wallet wallet = Mappers.walletMapper.get(entity);
		int redcount = wallet.red;
		int bluecount = wallet.blue;
		int greencount = wallet.green;

		// batch.draw(textureManager., x, y, originX, originY, width, height,
		// scaleX, scaleY, rotation);
	}
}
