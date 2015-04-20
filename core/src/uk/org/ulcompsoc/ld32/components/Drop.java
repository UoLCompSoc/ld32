package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class Drop extends Component {
	public final Colour colour;

	public Drop(Colour colour) {
		this.colour = colour;
	}

	public static enum Colour {
		RED(Color.RED.cpy()), GREEN(Color.GREEN.cpy()), BLUE(Color.BLUE.cpy());

		public final com.badlogic.gdx.graphics.Color renderColor;

		private Colour(com.badlogic.gdx.graphics.Color color) {
			this.renderColor = color;
		}
	}
}
