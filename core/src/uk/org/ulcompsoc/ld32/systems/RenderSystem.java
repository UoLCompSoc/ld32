package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Killable;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Rotatable;
import uk.org.ulcompsoc.ld32.components.Scalable;
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
		super(Family.all(Position.class, Renderable.class).get());

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

		switch (r.type) {
		case SHAPE: {
			renderer.setProjectionMatrix(camera.combined);
			renderer.begin(ShapeType.Filled);
			renderer.setColor(r.color);
			renderer.rect(p.getX() - (r.size / 2.0f), p.getY() - (r.size / 2.0f), r.size, r.size);
			renderer.end();

			if (k != null) {
				drawHealthBar(p, k, r.size);
			}

			break;
		}

		case STATIC_TEXTURE: {
			drawFrame(entity, p, r, k, r.region);
			break;
		}

		case ANIMATED_TEXTURE: {
			r.animTime += deltaTime;
			drawFrame(entity, p, r, k, r.animation.getKeyFrame(r.animTime));
			break;
		}

		default:
			break;
		}
	}

	private void drawFrame(final Entity entity, final Position p, final Renderable r, final Killable k,
	        final TextureRegion region) {
		final Rotatable rot = Mappers.rotatableMapper.get(entity);
		final Scalable sc = Mappers.scalableMapper.get(entity);
		final float scalingFactor = (sc != null ? sc.scale : 1.0f);
		final float rotation;

		if (Mappers.paddleMapper.has(entity)) {
			rotation = (float) Math.toDegrees(p.getPhi());
		} else {
			rotation = (rot != null ? rot.rotation : 0.0f);
		}

		final float xOffset = scalingFactor * region.getRegionWidth() / 2.0f;
		final float yOffset = scalingFactor * region.getRegionHeight() / 2.0f;

		batch.begin();
		batch.draw(region, p.getX() - xOffset, p.getY() - yOffset, 0.0f, 0.0f, region.getRegionWidth(),
		        region.getRegionHeight(), scalingFactor, scalingFactor, rotation);
		batch.end();

		if (k != null) {
			drawHealthBar(p, k, region.getRegionHeight() * scalingFactor);
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
	 * @param radius
	 *            how big is the entity?
	 */
	private void drawHealthBar(Position p, Killable k, float radius) {
		renderer.begin(ShapeType.Filled);

		// Draw the positive health
		renderer.setColor(POSITIVE_HEALTH_COLOR);
		renderer.rect(p.getX(), p.getY() + radius, radius, radius / 4.0f);

		// Draw the negative health
		renderer.setColor(NEGATIVE_HEALTH_COLOR);

		float remaningHealth = k.getHealth() / k.getOrigHealth();

		// If there's no difference, default to 0
		if (remaningHealth == 1) {
			remaningHealth = 0;
		}

		renderer.rect(p.getX(), p.getY() + radius, radius * remaningHealth, radius / HEALTH_HEIGHT_POSITION_MODIFIER);

		renderer.end();
	}
}
