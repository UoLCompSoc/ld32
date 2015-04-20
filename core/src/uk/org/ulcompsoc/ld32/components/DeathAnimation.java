package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;

public class DeathAnimation extends Component {
	public final Animation animation;

	public DeathAnimation(final Animation animation) {
		this.animation = animation;
	}
}
