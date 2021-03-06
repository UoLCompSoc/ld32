package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Renderable extends Component implements Comparable<Renderable> {
	public final RenderableType type;

	public Sprite sprite = null;

	public Animation animation = null;
	public float animTime = 0.0f;

	public TextureRegion region = null;

	public float size = -1;
	public Color color = Color.WHITE.cpy();

	public float baseScale = 1.0f;
	public float maxScale = 1.6f;
	public float scale = 1.0f;

	public float totalScaleAnimTime = 0.25f;
	public float scaleAnimTimeElapsed = 0.0f;

	private int priority = 0;

	public Renderable(final Color color, final float size) {
		this.type = RenderableType.SHAPE;

		this.color = color;
		this.size = size;
	}

	public Renderable(final TextureRegion region) {
		this.type = RenderableType.STATIC_TEXTURE;

		this.region = region;
	}

	public Renderable(final Sprite sprite) {
		this.type = RenderableType.SPRITE;

		this.sprite = sprite;
	}

	public Renderable(final Animation animation) {
		this.type = RenderableType.ANIMATED_TEXTURE;

		this.animation = animation;
	}

	public static enum RenderableType {
		STATIC_TEXTURE, ANIMATED_TEXTURE, SPRITE, SHAPE;
	}

	public Renderable setScale(float scale) {
		this.baseScale = this.scale = scale;
		return this;
	}

	public Renderable setColor(final Color color) {
		if (color != null) {
			this.color = color.cpy();
		}

		return this;
	}

	public float getWidth() {
		switch (type) {
		case ANIMATED_TEXTURE:
			return animation.getKeyFrame(animTime).getRegionWidth() * scale;

		case SHAPE:
			return size * scale;

		case STATIC_TEXTURE:
			return region.getRegionWidth() * scale;

		case SPRITE:
			return sprite.getRegionWidth() * scale;

		default:
			break;
		}

		throw new GdxRuntimeException("Invalid type in Renderable.getWidth()");
	}

	public float getHeight() {
		switch (type) {
		case ANIMATED_TEXTURE:
			return animation.getKeyFrame(animTime).getRegionHeight() * scale;

		case SHAPE:
			return size * scale;

		case STATIC_TEXTURE:
			return region.getRegionHeight() * scale;

		case SPRITE:
			return sprite.getRegionHeight() * scale;

		default:
			break;
		}

		throw new GdxRuntimeException("Invalid type in Renderable.getHeight()");
	}

	public float getMaximumWidth() {
		return getWidth() * maxScale;
	}

	public float getMaximumHeight() {
		return getHeight() * maxScale;
	}

	public Renderable withPriority(int priority) {
		this.priority = priority;
		return this;
	}

	public int getPriority() {
		return priority;
	}

	@Override
	public int compareTo(Renderable o) {
		return (int) Math.signum(this.priority - o.priority);
	}
}
