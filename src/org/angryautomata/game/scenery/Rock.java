package org.angryautomata.game.scenery;

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
}
