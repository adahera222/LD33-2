package com.wandurr.ld28.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.wandurr.ld28.GlobalConfig;
import com.wandurr.ld28.LD28Game;

public class GwtLauncher extends GwtApplication
{
	@Override
	public GwtApplicationConfiguration getConfig()
	{
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(GlobalConfig.screen_width, GlobalConfig.screen_height);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener()
	{
		return new LD28Game();
	}
}