package uk.org.ulcompsoc.ld32.client;

import uk.org.ulcompsoc.ld32.LD32;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class HtmlLauncher extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig() {
		return new GwtApplicationConfiguration(1280, 720);
	}

	@Override
	public ApplicationListener getApplicationListener() {
		return new LD32();
	}
}