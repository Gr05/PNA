package org.angryautomata.game.scenery;

import org.angryautomata.game.action.Action;

public class Rock extends Scenery
{
	private boolean degraded = false;

	public Rock()
	{
		super(4);
	}

	public boolean isDegraded()
	{
		return degraded;
	}

	public void setDegraded(boolean degraded)
	{
		this.degraded = degraded;
	}

	@Override
	public Action[] availableActions()
	{
		return new Action[]{Action.MOVE, Action.BREAK, Action.DEGRADE};
	}
}
