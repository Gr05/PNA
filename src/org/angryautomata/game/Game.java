package org.angryautomata.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.angryautomata.game.action.Action;
import org.angryautomata.game.scenery.Desert;
import org.angryautomata.game.scenery.Meadow;
import org.angryautomata.game.scenery.Scenery;

public class Game implements Runnable
{
	private final Engine engine = new Engine(this);
	private final Scheduler scheduler = new Scheduler();
	private final Board board;
	private final Map<Player, Position> players = new HashMap<>();
	private boolean pause = false, run = true;
	private int ticks = 0;

	public Game(Board board, Player... players)
	{
		this.board = board;

		if(players != null)
		{
			for(Player player : players)
			{
				this.players.put(player, board.torusPos((int) (Math.random() * 16.0D), (int) (Math.random() * 16.0D)));
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
				Position[] card = {board.torusPos(self.getX(), self.getY() - 1), board.torusPos(self.getX() + 1, self.getY()), board.torusPos(self.getX(), self.getY() + 1), board.torusPos(self.getX() - 1, self.getY())};

				Scenery o = board.sceneryAt(self);
				Scenery n = board.sceneryAt(card[0]);
				Scenery e = board.sceneryAt(card[1]);
				Scenery s = board.sceneryAt(card[2]);
				Scenery w = board.sceneryAt(card[3]);

				Action action = engine.action(player, o, n, e, s, w);

				if(action == Action.MIGRATE)
				{
					players.put(player, card[(int) (Math.random() * card.length)]);
				}
				else if(action == Action.POLLUTE || action == Action.CONTAMINATE || action == Action.POISON)
				{
					board.sceneryAt(self).setTrapped(true);
				}
				else if(action == Action.DRAW)
				{
					board.setScenery(self, new Desert());
				}
				else if(action == Action.HARVEST)
				{
					board.setScenery(self, new Desert());
				}
				else if(action == Action.CUT)
				{
					board.setScenery(self, new Meadow(false));
				}

				player.nextState(o.getSymbol());

				System.out.println(player + " - " + self);
			}

			System.out.println(board);

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

	public int action(Player player, int state, int symbol)
	{
		Position aPos = player.getAutomaton().getOrigin();

		return board.sceneryAt(board.torusPos(aPos.getX() + state, aPos.getY() + symbol)).getSymbol();
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

		players.put(player, board.torusPos(pos.getX() + relX, pos.getY() + relY));
	}
}