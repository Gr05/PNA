package org.angryautomata.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.angryautomata.game.action.Action;
import org.angryautomata.game.scenery.Desert;
import org.angryautomata.game.scenery.Meadow;
import org.angryautomata.game.scenery.Scenery;
import org.angryautomata.gui.Gui;

public class Game implements Runnable
{
	private final Engine engine = new Engine(this);
	private final Scheduler scheduler = new Scheduler();
	private final Gui gui;
	private final Board board;
	private final Map<Player, Position> players = new HashMap<>();
	private boolean pause = false, run = true;
	private int ticks = 0;

	public Game(Gui gui, Board board, Player... players)
	{
		this.gui = gui;
		this.board = board;

		if(players != null)
		{
			for(Player player : players)
			{
				addPlayer(player, board.torusPos((int) (Math.random() * 16.0D), (int) (Math.random() * 16.0D)));
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

			StringBuilder update = new StringBuilder();

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
					player.updateGradient(-1);
				}
				else if(action == Action.POLLUTE || action == Action.CONTAMINATE || action == Action.POISON)
				{
					board.sceneryAt(self).setTrapped(true);
					players.decGradient();
				}
				else if(action == Action.DRAW)
				{
					player.updateGradient(board.sceneryAt(self).gradient());
					board.setScenery(self, new Desert());
				}
				else if(action == Action.HARVEST)
				{
					player.updateGradient(board.sceneryAt(self).gradient());
					board.setScenery(self, new Desert());
				}
				else if(action == Action.CUT)
				{
					player.updateGradient(board.sceneryAt(self).gradient());
					board.setScenery(self, new Meadow(false));
				}

				player.nextState(o.getSymbol());

				update.append(player).append(" - ").append(self).append("\n");
			}

			update.append(board);

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
}