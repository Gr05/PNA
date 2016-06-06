package org.angryautomata.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.angryautomata.game.scenery.Scenery;

public class Game implements Runnable
{
	private final Engine engine = new Engine(this);
	private final Board board = new Board(16, 16);
	private final Scheduler scheduler = new Scheduler();
	private final Map<Player, Position> players = new HashMap<>();
	private boolean pause = false, run = true;
	private int ticks = 0;

	public Game(Automaton... automata)
	{
		if(automata != null)
		{
			for(Automaton automaton : automata)
			{
				players.put(new Player(automaton, 0), new Position((int) (Math.random() * 16.0D), (int) (Math.random() * 16.0D)));
			}
		}
	}

	@Override
	public void run()
	{
		while(run)
		{
			while(pause)
			{
				;
			}

			for(Map.Entry<Player, Position> entry : players.entrySet())
			{
				Player player = entry.getKey();

				Position self = entry.getValue();

				Scenery o = board.getScenery(self);
				Scenery n = board.getScenery(new Position(self.getX(), self.getY() - 1));
				Scenery e = board.getScenery(new Position(self.getX() + 1, self.getY()));
				Scenery s = board.getScenery(new Position(self.getX(), self.getY() + 1));
				Scenery w = board.getScenery(new Position(self.getX() - 1, self.getY()));

				int state = player.nextState(o.getSymbol());

				//Action action = engine.action(player, o, n, e, s, w);
			}

			ticks++;

			try
			{
				Thread.sleep(1000L);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void pause()
	{
		pause = true;
	}

	public void resume()
	{
		pause = false;
	}

	public void stop()
	{
		run = false;
	}

	public int ticks()
	{
		return ticks;
	}

	public void addPlayer(Player player, Position position)
	{
		players.put(player, position);
	}

	public Set<Player> getPlayers()
	{
		return Collections.unmodifiableSet(players.keySet());
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

	public void movePlayer(Player player, int relX, int relY)
	{
		Position pos = players.get(player);

		players.put(player, new Position(pos.getX() + relX, pos.getY() + relY));
	}
}