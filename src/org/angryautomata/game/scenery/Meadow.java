package org.angryautomata.game.scenery;

public class Meadow extends Scenery
{
	static
	{
		SCENERIES++;
	}

	public Meadow(boolean trapped)
	{
		super(2, trapped);
	}
}
