package org.angryautomata.game;

public class Automaton
{
	private final int[][] transitions;
	private final Position origin;

	public Automaton(int[][] transitions, Position origin)
	{
		this.transitions = transitions.clone();
		this.origin = origin;
	}

	public int nextState(int current, int symbol)
	{
		return transitions[symbol][current];
	}

	public Position getOrigin()
	{
		return origin;
	}
}
