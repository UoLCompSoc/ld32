package uk.org.ulcompsoc.ld32.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class TextureManager implements Disposable {
	public final List<Texture> textures = new ArrayList<Texture>();
	public final Map<TextureName, Texture> nameMap = new HashMap<TextureName, Texture>();
	public final Map<TextureName, TextureRegion[]> animationRegionMap = new HashMap<TextureName, TextureRegion[]>();
	public final Map<Character, TextureRegion> mapOfChars = new HashMap<Character, TextureRegion>();
	
	private Texture temp;

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
		TextureRegion[] temp = animationRegionMap.get(TextureName.FONT);
		for(int i = 0; i<25; i++){
			mapOfChars.put((char)((int)'A'+i), temp[i]);
		}
		for(int i=0,j=26; i<25; i++, j++){
			mapOfChars.put((char)((int)'a'+i), temp[j]);
		}
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
}
