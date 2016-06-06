package org.angryautomata.game;

import org.angryautomata.game.action.Action;
import org.angryautomata.game.scenery.Scenery;

public class Engine
{
	private final Game game;

	public Engine(Game game)
	{
		this.game = game;
	}

	public Action action(Player player, Scenery o, Scenery n, Scenery e, Scenery s, Scenery w)
	{
		int state = player.getState();
		int id = game.action(player, state, o.getSymbol());
		Action action = Action.byId(id);

		return action != null && o.getSymbol() == id ? action : Action.MIGRATE;

	}
}
