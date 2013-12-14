package com.wandurr.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.wandurr.ld28.Player.Direction;

public class GameScreen implements Screen
{

	private OrthographicCamera	camera;
	private SpriteBatch			batch;
	private Player				player;
	private PlayerController	player_controller;
	private GameInputProcessor	input_processor;

	private final ShapeRenderer	debugRenderer	= new ShapeRenderer();

	@Override
	public void show()
	{
		float viewport_width = Gdx.graphics.getWidth();
		float viewport_height = Gdx.graphics.getHeight();

		// camera = new OrthographicCamera(1, h / w);
		camera = new OrthographicCamera(viewport_width, viewport_height);
		final Rectangle viewport_rect = new Rectangle(0, 0, viewport_width, viewport_height);
		final Vector2 viewport_center = new Vector2();
		viewport_rect.getCenter(viewport_center);

		// move the center a tad towards origin, otherwise the padding will all be on the other side of the screen
		viewport_center.x -= 0.5f;
		viewport_center.y -= 0.5f;

		camera.position.set(new Vector3(viewport_center.x, viewport_center.y, 0));
		camera.update();

		batch = new SpriteBatch();

		player = new Player();
		player_controller = new PlayerController(player);

		input_processor = new GameInputProcessor(player_controller);
		Gdx.input.setInputProcessor(input_processor);
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		player_controller.update(delta);
		player.update(delta);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.draw(batch);
		batch.end();

		debugRender();
	}

	private void debugRender()
	{
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);

		// draw player
		Color playerColor = new Color(0, 1, 0, 1);
		debugRenderer.setColor(playerColor);
		final Rectangle rect = player.getBounds();
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);

		// draw player facing
		final float startX = player.getPosition().x + (rect.width / 2);
		final float startY = player.getPosition().y + (rect.height / 2);

		float endX = startX;
		float endY = startY;
		if(player.getFacing().equals(Direction.LEFT) || player.getFacing().equals(Direction.RIGHT))
		{
			endY = startY;
			endX = player.getFacing().equals(Direction.LEFT) ? startX - (rect.width / 2) : startX + (rect.width / 2);
		}
		else if(player.getFacing().equals(Direction.UP) || player.getFacing().equals(Direction.DOWN))
		{
			endX = startX;
			endY = player.getFacing().equals(Direction.DOWN) ? startY - (rect.height / 2) : startY + (rect.height / 2);
		}

		debugRenderer.line(startX, startY, endX, endY);

		debugRenderer.end();

	}

	@Override
	public void resize(int width, int height)
	{

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

}
