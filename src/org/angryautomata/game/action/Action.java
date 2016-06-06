package org.angryautomata.game.action;

public enum Action
{
	NOTHING(0), MOVE(1), EAT(2), DRINK(3), CUT(4), BREAK(5), DEGRADE(6);

	private final int id;

	Action(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public static int count()
	{
		return values().length;
	}

	public static Action byId(int id)
	{
		for(Action action : values())
		{
			if(action.getId() == id)
			{
				return action;
			}
		}

		return null;
	}
}
