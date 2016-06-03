package org.angryautomata.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Game
{
	private final Engine engine = new Engine();
	private final Board board = new Board();
	private final Scheduler scheduler = new Scheduler();
	private final Map<Player, Position> players = new HashMap<>();

	public Game()
	{

	}

	public void addPlayer(Player player, Position position)
	{
		players.put(player, position);
	}

	public Set<Player> getPlayers()
	{
		return Collections.unmodifiableSet(players.keySet());
	}

	public Player getPlayer(int x, int y)
	{
		return getPlayer(new Position(x, y, board));
	}

	public Player getPlayer(Position position)
	{
		for(Map.Entry<Player, Position> entry : players.entrySet())
		{
			if(entry.getValue().equals(position))
			{
				return entry.getKey();
			}
		}

		return null;
	}

	public Position removePlayer(Player player)
	{
		return players.remove(player);
	}
}