package com.wandurr.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class GameInputProcessor implements InputProcessor
{
	private CharacterController	player_controller;
	private GameScreen			game_screen;

	public GameInputProcessor(GameScreen game_screen, CharacterController character_controller)
	{
		this.game_screen = game_screen;
		this.player_controller = character_controller;
	}

	@Override
	public boolean keyDown(final int keycode)
	{
		if(keycode == Keys.LEFT)
		{
			player_controller.leftPressed();
		}
		if(keycode == Keys.RIGHT)
		{
			player_controller.rightPressed();
		}
		if(keycode == Keys.UP)
		{
			player_controller.upPressed();
		}
		if(keycode == Keys.DOWN)
		{
			player_controller.downPressed();
		}
		if(keycode == Keys.Z)
		{
			player_controller.attackPressed();
		}
		if(keycode == Keys.D)
		{
			game_screen.setDebugDraw(!game_screen.getDebugDraw());
		}
		return true;
	}

	@Override
	public boolean keyTyped(final char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(final int keycode)
	{
		if(keycode == Keys.LEFT)
		{
			player_controller.leftReleased();
		}
		if(keycode == Keys.RIGHT)
		{
			player_controller.rightReleased();
		}
		if(keycode == Keys.UP)
		{
			player_controller.upReleased();
		}
		if(keycode == Keys.DOWN)
		{
			player_controller.downReleased();
		}
		if(keycode == Keys.Z)
		{
			player_controller.attackReleased();
		}
		return true;
	}

	@Override
	public boolean mouseMoved(final int x, final int y)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(final int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(final int x, final int y, final int pointer,
								final int button)
	{
		if(GlobalConfig.is_touch_controls_enabled)
		{
			final int width = Gdx.graphics.getWidth();
			final int height = Gdx.graphics.getHeight();

			if((x < (width / 2)) && (y > (height / 2)))
			{
				player_controller.leftPressed();
			}
			else if((x > (width / 2)) && (y > (height / 2)))
			{
				player_controller.rightPressed();
			}
			else if(y < (height / 2))
			{
				player_controller.upPressed();
			}
			return true;
		}

		return false;
	}

	@Override
	public boolean touchDragged(final int x, final int y, final int pointer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(final int x, final int y, final int pointer,
							final int button)
	{
		if(GlobalConfig.is_touch_controls_enabled)
		{
			player_controller.rightReleased();
			player_controller.leftReleased();
			player_controller.upReleased();
		}

		return true;
	}
}
