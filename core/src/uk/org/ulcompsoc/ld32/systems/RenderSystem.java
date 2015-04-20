package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Killable;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Rotatable;
import uk.org.ulcompsoc.ld32.util.LDUtil;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import uk.org.ulcompsoc.ld32.util.TextureManager;
import uk.org.ulcompsoc.ld32.util.TextureName;

public class RenderSystem extends IteratingSystem {
	private final Batch batch;
	private final ShapeRenderer renderer;
	private final OrthographicCamera camera;
	private final Color POSITIVE_HEALTH_COLOR = Color.GREEN;
	private final Color NEGATIVE_HEALTH_COLOR = Color.RED;
	private final float HEALTH_HEIGHT_POSITION_MODIFIER = 4.0f;
	private TextureManager textureManager;

	@SuppressWarnings("unchecked")
	public RenderSystem(int priority, final Batch batch, final ShapeRenderer renderer, final OrthographicCamera camera) {
		super(Family.all(Position.class, Renderable.class).get(), priority);

		this.batch = batch;
		this.renderer = renderer;
		this.camera = camera;
		this.textureManager = new TextureManager();
		this.textureManager.load();
		initPongTextureRegions();
	}

	@Override
	public void update(float deltaTime) {
		batch.setProjectionMatrix(camera.combined);

		super.update(deltaTime);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final Position p = Mappers.positionMapper.get(entity);
		final Renderable r = Mappers.renderableMapper.get(entity);
		final Killable k = Mappers.killableMapper.get(entity);

		final float scalingFactor = calculateScalingFactor(r);

		switch (r.type) {
		case SHAPE: {
			renderer.setProjectionMatrix(camera.combined);
			renderer.begin(ShapeType.Filled);
			renderer.setColor(r.color);
			renderer.rect(p.getX() - (r.size / 2.0f), p.getY() - (r.size / 2.0f), r.size, r.size);
			renderer.end();

			if (k != null) {
				drawHealthBar(p.getX() - (r.size / 2.0f), p.getY() - (r.size / 2.0f), k, r);
			}

			break;
		}

		case STATIC_TEXTURE: {
			drawFrame(entity, p, r, k, scalingFactor, r.region);
			//It's a tower
			if(Mappers.towerMapper.has(entity)) {
				this.drawTowerPongProgress(entity, scalingFactor);
			}
			break;
		}

		case ANIMATED_TEXTURE: {
			r.animTime += deltaTime;
			drawFrame(entity, p, r, k, scalingFactor, r.animation.getKeyFrame(r.animTime));
			break;
		}

		case SPRITE: {
			drawFrame(entity, p, r, k, scalingFactor, r.sprite);


			break;
		}

		default:
			break;
		}




	}

	private void drawFrame(final Entity entity, final Position p, final Renderable r, final Killable k,
	        float scalingFactor, final TextureRegion region) {
		final Rotatable rot = Mappers.rotatableMapper.get(entity);

		final float rotationRad;
		final float rotationDeg;
		if (Mappers.paddleMapper.has(entity)) {
			rotationRad = p.getPhi();
		} else {
			rotationRad = (rot != null ? rot.rotation : 0.0f);
		}

		rotationDeg = (float) Math.toDegrees(rotationRad);

		final float xOffset = region.getRegionWidth() / 2.0f;
		final float yOffset = region.getRegionHeight() / 2.0f;

		final float x = p.getX() - xOffset;
		final float y = p.getY() - yOffset;

		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(region, x, y, region.getRegionWidth() / 2.0f, region.getRegionHeight() / 2.0f,
				region.getRegionWidth(), region.getRegionHeight(), scalingFactor, scalingFactor, rotationDeg);
		batch.end();

		if (k != null) {
			drawHealthBar(p.getX(), p.getY(), k, r);
		}


	}

