package org.angryautomata.game.scenery;

public abstract class Scenery
{
	private static int SCENERIES = 4;

	private final int symbol;
	private final int grad;
	private boolean trapped;

	// texture, etc..
	Scenery(int symbol, int grad, boolean trapped)
	{
		this.symbol = symbol;
		this.grad = grad;

		setTrapped(trapped);
	}

	public int getFakeSymbol()
	{
		return symbol;
	}

	public int getSymbol()
	{
		return trapped ? getFakeSymbol() + SCENERIES - 1 : getFakeSymbol();
	}

	public int gradient()
	{
		return trapped ? -grad : grad;
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

	public static Scenery valueOf(int k)
	{
		switch(k)
		{
			case 1:
			{
				return new Lake(false);
			}

			case 2:
			{
				return new Meadow(false);
			}

			case 3:
			{
				return new Forest(false);
			}

			case 4:
			{
				return new Lake(true);
			}

			case 5:
			{
				return new Meadow(true);
			}

			case 6:
			{
				return new Forest(true);
			}

			default:
			{
				return new Desert();
			}
		}
	}
}
