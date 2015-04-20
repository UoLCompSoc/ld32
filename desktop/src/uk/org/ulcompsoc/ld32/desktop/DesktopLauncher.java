package uk.org.ulcompsoc.ld32.desktop;

import uk.org.ulcompsoc.ld32.LD32;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.resizable = false;
		config.width = 1280;
		config.height = 720;
		config.title = "PONTRON";
		config.addIcon("app_icons/16.png", FileType.Internal);
		config.addIcon("app_icons/32.png", FileType.Internal);
		config.addIcon("app_icons/128.png", FileType.Internal);

		new LwjglApplication(new LD32(), config);
	}
}
