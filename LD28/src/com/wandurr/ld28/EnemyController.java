package com.wandurr.ld28;

import com.badlogic.gdx.math.Vector2;
import com.wandurr.ld28.Character.Direction;

public class EnemyController
{
	public static final float	ACCELERATION	= 500f;

	private Character			enemy;
	private Character			player;

	public EnemyController(Character enemy, Character player)
	{
		this.enemy = enemy;
		this.player = player;
	}

	public void update(float delta)
	{
		Vector2 acceleration = enemy.getAcceleration();
		Vector2 player_pos = player.getPosition();
		Vector2 enemy_pos = enemy.getPosition();

		if(player_pos.x - 20 > enemy_pos.x)
		{
			acceleration.x += ACCELERATION;
			enemy.setFacing(Direction.RIGHT);
		}
		else if(player_pos.x + 148 < enemy_pos.x)
		{
			acceleration.x += -ACCELERATION;
			enemy.setFacing(Direction.LEFT);
		}

		if(player_pos.y - 10 > enemy_pos.y)
		{
			acceleration.y += ACCELERATION;
			enemy.setFacing(Direction.UP);
		}
		else if(player_pos.y + 138 < enemy_pos.y)
		{
			acceleration.y += -ACCELERATION;
			enemy.setFacing(Direction.DOWN);
		}

		if(Math.abs(player_pos.x - enemy_pos.x) < Weapon.SWING_DISTANCE * 2.5f
			&& Math.abs(player_pos.y - enemy_pos.y) < Weapon.SWING_DISTANCE * 2.5f)
		{
			enemy.attack();
		}
		enemy.setAcceleration(acceleration);
	}

	public Character getEnemy()
	{
		return enemy;
	}
}
