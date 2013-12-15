package com.wandurr.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Character
{
	public enum Direction
	{
		LEFT, RIGHT, UP, DOWN
	}

	public enum State
	{
		IDLE, WALKING, DYING
	}

	public static final float	DAMP			= 0.93f;
	public static final float	MAX_VEL			= 500f;

	private Vector2				acceleration	= new Vector2();
	private Vector2				velocity		= new Vector2();

	private State				state			= State.IDLE;
	private Direction			facing			= Direction.RIGHT;

	private Texture				texture;
	private Sprite				sprite;
	private TextureRegion		region;

	private Weapon				weapon;
	private boolean				attacking;

	public Character(float size, float posX, float posY, String texture_path)
	{
		texture = new Texture(Gdx.files.internal(texture_path));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

		sprite = new Sprite(region);
		sprite.setSize(size, size * region.getRegionWidth() / region.getRegionHeight());
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		setPosition(posX, posY);

		weapon = new Weapon(this, posX + sprite.getWidth() / 2, posY + sprite.getHeight() / 2, 32, 64, "data/sword.png");

		setFacing(Direction.RIGHT);
	}

	public void dispose()
	{
		texture.dispose();
	}

	public void draw(SpriteBatch batch)
	{
		// batch.draw(region, sprite.getX(), sprite.getY(), sprite.getBoundingRectangle().width, sprite.getBoundingRectangle().height);
		// sprite.setRotation(sprite.getRotation() + 0.1f);
		sprite.draw(batch);
		weapon.draw(batch);
	}

	public Vector2 getPosition()
	{
		return new Vector2(sprite.getX(), sprite.getY());
	}

	public void setPosition(float x, float y)
	{
		Vector2 diff = new Vector2(getPosition());

		sprite.setPosition(x, y);
		diff.sub(getPosition());
		if(weapon != null)
		{
			weapon.move(diff);
		}
	}

	public void setPosition(Vector2 position)
	{
		setPosition(position.x, position.y);
	}

	public Vector2 getAcceleration()
	{
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration)
	{
		this.acceleration = acceleration;
	}

	public Vector2 getVelocity()
	{
		return velocity;
	}

	public void setVelocity(Vector2 velocity)
	{
		this.velocity = velocity;
	}

	public Rectangle getBounds()
	{
		return sprite.getBoundingRectangle();
	}

	public void setBounds(Rectangle bounds)
	{
		sprite.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public Direction getFacing()
	{
		return facing;
	}

	public Weapon getWeapon()
	{
		return weapon;
	}

	public void setFacing(Direction facing)
	{
		this.facing = facing;
		if(facing.equals(Direction.LEFT))
		{
			weapon.setRotation(90);
		}
		else if(facing.equals(Direction.RIGHT))
		{
			weapon.setRotation(-90);
		}
		else if(facing.equals(Direction.UP))
		{
			weapon.setRotation(0);
		}
		else if(facing.equals(Direction.DOWN))
		{
			weapon.setRotation(180);
		}
	}

	public void update(float delta)
	{
		// System.out.println(position + " [" + sprite.getX() + ":" + sprite.getY() + "]");

		acceleration.scl(delta);
		velocity.add(acceleration);

		if(velocity.x > MAX_VEL)
		{
			velocity.x = MAX_VEL;
		}

		if(velocity.x < -MAX_VEL)
		{
			velocity.x = -MAX_VEL;
		}

		if(velocity.y > MAX_VEL)
		{
			velocity.y = MAX_VEL;
		}

		if(velocity.y < -MAX_VEL)
		{
			velocity.y = -MAX_VEL;
		}

		velocity.scl(delta);
		Vector2 position = getPosition();
		position.add(velocity);
		setPosition(position);

		velocity.scl(1 / delta);

		velocity.scl(DAMP);

		weapon.update(delta);
	}

	public void attack()
	{
		if(!attacking)
		{
			weapon.swing(facing);
			attacking = true;
		}
	}

	public void stopAttack()
	{
		attacking = false;
	}

}
