package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Drop extends Component {
	public final Colour colour;

	public Drop(Colour colour) {
		this.colour = colour;
	}

	public static enum Colour {
		RED, GREEN, BLUE;
	}
}
