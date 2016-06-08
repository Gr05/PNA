package org.angryautomata.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Automaton
{
	private final int[][] transitions;
	private final int[][] actions;
	private final Position origin;
	private final String name;
	private final Color color;
	private final List<Player> players = new ArrayList<>();

	public Automaton(int[][] transitions, int[][] actions, Position origin, String name, int redness)
	{
		this.transitions = transitions;
		this.actions = actions;
		this.origin = origin;
		this.name = name;
		color = new Color(redness, 0, 0);
	}

	public int nextState(int state, int symbol)
	{
		return transitions[symbol][state];
	}

	public int initialAction(int state, int symbol)
	{
		return actions[symbol][state];
	}

	public int numberOfStates()
	{
		return transitions[0].length;
	}

	public Position getOrigin()
	{
		return origin;
	}

	public String getName()
	{
		return name;
	}

	public List<Player> getPlayers()
	{
		return Collections.unmodifiableList(players);
	}

	public void addPlayer(Player player)
	{
		if(!players.contains(player))
		{
			players.add(player);
		}
	}

	public boolean removePlayer(Player player)
	{
		return players.remove(player);
	}

	public Color getColor()
	{
		return color;
	}
}
