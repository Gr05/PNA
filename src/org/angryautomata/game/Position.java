package org.angryautomata.game;

public class Position
{
	private final int x;
	private final int y;

	public Position(int x, int y, Board board)
	{
		x %= board.getWidth();
		y %= board.getHeight();

		if(x < 0)
		{
			x += board.getWidth();
		}

		if(y < 0)
		{
			y += board.getHeight();
		}

		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}

		if(!(o instanceof Position))
		{
			return false;
		}

		Position that = (Position) o;

		return getX() == that.getX() && getY() == that.getY();

	}

	@Override
	public int hashCode()
	{
		return 31 * getX() + getY();
	}
}
