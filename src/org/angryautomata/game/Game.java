package org.angryautomata.game;

import java.util.*;

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
				addPlayer(player, board.torusPos((int) (Math.random() * board.getWidth()), (int) (Math.random() * board.getHeight())));
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

				Action action = action(player, o);

				if(action == Action.NOTHING)
				{
					player.updateGradient(-1);
				}
				else if(action == Action.MIGRATE)
				{
					players.put(player, card[(int) (Math.random() * card.length)]);
					player.updateGradient(-1);
				}
				else if(action == Action.POLLUTE || action == Action.CONTAMINATE || action == Action.POISON)
				{
					board.sceneryAt(self).setTrapped(true);
					player.updateGradient(-1);
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

				player.nextState(o.getFakeSymbol());

				update.append(player).append(" - ").append(o.getSymbol()).append(" - ").append(self).append("\n");
				update.append(action).append("\n");

				if(player.getGradient() <= 0)
				{
					removePlayer(player);
				}
			}

			gui.update(update.toString(), board, new ArrayList<>(players.values()));

			ticks++;

			try
			{
				Thread.sleep(20L);
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

	public Action action(Player player, Scenery o)
	{
		int state = player.getState();
		Position origin = player.getAutomaton().getOrigin();
		int id = board.sceneryAt(board.torusPos(origin.getX() + state, origin.getY() + o.getFakeSymbol())).getSymbol();
		Action action = Action.byId(id);

		return action != null && matches(action, o) ? action : Action.NOTHING;
	}

	private boolean matches(Action action, Scenery scenery)
	{
		/*if(action.getId() <= 0)
		{
			return true;
		}

		if(scenery.getFakeSymbol() == 1 && (action.getId() == 1 || action.getId() == 2))
		{
			return true;
		}

		if(scenery.getFakeSymbol() == 2 && (action.getId() == 3 || action.getId() == 4))
		{
			return true;
		}

		if(scenery.getFakeSymbol() == 3 && (action.getId() == 5 || action.getId() == 6))
		{
			return true;
		}

		return false;*/

		return action.getId() <= 0 || scenery.getFakeSymbol() == 1 && (action.getId() == 1 || action.getId() == 2) || scenery.getFakeSymbol() == 2 && (action.getId() == 3 || action.getId() == 4) || scenery.getFakeSymbol() == 3 && (action.getId() == 5 || action.getId() == 6);

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
		Position position = players.remove(player);

		if(players.isEmpty())
		{
			stop();
		}

		return position;
	}
}