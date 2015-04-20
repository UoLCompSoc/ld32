package uk.org.ulcompsoc.ld32.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import uk.org.ulcompsoc.ld32.LD32;
import uk.org.ulcompsoc.ld32.components.Player;
import uk.org.ulcompsoc.ld32.components.Tower;
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

import java.util.ArrayList;

public class GUIRenderSystem extends IteratingSystem {
	private final Batch batch;
	private final TextureManager textureManager;
	private OrthographicCamera camera;

	// different textures
	private TextureRegion frame = null;
	private TextureRegion redBallIcon = null;
	private TextureRegion blueBallIcon = null;
	private TextureRegion greenBallIcon = null;

	private TextureRegion zero = null;
	private TextureRegion one = null;
	private TextureRegion two = null;
	private TextureRegion three = null;
	private TextureRegion four = null;
	private TextureRegion five = null;
	private TextureRegion six = null;
	private TextureRegion seven = null;
	private TextureRegion eight = null;
	private TextureRegion nine = null;

	public static Entity selectedTowerEntity = null;

	// Default coordinates for drawing elements in predefined positions
	private final Vector3 DFLT_POSITION_OF_THE_FRAME = new Vector3(0.0f, 0.0f, 0.0f);
	private final Vector3 DFLT_POSITION_OF_THE_RED_BALL = new Vector3(120.0f, 150.0f, 0.0f);
	private final Vector3 DFLT_POSITION_OF_THE_GREEN_BALL = new Vector3(120.0f, 250.0f, 0.0f);
	private final Vector3 DFLT_POSITION_OF_THE_BLUE_BALL = new Vector3(120.0f, 350.f, 0.0f);

	private final Vector3 DFLT_RED_1_DIGIT_POSITION = new Vector3(120.0f, 95.0f, 0.0f);
	private final Vector3 DFLT_RED_2_DIGIT_POSITION = new Vector3(155.0f, 95.0f, 0.0f);

	private final Vector3 DFLT_GREEN_1_DIGIT_POSITION = new Vector3(120.0f, 195.0f, 0.0f);
	private final Vector3 DFLT_GREEN_2_DIGIT_POSITION = new Vector3(155.0f, 195.0f, 0.0f);

	private final Vector3 DFLT_BLUE_1_DIGIT_POSITION = new Vector3(120.0f, 295.0f, 0.0f);
	private final Vector3 DFLT_BLUE_2_DIGIT_POSITION = new Vector3(155.0f, 295.0f, 0.0f);
	private Vector3 temp;

