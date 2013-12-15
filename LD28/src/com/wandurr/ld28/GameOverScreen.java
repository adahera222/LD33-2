package com.wandurr.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen implements Screen, InputProcessor
{
	private OrthographicCamera	camera;
	private BitmapFont			font;
	private String				game_over_text	= "GAME OVER";
	private String				retry_text		= "press 'R' to retry";

	@Override
	public void show()
	{
		float viewport_width = Gdx.graphics.getWidth();
		float viewport_height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(viewport_width, viewport_height);

		LD28Game game = (LD28Game) Gdx.app.getApplicationListener();
		font = game.getFont();

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		SpriteBatch batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, game_over_text, -font.getBounds(game_over_text).width / 2, 0f);
		font.draw(batch, retry_text, -font.getBounds(retry_text).width / 2, -font.getLineHeight());
		batch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode)
	{
		if(keycode == Keys.R)
		{
			LD28Game game = (LD28Game) Gdx.app.getApplicationListener();
			game.setScreen(new GameScreen());
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
