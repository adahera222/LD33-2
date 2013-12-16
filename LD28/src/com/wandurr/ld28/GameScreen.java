package com.wandurr.ld28;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen
{

	private static final float		ENEMY_SPAWN_TIME	= 10;
	private static final float		TEXT_HIGHLIGHT_TIME	= 2;
	private OrthographicCamera		camera;
	private SpriteBatch				batch;

	private CharacterController		player_controller;
	private Array<EnemyController>	enemy_controllers;

	private GameInputProcessor		input_processor;

	private Background				background;
	private Array<Character>		characters;
	private final ShapeRenderer		debugRenderer		= new ShapeRenderer();

	private Vector2					camera_acceleration	= new Vector2();
	private Vector2					camera_velocity		= new Vector2();

	private Rectangle				player_free_moving_field;
	private boolean					debug_draw;

	private BitmapFont				font;

	private OrthographicCamera		ui_camera;
	private float					enemy_spawn_timer;

	private String					warning_text		= "WARNING! TURN BACK!";
	private String					health_text			= "HEALTH: ";
	private String					controls_text		= "Arrow keys to move, Z to attack";
	private boolean					player_gained_health;
	private float					player_gained_health_timer;

	private Music					music;

	@Override
	public void show()
	{
		float viewport_width = Gdx.graphics.getWidth();
		float viewport_height = Gdx.graphics.getHeight();

		// camera = new OrthographicCamera(1, h / w);
		camera = new OrthographicCamera(viewport_width, viewport_height);
		ui_camera = new OrthographicCamera(viewport_width, viewport_height);
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

		background = new Background(5000, viewport_center.x - 2000, viewport_center.y - 2000, "data/background.png");

		Character player = new Character(this, 128, camera.position.x, camera.position.y, "data/player.png", 10);
		player_controller = new CharacterController(player);

		characters = new Array<Character>();
		characters.add(player);
		enemy_controllers = new Array<EnemyController>();

		input_processor = new GameInputProcessor(this, player_controller);
		Gdx.input.setInputProcessor(input_processor);

		LD28Game game = (LD28Game) Gdx.app.getApplicationListener();
		font = game.getFont();

		debug_draw = false;
		player_gained_health = false;
		player_gained_health_timer = TEXT_HIGHLIGHT_TIME;

		enemy_spawn_timer = ENEMY_SPAWN_TIME;
		spawnEnemy();

		music = Gdx.audio.newMusic(Gdx.files.internal("data/Untitled.mp3"));
		music.setLooping(true);
		music.play();

		game.setPlayerScore(0);
	}

	private void spawnEnemy()
	{
		Character player = characters.get(0);
		Random random = new Random();
		int maxX = (int) camera.viewportWidth;
		int minX = -maxX;
		int maxY = (int) camera.viewportHeight;
		int minY = -maxY;
		float x = (float) random.nextInt((maxX - minX) + 1) + minX;
		float y = (float) random.nextInt((maxY - minY) + 1) + minY;
		Character enemy = new Character(this, 100, x, y, "data/enemy.png", 5);
		EnemyController enemy_controller = new EnemyController(enemy, player);
		enemy_controllers.add(enemy_controller);
		characters.add(enemy);
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		for(EnemyController enemy_controller : enemy_controllers)
		{
			enemy_controller.update(delta);
		}

		player_controller.update(delta);
		for(Character character : characters)
		{
			character.update(delta);
		}

		checkWeaponHits();

		updateCameraPosition(delta);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		background.draw(batch);

		for(Character character : characters)
		{
			character.draw(batch);
		}

		batch.end();

		batch.setProjectionMatrix(ui_camera.combined);
		batch.begin();

		// batch to draw the warning text if necessary
		checkPlayerOutOfBounds(batch);

		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, health_text + characters.get(0).getHealth(), -camera.viewportWidth / 2.1f, camera.viewportHeight / 2f);

		if(player_gained_health)
		{
			player_gained_health_timer -= delta;
			float progress = player_gained_health_timer / TEXT_HIGHLIGHT_TIME;
			float rb = TEXT_HIGHLIGHT_TIME - progress;

			font.setColor(rb, 1.0f, rb, progress);
			float health_text_width = font.getBounds(health_text + characters.get(0).getHealth()).width;
			font.draw(batch, " +3", -camera.viewportWidth / 2.1f + health_text_width,
						camera.viewportHeight / 2f);

			if(player_gained_health_timer <= 0f)
			{
				player_gained_health = false;
				player_gained_health_timer = TEXT_HIGHLIGHT_TIME;
			}
		}

		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, controls_text, -camera.viewportWidth / 2.2f, -camera.viewportHeight / 2.5f);
		batch.end();

		if(enemy_spawn_timer <= 0.f)
		{
			spawnEnemy();
			enemy_spawn_timer = ENEMY_SPAWN_TIME;
			LD28Game game = (LD28Game) Gdx.app.getApplicationListener();
			if(game.getPlayerScore() > 10)
			{
				enemy_spawn_timer = ENEMY_SPAWN_TIME / 3f;
			}
			else if(game.getPlayerScore() > 5)
			{
				enemy_spawn_timer = ENEMY_SPAWN_TIME / 2f;
			}
			else if(game.getPlayerScore() > 3)
			{
				enemy_spawn_timer = ENEMY_SPAWN_TIME / 1.5f;
			}
		}

		enemy_spawn_timer -= delta;

		if(debug_draw)
		{
			debugRender();
		}
	}

	private void checkPlayerOutOfBounds(SpriteBatch ui_batch)
	{
		if(!background.getBounds().contains(characters.get(0).getBounds()))
		{
			Random rand = new Random();
			float rand_float = rand.nextFloat();
			if(rand_float > 0.4f && rand_float < 0.7f)
			{
				characters.get(0).takeDamage();
			}

			font.setColor(1.0f, 1.0f, 1.0f, rand_float < 0.5f ? 0.5f : 1.0f);
			font.draw(ui_batch, warning_text, -font.getBounds(warning_text).width / 2, 0f);
		}
	}

	private void checkWeaponHits()
	{
		Character player = characters.get(0);
		for(Character character : characters)
		{
			if(character.equals(player))
			{
				continue;
			}

			if(!character.isTakingDamage() && player.isAttacking() && player.getWeapon().getBounds().overlaps(character.getBounds()))
			{
				character.takeDamage();
			}

			if(!player.isTakingDamage() && character.isAttacking() && character.getWeapon().getBounds().overlaps(player.getBounds()))
			{
				player.takeDamage();
			}
		}
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
		music.stop();
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

	public void characterDied(Character character)
	{
		Character player = characters.get(0);
		if(character.equals(player))
		{
			LD28Game game = (LD28Game) Gdx.app.getApplicationListener();
			game.getSounds().gameover.play(1f);
			game.setScreen(new GameOverScreen());
		}
		else
		{
			for(EnemyController enemy_controller : enemy_controllers)
			{
				if(enemy_controller.getEnemy().equals(character))
				{
					enemy_controllers.removeValue(enemy_controller, true);
				}
			}
			characters.removeValue(character, true);

			player.setHealth(player.getHealth() + 5);
			player_gained_health = true;

			LD28Game game = (LD28Game) Gdx.app.getApplicationListener();
			game.getSounds().enemykilled.play(1.f);
			game.setPlayerScore(game.getPlayerScore() + 1);
		}
	}

	public boolean getDebugDraw()
	{
		return debug_draw;
	}

	public void setDebugDraw(boolean debug_draw)
	{
		this.debug_draw = debug_draw;
	}
}