	@SuppressWarnings("unchecked")
	public GUIRenderSystem(final Batch batch, final OrthographicCamera cam, int priority) {
		super(Family.all(Wallet.class).get(), priority);
		this.batch = batch;
		this.textureManager = LD32.textureManager;
		this.camera = cam;

		this.frame = new TextureRegion(textureManager.nameMap.get(TextureName.FRAME_1));
		this.redBallIcon = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_R));
		this.blueBallIcon = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_B));
		this.greenBallIcon = new TextureRegion(textureManager.nameMap.get(TextureName.BALL_G));

		this.zero = new TextureRegion(textureManager.nameMap.get(TextureName.ZERO));
		this.one = new TextureRegion(textureManager.nameMap.get(TextureName.ONE));
		this.two = new TextureRegion(textureManager.nameMap.get(TextureName.TWO));
		this.three = new TextureRegion(textureManager.nameMap.get(TextureName.THREE));
		this.four = new TextureRegion(textureManager.nameMap.get(TextureName.FOUR));
		this.five = new TextureRegion(textureManager.nameMap.get(TextureName.FIVE));
		this.six = new TextureRegion(textureManager.nameMap.get(TextureName.SIX));
		this.seven = new TextureRegion(textureManager.nameMap.get(TextureName.SEVEN));
		this.eight = new TextureRegion(textureManager.nameMap.get(TextureName.EIGHT));
		this.nine = new TextureRegion(textureManager.nameMap.get(TextureName.NINE));

	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Wallet wallet = Mappers.walletMapper.get(entity);

		// int screenWidth = Gdx.graphics.getWidth();
		// int screenHeight = Gdx.graphics.getHeight();

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
		// batch.draw(frame, temp.x, temp.y-h , screenWidth*0.1f, h);

		batch.draw(frame, temp.x, temp.y - h, w, h);

		temp = camera.unproject(DFLT_POSITION_OF_THE_RED_BALL.cpy());
		batch.draw(redBallIcon, temp.x, temp.y);

		temp = camera.unproject(DFLT_POSITION_OF_THE_BLUE_BALL.cpy());
		batch.draw(blueBallIcon, temp.x, temp.y);

		temp = camera.unproject(DFLT_POSITION_OF_THE_GREEN_BALL.cpy());
		batch.draw(greenBallIcon, temp.x, temp.y);

		this.handleACounter(redcount, batch, DFLT_RED_1_DIGIT_POSITION.cpy(), DFLT_RED_2_DIGIT_POSITION.cpy());
		this.handleACounter(bluecount, batch, DFLT_BLUE_1_DIGIT_POSITION.cpy(), DFLT_BLUE_2_DIGIT_POSITION.cpy());
		this.handleACounter(greencount, batch, DFLT_GREEN_1_DIGIT_POSITION.cpy(), DFLT_GREEN_2_DIGIT_POSITION.cpy());

		// batch.draw(textureManager., x, y, originX, originY, width, height,
		// scaleX, scaleY, rotation);

		if (selectedTowerEntity != null) {
			//Tower tower = Mappers.towerMapper.get(entity);
		}

		/**
		 * Score
		 */
		handleScore(batch);



		batch.end();
	}

	protected void handleACounter(int counter, Batch batch, final Vector3 vector1, final Vector3 vector2) {
		float scalefactor = 0.3f;
		float newWidth = zero.getRegionWidth() * scalefactor;
		float newHeight = zero.getRegionHeight() * scalefactor;

		String toBreakDown;
		if (counter > 9) {
			if (counter > 99) {
				toBreakDown = "99";
			} else {
				toBreakDown = counter + "";
			}
			temp = camera.unproject(vector1);
			batch.draw(this.getNumber(Integer.parseInt(toBreakDown.substring(0, 1))), temp.x, (temp.y - newWidth),
			        newWidth, newHeight);
			temp = camera.unproject(vector2);
			batch.draw(this.getNumber(Integer.parseInt(toBreakDown.substring(1))), temp.x, (temp.y - newWidth),
			        newWidth, newHeight);
		} else {
			temp = camera.unproject(vector2);
			batch.draw(this.getNumber(counter), temp.x, (temp.y - newWidth), newWidth, newHeight);
		}
	}

	protected void handleScore(Batch batch) {
		float scalefactor = 0.1f;
		float newWidth = zero.getRegionWidth() * scalefactor;
		float newHeight = zero.getRegionHeight() * scalefactor;

		int score = Player.score;
		//System.out.println(Player.score);

		ArrayList<Integer> characters = new ArrayList<>();

		while(score > 0) {
			characters.add(score % 10);
			score /= 10;
		}

		Vector3 start = camera.unproject(new Vector3(Gdx.graphics.getWidth() /2 - newWidth * characters.size(), Gdx.graphics.getHeight() / 10, 0.0f));
		float SPACER_MULTIPLIER = newWidth;
		float space = 0.0f;


		for(int i = characters.size() -1 ; i >= 0; i--) {
			batch.draw(this.getNumber(characters.get(i)), start.x + space, (start.y), newWidth, newHeight);
			space += SPACER_MULTIPLIER;
		}

	}

	private TextureRegion getNumber(int number) {
		switch (number) {
		case 0:
			return this.zero;
		case 1:
			return this.one;
		case 2:
			return this.two;
		case 3:
			return this.three;
		case 4:
			return this.four;
		case 5:
			return this.five;
		case 6:
			return this.six;
		case 7:
			return this.seven;
		case 8:
			return this.eight;
		case 9:
			return this.nine;
		default:
			return null;
		}
	}

}
