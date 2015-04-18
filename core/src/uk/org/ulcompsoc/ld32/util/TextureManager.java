package uk.org.ulcompsoc.ld32.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class TextureManager implements Disposable {
	public final List<Texture> textures = new ArrayList<Texture>();
	public final Map<TextureName, Texture> nameMap = new HashMap<TextureName, Texture>();

	public TextureManager() {
	}

	public void load() {
		for (final TextureName texName : TextureName.values()) {
			final FileHandle handle = Gdx.files.internal(texName.assetName);

			final Texture texture = new Texture(handle);

			textures.add(texture);
			nameMap.put(texName, texture);
		}
	}

	@Override
	public void dispose() {
		nameMap.clear();

		for (final Texture t : textures) {
			t.dispose();
		}

		textures.clear();
	}
}
