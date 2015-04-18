package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Killable;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Rotatable;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;

public class RenderSystem extends IteratingSystem {
	private final Batch batch;
	private final ShapeRenderer renderer;
	private final OrthographicCamera camera;

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

		batch.begin();

		super.update(deltaTime);

		batch.end();
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

			if(k != null) {
				drawHealthBar(p, r, k, r.size);
			}

			break;
		}

		case TEXTURE: {
			final Rotatable rot = Mappers.rotatableMapper.get(entity);

			if (rot != null) {
				// TODO: impl rotation
				batch.draw(r.region, p.getX(), p.getY());
			} else {
				batch.draw(r.region, p.getX(), p.getY());
			}

			if(k != null) {
				drawHealthBar(p, r, k, r.region.getRegionWidth());
			}


			break;
		}

		default:
			break;
		}



	}

	private final Color POSITIVE_HEALTH_COLOR = Color.GREEN;
	private final Color NEGATIVE_HEALTH_COLOR = Color.RED;

	private void drawHealthBar(Position p, Renderable r, Killable k, float radius) {
		renderer.begin(ShapeType.Filled);
		renderer.setColor(POSITIVE_HEALTH_COLOR);
		renderer.rect(p.getX() - (radius / 2.0f), p.getY() + radius, radius, radius / 4.0f);

		renderer.setColor(NEGATIVE_HEALTH_COLOR);
		float remaningHealth = k.health / k.originalHealth;
		if(remaningHealth == 1) remaningHealth = 0;
		renderer.rect(p.getX() - (radius / 2.0f), p.getY() + radius, radius*remaningHealth, radius / 4.0f);

		renderer.end();
	}
}
