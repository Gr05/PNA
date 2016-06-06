package org.angryautomata.game.action;

public enum Action
{
	NOTHING(-1), MIGRATE(0), POLLUTE(1), DRAW(2), CONTAMINATE(3), HARVEST(4), POISON(5), CUT(6);

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
