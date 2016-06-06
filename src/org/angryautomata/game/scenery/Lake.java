package org.angryautomata.game.scenery;

public class Lake extends Scenery
{
	static
	{
		SCENERIES++;
	}

	public Lake(boolean trapped)
	{
		super(1, 1, trapped);
	}
}
