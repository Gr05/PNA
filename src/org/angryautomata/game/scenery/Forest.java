package org.angryautomata.game.scenery;

public class Forest extends Scenery
{
	static
	{
		SCENERIES++;
	}

	public Forest(boolean trapped)
	{
		super(3, trapped);
	}
}
