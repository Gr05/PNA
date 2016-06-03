package org.angryautomata.game;

public class Player
{
	private final Automaton automaton;
	private int state;

	public Player(Automaton automaton, int state)
	{
		this.automaton = automaton;
		this.state = state;
	}
}