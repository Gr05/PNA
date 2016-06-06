package org.angryautomata.game.scenery;

import org.angryautomata.game.action.Action;

public class Forest extends Scenery
{
	private boolean degraded = false;

	public Forest()
	{
		super(2);
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
		return new Action[]{Action.MOVE, Action.EAT, Action.CUT, Action.DEGRADE};
	}
}
