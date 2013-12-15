package com.wandurr.ld28;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.Color;

public class CharacterAccessor implements TweenAccessor<Character>
{
	public static final int	COLOR	= 1;

	public CharacterAccessor()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getValues(Character target, int tweenType, float[] returnValues)
	{
		switch(tweenType)
		{
			case COLOR:
				returnValues[0] = target.getSpriteColor().r;
				returnValues[1] = target.getSpriteColor().g;
				returnValues[2] = target.getSpriteColor().b;
				returnValues[3] = target.getSpriteColor().a;
				return 4;
			default:
				assert false;
				return -1;
		}
	}

	@Override
	public void setValues(Character target, int tweenType, float[] newValues)
	{
		switch(tweenType)
		{
			case COLOR:
				target.setSpriteColor(new Color(newValues[0], newValues[1], newValues[2], newValues[3]));
				break;
			default:
				assert false;
				break;
		}
	}
}
