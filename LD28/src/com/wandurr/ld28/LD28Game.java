package com.wandurr.ld28;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class LD28Game extends Game
{
	private BitmapFont	font;

	@Override
	public void create()
	{
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"),
								Gdx.files.internal("data/font.png"), false);
		setScreen(new GameScreen());
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void render()
	{
		super.render();
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
	}

	@Override
	public void pause()
	{
		super.pause();
	}

	@Override
	public void resume()
	{
		super.resume();
	}

	public BitmapFont getFont()
	{
		return font;
	}

	public void setFont(BitmapFont font)
	{
		this.font = font;
	}
}
