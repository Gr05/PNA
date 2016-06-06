package org.angryautomata.game.scenery;

import org.angryautomata.game.action.Action;

public class Plains extends Scenery
{
	public Plains()
	{
		super(1);
	}

	@Override
	public Action[] availableActions()
	{
		return new Action[0];
	}
}
