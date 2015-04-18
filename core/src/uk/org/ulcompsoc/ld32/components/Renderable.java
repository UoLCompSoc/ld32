package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class Renderable extends Component {
	public Color color;

	public Renderable() {
		this(Color.BLACK);
	}

	public Renderable(final Color color) {
		this.color = color;
	}
}
