package com.wandurr.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background
{
	private Texture			texture;
	private Sprite			sprite;
	private TextureRegion	region;

	public Background(float size, float posX, float posY, String texture_path)
	{
		texture = new Texture(Gdx.files.internal(texture_path));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

		sprite = new Sprite(texture);
		sprite.setPosition(posX, posY);
		sprite.setSize(size, size * region.getRegionWidth() / region.getRegionHeight());
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(posX, posY);
	}

	public void draw(SpriteBatch batch)
	{
		batch.draw(region, sprite.getX(), sprite.getY(), sprite.getBoundingRectangle().width, sprite.getBoundingRectangle().height);
	}

}
