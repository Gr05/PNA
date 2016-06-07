package org.angryautomata.game;

public class Update
{
	private final int prevSymbol;
	private final int regenTicks;
	private int ticksLeft;
	private Update next = null;

	public Update(int prevSymbol, int regenTicks)
	{
		this.prevSymbol = prevSymbol;
		this.regenTicks = ticksLeft = regenTicks;
	}

	public void countDown()
	{
		if(next == null)
		{
			ticksLeft--;
		}
		else
		{
			next.countDown();
		}
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

	public void setNextUpdate(Update update)
	{
		if(next == null)
		{
			reset();
			next = update;
		}
		else
		{
			next.setNextUpdate(update);
		}
	}
}
