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
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen
{

	private OrthographicCamera	camera;
	private SpriteBatch			batch;

	private CharacterController	player_controller;
	private EnemyController		enemy_controller;

	private GameInputProcessor	input_processor;

	private Background			background;
	private Array<Character>	characters;
	private final ShapeRenderer	debugRenderer		= new ShapeRenderer();

	private Vector2				camera_acceleration	= new Vector2();
	private Vector2				camera_velocity		= new Vector2();

	private Rectangle			player_free_moving_field;

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

		player_free_moving_field = new Rectangle(150, 100, 500, 400);

		batch = new SpriteBatch();

		background = new Background(1024, viewport_center.x - 500, viewport_center.y - 500, "data/background.png");

		Character player = new Character(128, camera.position.x, camera.position.y, "data/player.png");
		player_controller = new CharacterController(player);

		Character enemy = new Character(100, camera.position.x, camera.position.y - player.getBounds().height * 2, "data/enemy.png");
		enemy_controller = new EnemyController(enemy, player);

		characters = new Array<Character>();
		characters.add(player);
		characters.add(enemy);

		input_processor = new GameInputProcessor(player_controller);
		Gdx.input.setInputProcessor(input_processor);
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		enemy_controller.update(delta);
		player_controller.update(delta);
		for(Character character : characters)
		{
			character.update(delta);
		}

		updateCameraPosition(delta);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		background.draw(batch);
		for(Character character : characters)
		{
			character.draw(batch);
		}
		batch.end();

		debugRender();
	}

	/**
	 * move camera if player gets near the screen edges
	 * 
	 * @param delta
	 */
	private void updateCameraPosition(float delta)
	{
		Character player = characters.get(0);
		Rectangle player_bounds = new Rectangle(player.getBounds());
		Vector3 player_pos_projected = new Vector3(player_bounds.x, player_bounds.y, 0);
		camera.project(player_pos_projected);

		player_bounds.setPosition(new Vector2(player_pos_projected.x, player_pos_projected.y));
		if(!player_free_moving_field.contains(player_bounds))
		{
			camera_acceleration = new Vector2(player.getAcceleration());
			camera_velocity = new Vector2(player.getVelocity());
		}

		camera_acceleration.scl(delta);
		camera_velocity.add(camera_acceleration);

		if(camera_velocity.x > Character.MAX_VEL)
		{
			camera_velocity.x = Character.MAX_VEL;
		}

		if(camera_velocity.x < -Character.MAX_VEL)
		{
			camera_velocity.x = -Character.MAX_VEL;
		}

		if(camera_velocity.y > Character.MAX_VEL)
		{
			camera_velocity.y = Character.MAX_VEL;
		}

		if(camera_velocity.y < -Character.MAX_VEL)
		{
			camera_velocity.y = -Character.MAX_VEL;
		}

		camera_velocity.scl(1.08f);
		camera_velocity.scl(delta);
		Vector2 position = new Vector2(camera.position.x, camera.position.y);
		position.add(camera_velocity);
		camera.position.set(position.x, position.y, 0);
		camera.update();

		camera_velocity.scl(1 / delta);
		camera_velocity.scl(Character.DAMP * 0.5f);
	}

	private void debugRender()
	{
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);

		// draw characters
		for(Character character : characters)
		{
			Color playerColor = new Color(0, 1, 0, 1);
			debugRenderer.setColor(playerColor);
			final Rectangle rect = character.getBounds();
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);

			// draw player weapon bounds
			final Rectangle weapon_rect = character.getWeapon().getBounds();
			debugRenderer.rect(weapon_rect.x, weapon_rect.y, weapon_rect.width, weapon_rect.height);
			// final float startX = character.getPosition().x + (rect.width / 2);
			// final float startY = character.getPosition().y + (rect.height / 2);
			//
			// float endX = startX;
			// float endY = startY;
			// if(character.getFacing().equals(Direction.LEFT) || character.getFacing().equals(Direction.RIGHT))
			// {
			// endY = startY;
			// endX = character.getFacing().equals(Direction.LEFT) ? startX - (rect.width / 2) : startX + (rect.width / 2);
			// }
			// else if(character.getFacing().equals(Direction.UP) || character.getFacing().equals(Direction.DOWN))
			// {
			// endX = startX;
			// endY = character.getFacing().equals(Direction.DOWN) ? startY - (rect.height / 2) : startY + (rect.height / 2);
			// }
			//
			// debugRenderer.line(startX, startY, endX, endY);
		}

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
