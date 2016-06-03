package org.angryautomata.game.action;

import java.util.ArrayList;
import java.util.List;

import org.angryautomata.exception.ActionException;

public abstract class Action
{
	private static final List<Action> ACTIONS = new ArrayList<>();

	private final int id;

	protected Action(int id)
	{
		for(Action action : ACTIONS)
		{
			if(action.getId() == id)
			{
				throw new ActionException("There is already an action with that id");
			}
		}

		this.id = id;

		ACTIONS.add(this);
	}

	public int getId()
	{
		return id;
	}

	public static int actionCount()
	{
		return ACTIONS.size();
	}

	public static Action byId(int id)
	{
		for(Action action : ACTIONS)
		{
			if(action.getId() == id)
			{
				return action;
			}
		}

		return null;
	}
}
