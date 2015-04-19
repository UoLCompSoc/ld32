package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Killable;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Rotatable;
import uk.org.ulcompsoc.ld32.components.Scalable;
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

public class RenderSystem extends IteratingSystem {
	private final Batch batch;
	private final ShapeRenderer renderer;
	private final OrthographicCamera camera;
	private final Color POSITIVE_HEALTH_COLOR = Color.GREEN;
	private final Color NEGATIVE_HEALTH_COLOR = Color.RED;
	private final float HEALTH_HEIGHT_POSITION_MODIFIER = 4.0f;

	@SuppressWarnings("unchecked")
	public RenderSystem(int priority, final Batch batch, final ShapeRenderer renderer, final OrthographicCamera camera) {
		super(Family.all(Position.class, Renderable.class).get(), priority);

		this.batch = batch;
		this.renderer = renderer;
		this.camera = camera;
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

		final Scalable sc = Mappers.scalableMapper.get(entity);

		final float scalingFactor = calculateScalingFactor(sc);

		switch (r.type) {
		case SHAPE: {
			renderer.setProjectionMatrix(camera.combined);
			renderer.begin(ShapeType.Filled);
			renderer.setColor(r.color);
			renderer.rect(p.getX() - (r.size / 2.0f), p.getY() - (r.size / 2.0f), r.size, r.size);
			renderer.end();

			if (k != null) {
				drawHealthBar(p.getX() - (r.size / 2.0f), p.getY() - (r.size / 2.0f), k, r.size);
			}

			break;
		}

		case STATIC_TEXTURE: {
			drawFrame(entity, p, r, k, scalingFactor, r.region);
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

		final float xOffset = scalingFactor * region.getRegionWidth() / 2.0f;
		final float yOffset = scalingFactor * region.getRegionHeight() / 2.0f;

		final float x = p.getX() - xOffset;
		final float y = p.getY() - yOffset;

		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(region, x, y, 0.0f, 0.0f, region.getRegionWidth(), region.getRegionHeight(), scalingFactor,
		        scalingFactor, 0.0f);
		batch.end();

		if (k != null) {
			drawHealthBar(x, y, k, region.getRegionHeight() * scalingFactor);
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
	 * @param radiusOfEntity
	 *            how big is the entity?
	 */
	private void drawHealthBar(float x, float y, Killable k, float radiusOfEntity) {
		renderer.begin(ShapeType.Filled);

		// Draw the positive health
		renderer.setColor(POSITIVE_HEALTH_COLOR);
		renderer.rect(x, y + radiusOfEntity, radiusOfEntity, radiusOfEntity / 4.0f);

		// Draw the negative health
		renderer.setColor(NEGATIVE_HEALTH_COLOR);

		float remaningHealth = k.getHealth() / k.getOrigHealth();

		// If there's no difference, default to 0
		if (remaningHealth == 1 && k.getHealth() >= 0) {
			remaningHealth = 0;
		} else if(k.getHealth() <= 0) {
			remaningHealth = 1;
		}

		renderer.rect(x, y + radiusOfEntity, radiusOfEntity * remaningHealth, radiusOfEntity
		        / HEALTH_HEIGHT_POSITION_MODIFIER);

		renderer.end();
	}

	private float calculateScalingFactor(final Scalable sc) {
		if (sc != null) {
			if (sc.timeElapsed <= 0.0f) {
				sc.scale = sc.baseScale;
			} else {
				sc.scale = sc.baseScale * (1.0f + LDUtil.normalCurve(0.0f, sc.totalAnimTime, sc.timeElapsed, true));
				// System.out.format("Scale = %f, tE = %f, tAT = %f\n",
				// sc.scale, sc.timeElapsed, sc.totalAnimTime);
			}

			return sc.scale;
		} else {
			return 1.0f;
		}
	}
}
