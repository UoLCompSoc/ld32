package uk.org.ulcompsoc.ld32.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.utils.ImmutableArray;
import uk.org.ulcompsoc.ld32.components.PaddleInputListener;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Atom;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.util.LDUtil;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;

public class PaddleInputSystem extends IteratingSystem {

	public Engine engine;

	@SuppressWarnings("unchecked")
	public PaddleInputSystem(int priority) {
		super(Family.all(Position.class, PaddleInputListener.class).get(), priority);
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.engine = engine;
	}

	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		this.engine = null;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final Position p = Mappers.positionMapper.get(entity);
		final PaddleInputListener pil = Mappers.paddleInputListener.get(entity);
		boolean moving = false;
		boolean firing = false;
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
				firing = true;
				break out;
			}
		}

		if(firing) {
			//Get the atoms
			ImmutableArray<Entity> atoms = engine.getEntitiesFor(Family.all(Atom.class).get());

			//Loop through them
			out: for(Entity e : atoms) {
				Atom atom = Mappers.atomMapper.get(e);

				//If we found an atom at the paddle
				if(atom.atPaddle) {
					//Request it to be fired
					atom.primed = true;
					//Don't continue (only fire one)
					break out;
				}

			}

		}







	}
}
