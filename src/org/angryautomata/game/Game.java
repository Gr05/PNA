package org.angryautomata.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game
{
	private final Engine engine = new Engine();
	private final Board board = new Board();
	private final Scheduler scheduler = new Scheduler();
	private final List<Player> players = new ArrayList<>();

	public Game()
	{

	}

	public void addPlayer(Player player)
	{
		if(!players.contains(player))
		{
			players.add(player);
		}
	}

	public List<Player> getPlayers()
	{
		return Collections.unmodifiableList(players);
	}

	public Player getPlayer(int x, int y)
	{
		return getPlayer(new Position(x, y));
	}

	public Player getPlayer(Position position)
	{
		for(Player player : players)
		{
			if(player.getPosition().equals(position))
			{
				return player;
			}
		}

		return null;
	}
}