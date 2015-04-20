package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.MouseListener;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MouseListenerSystem extends IteratingSystem {
	private final OrthographicCamera camera;

	@SuppressWarnings("unchecked")
	public MouseListenerSystem(int priority, final OrthographicCamera camera) {
		super(Family.all(MouseListener.class).get(), priority);
		this.camera = camera;
	}

	private Vector3 mouseTemp = new Vector3(0.0f, 0.0f, 0.0f);

	private float mouseX = 0.0f;
	private float mouseY = 0.0f;

	@Override
	public void update(float deltaTime) {
		// mouse coords are in screen coords, need to unproject into world
		// space.
		mouseTemp.x = Gdx.input.getX();
		mouseTemp.y = Gdx.input.getY();

		mouseTemp = camera.unproject(mouseTemp);
		mouseX = mouseTemp.x;
		mouseY = mouseTemp.y;
		super.update(deltaTime);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final MouseListener ml = Mappers.mouseListenerMapper.get(entity);

		if (ml.region.contains(mouseX, mouseY)) {
			ml.timeIn += deltaTime;

			if (!ml.isIn) {
				ml.handler.handleMouseEnter(entity, mouseX, mouseY);
				ml.isIn = true;
			} else {
				ml.handler.handleMouseIn(entity, mouseX, mouseY);
			}

			if (ml.clickCooldownRemaining <= 0.0f) {
				boolean clicked = false;

				if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
					ml.handler.handleClick(entity, MouseButtons.LEFT, mouseX, mouseY);
					clicked = true;
				} else if (Gdx.input.isButtonPressed(Buttons.MIDDLE)) {
					ml.handler.handleClick(entity, MouseButtons.MIDDLE, mouseX, mouseY);
					clicked = true;
				} else if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
					ml.handler.handleClick(entity, MouseButtons.RIGHT, mouseX, mouseY);
					clicked = true;
				}

				if (clicked) {
					ml.clickCooldownRemaining = ml.clickCooldown;
				}
			} else {
				ml.clickCooldownRemaining -= deltaTime;
			}
		} else {
			if (ml.isIn) {
				ml.handler.handleMouseLeave(entity, mouseX, mouseX);
				ml.isIn = false;
			} else {
				ml.timeIn = 0.0f;
			}
		}
	}
}
