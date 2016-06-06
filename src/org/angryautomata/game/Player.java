package org.angryautomata.game;

public class Player
{
	private final Automaton automaton;
	private int state;
	private int gradient = 50;

	public Player(Automaton automaton, int state)
	{
		this.automaton = automaton;
		this.state = state;
	}

	public Automaton getAutomaton()
	{
		return automaton;
	}

	public int getState()
	{
		return state;
	}

	public int nextState(int symbol)
	{
		return state = automaton.nextState(state, symbol);
	}

	public int getGradient()
	{
		return gradient;
	}

	public void updateGradient(int grad)
	{
		gradient += grad;
	}

	@Override
	public String toString()
	{
		return state + ", " + gradient;
	}
}