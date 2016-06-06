package org.angryautomata.game.scenery;

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
}
