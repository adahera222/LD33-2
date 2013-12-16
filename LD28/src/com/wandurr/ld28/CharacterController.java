package com.wandurr.ld28;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.wandurr.ld28.Character.Direction;
import com.wandurr.ld28.Character.State;

public class CharacterController
{
	enum Keys
	{
		LEFT, RIGHT, UP, DOWN, ATTACK
	}

	public static final float				ACCELERATION	= 1000f;

	private final Character					player;
	private final HashMap<Keys, Boolean>	keys			= new HashMap<CharacterController.Keys, Boolean>();

	public CharacterController(Character player)
	{
		this.player = player;

		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
		keys.put(Keys.ATTACK, false);
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

	public void attackPressed()
	{
		keys.get(keys.put(Keys.ATTACK, true));
	}

	public void attackReleased()
	{
		keys.get(keys.put(Keys.ATTACK, false));
	}

	public void update(final float delta)
	{
		processInput();
	}

	private boolean processInput()
	{
		Vector2 acceleration = player.getAcceleration();
		if(keys.get(Keys.LEFT))
		{
			player.setFacing(Direction.LEFT);
			player.setState(State.WALKING);
			acceleration.x = -ACCELERATION;
		}
		if(keys.get(Keys.RIGHT))
		{
			player.setFacing(Direction.RIGHT);
			player.setState(State.WALKING);
			acceleration.x = ACCELERATION;
		}
		if(keys.get(Keys.UP))
		{
			player.setFacing(Direction.UP);
			player.setState(State.WALKING);
			acceleration.y = ACCELERATION;
		}
		if(keys.get(Keys.DOWN))
		{
			player.setFacing(Direction.DOWN);
			player.setState(State.WALKING);
			acceleration.y = -ACCELERATION;
		}
		if(keys.get(Keys.ATTACK))
		{
			player.attack();
			player.setState(State.ATTACKING);
			attackReleased();
		}
		player.setAcceleration(acceleration);

		return false;
	}
}
