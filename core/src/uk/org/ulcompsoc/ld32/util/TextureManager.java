package uk.org.ulcompsoc.ld32.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.org.ulcompsoc.ld32.components.EntityLink;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class TextureManager implements Disposable {
	public final List<Texture> textures = new ArrayList<Texture>();
	public final Map<TextureName, Texture> nameMap = new HashMap<TextureName, Texture>();
	public final Map<TextureName, TextureRegion[]> animationRegionMap = new HashMap<TextureName, TextureRegion[]>();
	public final Map<Character, TextureRegion> mapOfChars = new HashMap<Character, TextureRegion>();

	public TextureManager() {
	}

	public void load() {
		for (final TextureName texName : TextureName.values()) {
			final FileHandle handle = Gdx.files.internal(texName.assetName);

			final Texture texture = new Texture(handle);

			textures.add(texture);
			nameMap.put(texName, texture);

			if (texName.isAnimated) {
				animationRegionMap.put(texName,
				        TextureRegion.split(texture, texName.frameWidth, texName.frameHeight)[0]);
			}
		}

		fillCharTextures();
	}

	private void fillCharTextures() {
		final TextureRegion[] temp = animationRegionMap.get(TextureName.FONT);

		for (int i = 0; i < 25; ++i) {
			mapOfChars.put((char) ('A' + i), temp[i]);
		}

		for (int i = 0, j = 26; i < 25; ++i, ++j) {
			mapOfChars.put((char) ('a' + i), temp[j]);
		}

		for (int i = 0, j = 26 * 2; i < 9; ++i, ++j) {
			mapOfChars.put((char) ('1' + i), temp[j]);
		}

		final int zeroPos = 26 * 2 + 9;

		mapOfChars.put('0', temp[zeroPos + 0]);
		mapOfChars.put('-', temp[zeroPos + 1]);
		mapOfChars.put('+', temp[zeroPos + 2]);
		mapOfChars.put('(', temp[zeroPos + 3]);
		mapOfChars.put(')', temp[zeroPos + 4]);
		mapOfChars.put(' ', temp[0]); // will be drawn with 0 alpha
	}

	public Entity makeWord(Engine engine, String str, int x, int y) {
		return makeWord(engine, str, Color.BLACK, x, y);
	}

	public Entity makeWord(Engine engine, String str, Color color, int x, int y) {
		Entity head = null;
		final Entity[] rest = new Entity[str.length() - 1];

		for (int i = 0; i < str.length(); ++i) {
			final char c = str.charAt(i);
			if (!mapOfChars.containsKey(c)) {
				throw new IllegalArgumentException("Can't use character \"" + c + "\" in TextureManager.makeWord");
			}

			final Entity e = new Entity();
			final TextureRegion region = new TextureRegion(mapOfChars.get(c));
			final Renderable r = new Renderable(region).setScale(0.25f);

			final Color col = color.cpy();
			if (c == ' ') {
				col.a = 0.0f;
			}
			r.setColor(col);

			e.add(r);
			e.add(Position.fromEuclidean(x + i * r.getWidth(), y));

			if (i == 0) {
				head = e;
			} else {
				rest[i - 1] = e;
			}

			engine.addEntity(e);
		}

		return head.add(new EntityLink(rest));
	}

	@Override
	public void dispose() {
		animationRegionMap.clear();
		nameMap.clear();

		for (final Texture t : textures) {
			t.dispose();
		}

		textures.clear();
	}

	/*
	 * public Entity[] generateWord(String characters, float x, float y){ float
	 * xValue = x; float yValue = y; Entity[] toReturn = new
	 * Entity[characters.length()]; while(!characters.equals("")){ char tempChar
	 * = characters.charAt(0); characters = characters.substring(1); Entity temp
	 * = new Entity(); temp.add(Position.fromEuclidean(xValue, yValue));
	 * temp.add(component) } }
	 */
}
