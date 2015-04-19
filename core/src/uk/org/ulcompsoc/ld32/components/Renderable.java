package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Renderable extends Component {
	public Animation animation = null;
	public float animTime = 0.0f;

	public TextureRegion region = null;

	public final RenderableType type;

	public float size = -1;
	public Color color = null;

	public Renderable(final Color color, final float size) {
		this.type = RenderableType.SHAPE;

		this.color = color;
		this.size = size;
	}

	public Renderable(final TextureRegion region) {
		this.type = RenderableType.STATIC_TEXTURE;

		this.region = region;
	}

	public Renderable(final Animation animation) {
		this.type = RenderableType.ANIMATED_TEXTURE;

		this.animation = animation;
	}

	public static enum RenderableType {
		STATIC_TEXTURE, ANIMATED_TEXTURE, SHAPE;
	}
}
