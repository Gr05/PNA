package org.angryautomata.game.scenery;

public abstract class Scenery
{
	protected static int SCENERIES = 0;

	private final int symbol;
	private boolean trapped;

	// texture, etc..
	Scenery(int symbol, boolean trapped)
	{
		this.symbol = symbol;

		setTrapped(trapped);
	}

	public int getSymbol()
	{
		return trapped ? symbol + SCENERIES - 1 : symbol;
	}

	public boolean isTrapped()
	{
		return trapped;
	}

	public void setTrapped(boolean trapped)
	{
		this.trapped = trapped;
	}

	public static int sceneries()
	{
		return SCENERIES;
	}

	public static Scenery valueOf(int action)
	{
		switch(action)
		{
			case 0:
			{
				return new Desert();
			}

			case 1:
			case 2:
			{
				return new Lake(false);
			}

			case 3:
			case 4:
			{
				return new Meadow(false);
			}

			case 5:
			case 6:
			{
				return new Forest(false);
			}

			default:
			{
				return null;
			}
		}
	}
}
