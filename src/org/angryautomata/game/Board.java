package org.angryautomata.game;

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
				terrain[i][j] = Scenery.valueOf((int) (Math.random() * Scenery.sceneries()));
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
		int yMax = actions.length, xMax = actions[0].length;
		int ox = origin.getX(), oy = origin.getY();

		for(int y = 0; y < yMax; y++)
		{
			for(int x = 0; x < xMax; x++)
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

	public Position randomPos()
	{
		return new Position((int) (Math.random() * width), (int) (Math.random() * height));
	}
}
