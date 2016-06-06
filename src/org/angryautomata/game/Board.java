package org.angryautomata.game;

import org.angryautomata.game.action.Action;
import org.angryautomata.game.scenery.Desert;
import org.angryautomata.game.scenery.Scenery;

public class Board
{
	private final Scenery[][] terrain;
	private final int height, width;

	public Board(int width, int height)
	{
		terrain = new Scenery[height][width];
		this.width = width;
		this.height = height;

		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				terrain[i][j] = new Desert();
			}
		}
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	public void setScenery(Position position, Scenery scenery)
	{
		terrain[position.getY()][position.getX()] = scenery;
	}

	public Scenery sceneryAt(Position position)
	{
		return terrain[position.getY()][position.getX()];
	}

	public void addScenery(Position origin, int[][] actions)
	{
		int count = Action.count();
		int ox = origin.getX(), oy = origin.getY();

		for(int y = 0; y < count; y++)
		{
			for(int x = 0; x < states; x++)
			{
				setScenery(torusPos(x + ox, y + oy), Scenery.valueOf(actions[y][x]));
			}
		}
	}

	public Position torusPos(int x, int y)
	{
		x %= width;
		y %= height;

		if(x < 0)
		{
			x += width;
		}

		if(y < 0)
		{
			y += height;
		}

		return new Position(x, y);
	}

	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder();

		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				result.append(terrain[i][j].getSymbol()).append("\t");
			}

			result.append(System.lineSeparator());
		}

		return result.toString();
	}
}
