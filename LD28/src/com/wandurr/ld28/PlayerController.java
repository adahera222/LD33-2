package com.wandurr.ld28;

import java.util.HashMap;

import com.wandurr.ld28.Player.Direction;

public class PlayerController
{
	enum Keys
	{
		LEFT, RIGHT, UP, DOWN
	}

	private final Player					player;
	private final HashMap<Keys, Boolean>	keys	= new HashMap<PlayerController.Keys, Boolean>();

	public PlayerController(Player player)
	{
		this.player = player;

		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	}

	public void init()
	{
	}

	public void leftPressed()
	{
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void leftReleased()
	{
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightPressed()
	{
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void rightReleased()
	{
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void upPressed()
	{
		keys.get(keys.put(Keys.UP, true));
	}

	public void upReleased()
	{
		keys.get(keys.put(Keys.UP, false));
	}

	public void downPressed()
	{
		keys.get(keys.put(Keys.DOWN, true));
	}

	public void downReleased()
	{
		keys.get(keys.put(Keys.DOWN, false));
	}

	public void update(final float delta)
	{
		processInput();
	}

	private boolean processInput()
	{
		if(keys.get(Keys.LEFT))
		{
			player.setFacing(Direction.LEFT);
			player.getAcceleration().x = -Player.ACCELERATION;
		}
		else if(keys.get(Keys.RIGHT))
		{
			player.setFacing(Direction.RIGHT);
			player.getAcceleration().x = Player.ACCELERATION;
		}
		else if(keys.get(Keys.UP))
		{
			player.setFacing(Direction.UP);
			player.getAcceleration().y = Player.ACCELERATION;
		}
		else if(keys.get(Keys.DOWN))
		{
			player.setFacing(Direction.DOWN);
			player.getAcceleration().y = -Player.ACCELERATION;
		}

		return false;
	}
}
