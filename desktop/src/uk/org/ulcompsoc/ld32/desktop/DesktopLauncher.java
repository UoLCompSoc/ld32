package uk.org.ulcompsoc.ld32.desktop;

import uk.org.ulcompsoc.ld32.LD32;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 1280;
		config.height = 720;

		new LwjglApplication(new LD32(), config);
	}
}
