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

		int totalSceneries = Scenery.sceneries();

		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				terrain[i][j] = Scenery.byId((int) (Math.random() * totalSceneries));
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

	public void setSceneryAt(Position position, Scenery scenery)
	{
		terrain[position.getY()][position.getX()] = scenery;
	}

	public Scenery getSceneryAt(Position position)
	{
		return terrain[position.getY()][position.getX()];
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
