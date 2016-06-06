package org.angryautomata.game.scenery;

import org.angryautomata.game.action.Action;

public class Lake extends Scenery
{
	private boolean degraded = false;

	public Lake()
	{
		super(3);
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
		return new Action[]{Action.DRINK, Action.POLLUTE};
	}
}
