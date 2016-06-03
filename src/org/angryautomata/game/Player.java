package org.angryautomata.game;

import org.angryautomata.exception.PositionException;

public class Player
{
	private final Automaton automaton;
	private Position position;

	public Player(Automaton automaton, Position position)
	{
		if(position == null)
		{
			throw new PositionException("position must be non-null");
		}

		this.automaton = automaton;
		this.position = position;
	}

	public Position getPosition()
	{
		return position;
	}

	public void setPosition(int x, int y)
	{
		setPosition(new Position(x, y));
	}

	public void setPosition(Position position)
	{
		this.position = position;
	}
}