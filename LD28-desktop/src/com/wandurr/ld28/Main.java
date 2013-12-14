package com.wandurr.ld28;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main
{
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "LD28";
		cfg.useGL20 = false;
		cfg.width = GlobalConfig.screen_width;
		cfg.height = GlobalConfig.screen_height;
		cfg.resizable = false;

		new LwjglApplication(new LD28Game(), cfg);
	}
}
