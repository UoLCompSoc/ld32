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

public class GUIRenderSystem extends IteratingSystem {
	final Batch batch;
	final TextureManager textureManager;
	private OrthographicCamera camera;

	private TextureRegion frame = null;
	private TextureRegion redBallIcon = null;
	private TextureRegion blueBallIcon = null;
	private TextureRegion greenBallIcon = null;

	// alter actual values
	private final Vector3 DFLT_POSITION_OF_THE_FRAME = new Vector3(0.0f, 0.0f, 0.0f);
	private final Vector3 DFLT_POSITION_OF_THE_RED_BALL = new Vector3(30.0f, 100.0f, 0.0f);
	private Vector3 temp;

	@SuppressWarnings("unchecked")
	public GUIRenderSystem(final Batch batch, final TextureManager textureManager, final OrthographicCamera cam,
	        int priority) {
		super(Family.all(Wallet.class).get(), priority);
		this.batch = batch;
		this.textureManager = textureManager;
		this.camera = cam;

		this.frame = new TextureRegion(textureManager.nameMap.get(TextureName.FRAME_1));
		this.redBallIcon = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_R));
		this.blueBallIcon = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_B));
		this.greenBallIcon = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_G));
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Wallet wallet = Mappers.walletMapper.get(entity);

		camera.update();
		temp = camera.unproject(DFLT_POSITION_OF_THE_FRAME.cpy());
		batch.setProjectionMatrix(camera.combined);
		int redcount = wallet.red;
		int bluecount = wallet.blue;
		int greencount = wallet.green;

		final float scale = 0.68f;
		final float w = frame.getRegionWidth() * scale;
		final float h = frame.getRegionHeight() * scale;

		batch.begin();
		batch.draw(frame, temp.x, temp.y - h, w, h);
		temp = camera.unproject(DFLT_POSITION_OF_THE_RED_BALL.cpy());
		batch.draw(redBallIcon, temp.x, temp.y);
		batch.end();
		// batch.draw(textureManager., x, y, originX, originY, width, height,
		// scaleX, scaleY, rotation);
	}

	protected void handleRedCounter(int counter, Batch batch) {

	}

}
