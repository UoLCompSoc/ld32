package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.PaddleInputListener;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.util.LDUtil;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;

public class PaddleInputSystem extends IteratingSystem {

	@SuppressWarnings("unchecked")
	public PaddleInputSystem(int priority) {
		super(Family.all(Position.class, PaddleInputListener.class).get(), priority);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final Position p = Mappers.positionMapper.get(entity);
		final PaddleInputListener pil = Mappers.paddleInputListener.get(entity);
		boolean moving = false;
		float movingDirection = 0;

		out: for (int i = 0; i < pil.leftKeys.length; ++i) {
			final int lKey = pil.leftKeys[i];
			if (Gdx.input.isKeyPressed(lKey)) {
				moving = true;
				movingDirection = 1.0f;
				break out;
			}
		}

		if (!moving) {
			out: for (int i = 0; i < pil.rightKeys.length; ++i) {
				final int rKey = pil.rightKeys[i];
				if (Gdx.input.isKeyPressed(rKey)) {
					moving = true;
					movingDirection = -1.0f;
					break out;
				}
			}
		}

		if (moving) {
			pil.pressTime += deltaTime;
			pil.setVelocity(LDUtil.smoothStep(0.0f, PaddleInputListener.MAX_SPEED_TIME, pil.pressTime)
			        * PaddleInputListener.MAX_VELOCITY);
			p.movePolarAngle(movingDirection * pil.velocity);
		} else {
			pil.pressTime = pil.velocity = 0.0f;
		}

		/**
		 * Firing mechanism
		 */
		out: for (int i = 0; i < pil.fireKeys.length; ++i) {
			final int rKey = pil.fireKeys[i];
			if (Gdx.input.isKeyPressed(rKey)) {
				break out;
			}
		}



	}
}
