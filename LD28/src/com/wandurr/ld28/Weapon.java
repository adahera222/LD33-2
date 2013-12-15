package com.wandurr.ld28;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.wandurr.ld28.Character.Direction;

public class Weapon
{
	public final static float	SWING_DISTANCE	= 75f;
	private Texture				texture;
	private Sprite				sprite;
	private TextureRegion		region;
	private TweenManager		tween_manager;
	private WeaponTweenAccessor	weapon_accessor;
	private Character			character;

	public Weapon(Character character, float posX, float posY, float width, float height, String texture_path)
	{
		this.character = character;
		texture = new Texture(Gdx.files.internal(texture_path));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

		tween_manager = new TweenManager();
		weapon_accessor = new WeaponTweenAccessor();
		Tween.registerAccessor(Weapon.class, weapon_accessor);
		Tween.setWaypointsLimit(3);

		sprite = new Sprite(region);
		sprite.setSize(width, height);
		sprite.setOrigin(sprite.getWidth() / 2, 0);
		sprite.setPosition(posX - sprite.getWidth() / 2, posY);
	}

	public Rectangle getBounds()
	{
		return sprite.getBoundingRectangle();
	}

	public void draw(SpriteBatch batch)
	{
		// batch.draw(region, sprite.getX(), sprite.getY(), sprite.getBoundingRectangle().width, sprite.getBoundingRectangle().height);
		sprite.draw(batch);
	}

	public void update(float delta)
	{
		tween_manager.update(delta);
	}

	public void move(Vector2 diff)
	{
		sprite.setPosition(sprite.getX() - diff.x, sprite.getY() - diff.y);
	}

	public Vector2 getPosition()
	{
		return new Vector2(sprite.getX(), sprite.getY());
	}

	public void setPosition(float x, float y)
	{
		sprite.setPosition(x, y);
	}

	public void setRotation(float degrees)
	{
		sprite.setRotation(degrees);
	}

	public void swing(Direction direction)
	{
		if(direction.equals(Direction.LEFT))
		{
			Tween.to(this, WeaponTweenAccessor.POSITION_X, 0.2f)
					.targetRelative(0)
					.waypoint(-SWING_DISTANCE)
					.setCallback(new TweenCallback()
					{
						@Override
						public void onEvent(int type, BaseTween<?> source)
						{
							if(type == TweenCallback.COMPLETE)
							{
								character.stopAttack();
							}
						}
					})
					.start(tween_manager);
		}
		else if(direction.equals(Direction.RIGHT))
		{
			Tween.to(this, WeaponTweenAccessor.POSITION_X, 0.2f)
					.targetRelative(0)
					.waypoint(SWING_DISTANCE)
					.setCallback(new TweenCallback()
					{
						@Override
						public void onEvent(int type, BaseTween<?> source)
						{
							if(type == TweenCallback.COMPLETE)
							{
								character.stopAttack();
							}
						}
					})
					.start(tween_manager);
		}
		else if(direction.equals(Direction.UP))
		{
			Tween.to(this, WeaponTweenAccessor.POSITION_Y, 0.2f)
					.targetRelative(0)
					.waypoint(SWING_DISTANCE)
					.setCallback(new TweenCallback()
					{
						@Override
						public void onEvent(int type, BaseTween<?> source)
						{
							if(type == TweenCallback.COMPLETE)
							{
								character.stopAttack();
							}
						}
					})
					.start(tween_manager);
		}
		else if(direction.equals(Direction.DOWN))
		{
			Tween.to(this, WeaponTweenAccessor.POSITION_Y, 0.2f)
					.targetRelative(0)
					.waypoint(-SWING_DISTANCE)
					.setCallback(new TweenCallback()
					{
						@Override
						public void onEvent(int type, BaseTween<?> source)
						{
							if(type == TweenCallback.COMPLETE)
							{
								character.stopAttack();
							}
						}
					})
					.start(tween_manager);
		}

	}
}
