package org.angryautomata.game;

import java.awt.*;
import java.util.*;
import java.util.List;

import org.angryautomata.game.action.Action;
import org.angryautomata.game.scenery.Desert;
import org.angryautomata.game.scenery.Meadow;
import org.angryautomata.game.scenery.Scenery;
import org.angryautomata.gui.Gui;

public class Game implements Runnable
{
	private final Board board;
	private final Map<Player, Position> players = new HashMap<>();
	private final Map<Position, LinkedList<Update>> toUpdate = new HashMap<>();
	private Gui gui = null;
	private long tickSpeed = 500L;
	private boolean pause = false, run = true;
	private int ticks = 0;

	public Game(Board board, Player... players)
	{
		this.board = board;

		if(players != null)
		{
			for(Player player : players)
			{
				addPlayer(player, board.randomPos());
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
				try
				{
					Thread.sleep(10L);
				}
				catch(InterruptedException ignored)
				{
				}
			}

			List<Player> clones = new ArrayList<>(), dead = new ArrayList<>();

			for(Map.Entry<Player, Position> entry : players.entrySet())
			{
				Player player = entry.getKey();

				Position self = entry.getValue();
				Position[] card = {board.torusPos(self.getX(), self.getY() - 1), board.torusPos(self.getX() + 1, self.getY()), board.torusPos(self.getX(), self.getY() + 1), board.torusPos(self.getX() - 1, self.getY())};

				Scenery o = board.sceneryAt(self);

				if(player.canClone())
				{
					clones.add(player.createClone());
				}
				else
				{
					Action action = action(player, o);

					if(action == Action.NOTHING)
					{
						player.updateGradient(-1);
					}
					else if(action == Action.MIGRATE)
					{
						entry.setValue(card[(int) (Math.random() * card.length)]);

						player.updateGradient(-1);
					}
					else if(action == Action.POLLUTE || action == Action.CONTAMINATE || action == Action.POISON)
					{
						board.sceneryAt(self).setTrapped(true);

						player.updateGradient(-1);
					}
					else
					{
						if(action == Action.DRAW)
						{
							player.updateGradient(o.gradient());

							board.setScenery(self, new Desert());
						}
						else if(action == Action.HARVEST)
						{
							player.updateGradient(o.gradient());

							board.setScenery(self, new Desert());
						}
						else if(action == Action.CUT)
						{
							player.updateGradient(o.gradient());

							board.setScenery(self, new Meadow(false));
						}

						if(!toUpdate.containsKey(self))
						{
							toUpdate.put(self, new LinkedList<>());
						}

						LinkedList<Update> pending = toUpdate.get(self);
						Update update = new Update(o.getFakeSymbol(), 50);
						pending.addFirst(update);
					}
				}

				if(player.isDead())
				{
					dead.add(player);
				}
				else
				{
					player.nextState(o.getFakeSymbol());
				}
			}

			int height = board.getHeight(), width = board.getWidth();

			for(Player player : clones)
			{
				addPlayer(player, board.randomPos());
			}

			dead.forEach(this::removePlayer);

			int randomTileUpdates = (int) ((height * width) * 0.2F);

			for(int k = 0; k < randomTileUpdates; k++)
			{
				Position rnd = board.randomPos();
				LinkedList<Update> updates = toUpdate.get(rnd);

				if(updates != null && !updates.isEmpty())
				{
					Update update = updates.peekLast();

					if(update.canUpdate())
					{
						board.setScenery(rnd, Scenery.valueOf(update.getPrevSymbol()));

						updates.removeLast();
					}
					else
					{
						update.countDown();
					}
				}
			}

			if(gui != null)
			{
				Map<Position, Color> colors = new HashMap<>();

				for(Map.Entry<Player, Position> entry : players.entrySet())
				{
					colors.put(entry.getValue(), entry.getKey().getColor());
				}

				gui.update(board, colors);
			}

			ticks++;

			try
			{
				Thread.sleep(tickSpeed);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	private Action action(Player player, Scenery o)
	{
		int state = player.getState();
		Position origin = player.getAutomaton().getOrigin();
		int id = board.sceneryAt(board.torusPos(origin.getX() + state, origin.getY() + o.getFakeSymbol())).getSymbol();
		Action action = Action.byId(id);

		return action != null && matches(action, o) ? action : Action.MIGRATE;
	}

	private boolean matches(Action action, Scenery scenery)
	{
		return action.getId() <= 0 || scenery.getFakeSymbol() == 1 && (action.getId() == 1 || action.getId() == 2) || scenery.getFakeSymbol() == 2 && (action.getId() == 3 || action.getId() == 4) || scenery.getFakeSymbol() == 3 && (action.getId() == 5 || action.getId() == 6);
	}

	private void addPlayer(Player player, Position position)
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

	private Position removePlayer(Player player)
	{
		Position position = players.remove(player);

		if(players.isEmpty())
		{
			stop();
		}

		return position;
	}

	private boolean hasPlayerOn(Position position)
	{
		return players.values().contains(position);
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

	public boolean isPaused()
	{
		return pause;
	}

	public boolean isStopped()
	{
		return !run;
	}

	public int ticks()
	{
		return ticks;
	}

	public int getWidth()
	{
		return board.getWidth();
	}

	public int getHeight()
	{
		return board.getHeight();
	}

	public void setGui(Gui gui)
	{
		this.gui = gui;
	}
}