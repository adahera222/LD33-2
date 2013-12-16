package com.wandurr.ld28;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class LD28Game extends Game
{
	private BitmapFont	font;
	private Sounds		sounds;

	private int			player_score;

	@Override
	public void create()
	{
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"),
								Gdx.files.internal("data/font.png"), false);
		font.getRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		sounds = new Sounds();

		setScreen(new GameScreen());
	}

	public Sounds getSounds()
	{
		return sounds;
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

	public int getPlayerScore()
	{
		return player_score;
	}

	public void setPlayerScore(int player_score)
	{
		this.player_score = player_score;
	}
}
