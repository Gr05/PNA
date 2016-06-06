package org.angryautomata.game;

import org.angryautomata.game.action.Action;
import org.angryautomata.game.scenery.*;

public class Engine
{
	private final Game game;

	public Engine(Game game)
	{
		this.game = game;
	}

	public void execute(Player player, Scenery o, Scenery n, Scenery e, Scenery s, Scenery w)
	{
		player.nextState(o.getSymbol());

		if(o instanceof Forest)
		{
			Forest forest = (Forest) o;
			Action[] actions = forest.availableActions();

		}
		else if(o instanceof Lake)
		{
			
		}
		else if(o instanceof Rock)
		{

		}
		else if(o instanceof Plains)
		{

		}


	}
}
