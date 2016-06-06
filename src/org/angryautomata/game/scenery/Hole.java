package org.angryautomata.game.scenery;

import org.angryautomata.game.action.Action;

public class Hole extends Scenery
{
	public Hole()
	{
		super(0);
	}

	@Override
	public Action[] availableActions()
	{
		return new Action[0];
	}
}
