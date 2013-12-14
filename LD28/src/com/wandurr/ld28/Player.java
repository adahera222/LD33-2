package com.wandurr.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player
{
	public enum Direction
	{
		LEFT, RIGHT, UP, DOWN
	}

	public enum State
	{
		IDLE, WALKING, DYING

	}

	public static final float	ACCELERATION	= 500f;
	public static final float	DAMP			= 0.95f;
	public static final float	MAX_VEL			= 500f;

	private Texture				texture;
	private Sprite				sprite;
	private Vector2				position		= new Vector2();
	private Vector2				acceleration	= new Vector2();
	private Vector2				velocity		= new Vector2();
	private Rectangle			bounds			= new Rectangle();
	private State				state			= State.IDLE;
	private Direction			facing			= Direction.RIGHT;
	private TextureRegion		region;

	public Player()
	{
		texture = new Texture(Gdx.files.internal("data/player.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

		bounds.setSize(128);
		setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		sprite = new Sprite(texture);
		sprite.setSize(bounds.width, bounds.height);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(position.x, position.y);
	}

	public void dispose()
	{
		texture.dispose();
	}

	public void draw(SpriteBatch batch)
	{
		batch.draw(region, position.x, position.y, bounds.width, bounds.height);
	}

	public Vector2 getPosition()
	{
		return position;
	}

	private void setPosition(float x, float y)
	{
		setPosition(new Vector2(x, y));
	}

	public void setPosition(Vector2 position)
	{
		this.position = position;
		this.bounds.setPosition(position);
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
		return bounds;
	}

	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
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

	public void setFacing(Direction facing)
	{
		this.facing = facing;
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
		position.add(velocity);
		setPosition(position);

		// sprite.setPosition(position.x, position.y);

		velocity.scl(1 / delta);

		velocity.scl(DAMP);
	}

}
