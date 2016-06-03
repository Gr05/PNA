package org.angryautomata.game;

import org.angryautomata.game.action.Action;

public class Board
{
	private final Action[][] terrain;
	private final int height, width;

	public Board()
	{
		height = 256;
		width = 256;
		terrain = new Action[height][width];
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	public void setAction(Position position, Action action)
	{

	}

	public Action getAction(int x, int y)
	{
		return terrain[y][x];
	}
}
