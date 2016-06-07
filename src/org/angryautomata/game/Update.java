package org.angryautomata.game;

public class Update
{
	private final int prevSymbol;
	private final int regenTicks;
	private int ticksLeft;

	public Update(int prevSymbol, int regenTicks)
	{
		this.prevSymbol = prevSymbol;
		this.regenTicks = ticksLeft = regenTicks;
	}

	public void countDown()
	{
		ticksLeft--;
	}

	public boolean canUpdate()
	{
		return ticksLeft <= 0;
	}

	public void reset()
	{
		ticksLeft = regenTicks;
	}

	public int ticksLeft()
	{
		return ticksLeft;
	}

	public int getPrevSymbol()
	{
		return prevSymbol;
	}
}
