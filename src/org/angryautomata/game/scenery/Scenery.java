package org.angryautomata.game.scenery;

public abstract class Scenery
{
	private final int symbol;

	// texture, etc..
	public Scenery(int symbol)
	{
		this.symbol = symbol;
	}

	public int getSymbol()
	{
		return symbol;
	}
}
