package com.wandurr.ld28;

import aurelienribon.tweenengine.TweenAccessor;

public class WeaponTweenAccessor implements TweenAccessor<Weapon>
{
	public static final int	POSITION_X	= 1;
	public static final int	POSITION_Y	= 2;
	public static final int	POSITION_XY	= 3;

	public WeaponTweenAccessor()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getValues(Weapon target, int tweenType, float[] returnValues)
	{
		switch(tweenType)
		{
			case POSITION_X:
				returnValues[0] = target.getRelativePosition().x;
				return 1;
			case POSITION_Y:
				returnValues[0] = target.getRelativePosition().x;
				return 1;
			case POSITION_XY:
				returnValues[0] = target.getRelativePosition().x;
				returnValues[1] = target.getRelativePosition().y;
				return 2;
			default:
				assert false;
				return -1;
		}
	}

	@Override
	public void setValues(Weapon target, int tweenType, float[] newValues)
	{
		switch(tweenType)
		{
			case POSITION_X:
				target.setRelativePosition(newValues[0], target.getRelativePosition().y);

				break;
			case POSITION_Y:
				target.setRelativePosition(target.getRelativePosition().x, newValues[0]);

				break;
			case POSITION_XY:
				target.setRelativePosition(newValues[0], newValues[1]);

				break;
			default:
				assert false;
				break;
		}
	}

}