	/**
	 * Draws a health bar above a position based on the radius of the entity.
	 * Drawing appropriate regions for remaining health.
	 *
	 * @param p
	 *            position of the entity
	 * @param k
	 *            for health information
	 * @param entityRenderable
	 *            the renderable of the entity whose healthbar this is
	 */
	private void drawHealthBar(float x, float y, Killable k, Renderable entityRenderable) {
		renderer.begin(ShapeType.Filled);

		x -= entityRenderable.getWidth() / 2.0f;
		y += entityRenderable.getHeight() / 1.9f;

		// Draw the positive health
		renderer.setColor(POSITIVE_HEALTH_COLOR);
		renderer.rect(x, y, entityRenderable.getWidth(), entityRenderable.getHeight() / 4.0f);

		// Draw the negative health
		renderer.setColor(NEGATIVE_HEALTH_COLOR);

		float remaningHealth = k.getHealth() / k.getOrigHealth();

		// If there's no difference, default to 0
		if (remaningHealth == 1 && k.getHealth() >= 0) {
			remaningHealth = 0;
		} else if (k.getHealth() <= 0) {
			remaningHealth = 1;
		}

		renderer.rect(x, y, entityRenderable.getWidth() * remaningHealth, entityRenderable.getHeight()
		        / HEALTH_HEIGHT_POSITION_MODIFIER);

		renderer.end();
	}

	private float calculateScalingFactor(final Renderable sc) {
		if (sc != null) {
			if (sc.scaleAnimTimeElapsed <= 0.0f) {
				sc.scale = sc.baseScale;
			} else {
				sc.scale = sc.baseScale
				        * (1.0f + LDUtil.normalCurve(0.0f, sc.totalScaleAnimTime, sc.scaleAnimTimeElapsed, true));
				// System.out.format("Scale = %f, tE = %f, tAT = %f\n",
				// sc.scale, sc.timeElapsed, sc.totalAnimTime);
			}

			return sc.scale;
		} else {
			return 1.0f;
		}
	}


	private TextureRegion pong0, pong1, pong2, pong3, pong4, pong5, pong6, pong7, pong8, pong9, pong10;

	private void initPongTextureRegions() {
		this.pong0 = new TextureRegion(textureManager.nameMap.get(TextureName.PONG_ZERO));
		this.pong1 = new TextureRegion(textureManager.nameMap.get(TextureName.PONG_ONE));
		this.pong2 = new TextureRegion(textureManager.nameMap.get(TextureName.PONG_TWO));
		this.pong3 = new TextureRegion(textureManager.nameMap.get(TextureName.PONG_THREE));
		this.pong4 = new TextureRegion(textureManager.nameMap.get(TextureName.PONG_FOUR));
		this.pong5 = new TextureRegion(textureManager.nameMap.get(TextureName.PONG_FIVE));
		this.pong6 = new TextureRegion(textureManager.nameMap.get(TextureName.PONG_SIX));
		this.pong7 = new TextureRegion(textureManager.nameMap.get(TextureName.PONG_SEVEN));
		this.pong8 = new TextureRegion(textureManager.nameMap.get(TextureName.PONG_EIGHT));
		this.pong9 = new TextureRegion(textureManager.nameMap.get(TextureName.PONG_NINE));
		this.pong10 = new TextureRegion(textureManager.nameMap.get(TextureName.PONG_TEN));
	}

	private void drawTowerPongProgress(Entity entity, float scalingFactor) {
		Tower tower = Mappers.towerMapper.get(entity);
		Position p = Mappers.positionMapper.get(entity);

		TextureRegion region = null;

		switch (tower.pongBonusCounter) {
			case 0: region = pong0; break;
			case 1: region = pong1; break;
			case 2: region = pong2; break;
			case 3: region = pong3; break;
			case 4: region = pong4; break;
			case 5: region = pong5; break;
			case 6: region = pong6; break;
			case 7: region = pong7; break;
			case 8: region = pong8; break;
			case 9: region = pong9; break;
			case 10: region = pong10; break;
		}


		final float xOffset = region.getRegionWidth() / 2.0f;
		final float yOffset = region.getRegionHeight() / 2.0f;

		final float x = p.getX() - xOffset;
		final float y = p.getY() - yOffset;

		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(region, x, y, region.getRegionWidth() / 2.0f, region.getRegionHeight() / 2.0f,
				region.getRegionWidth(), region.getRegionHeight(), scalingFactor, scalingFactor, 0.0f);
		batch.end();

	}
}
