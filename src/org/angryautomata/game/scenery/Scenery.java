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
			case 1:
			{
				return new Lake(true);
			}

			case 2:
			{
				return new Lake(false);
			}

			case 3:
			{
				return new Meadow(true);
			}

			case 4:
			{
				return new Meadow(false);
			}

			case 5:
			{
				return new Forest(true);
			}

			case 6:
			{
				return new Forest(false);
			}

			default:
			{
				return new Desert();
			}
		}
	}
}
