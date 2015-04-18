package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Renderable extends Component {
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
		this.type = RenderableType.TEXTURE;

		this.region = region;
	}

	public static enum RenderableType {
		TEXTURE, SHAPE;
	}
}
