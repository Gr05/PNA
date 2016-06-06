package org.angryautomata.game.scenery;

import org.angryautomata.game.action.Action;

public class Air extends Scenery
{
	public Air()
	{
		super(0);
	}

	@Override
	public Action[] availableActions()
	{
		return new Action[]{Action.NOTHING};
	}
}
