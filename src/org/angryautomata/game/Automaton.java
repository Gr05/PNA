package org.angryautomata.game;

import org.angryautomata.game.action.Action;

public class Automaton
{
	private final int[][] transitions;
	private final Action[][] actions;

	public Automaton(int[][] transitions, Action[][] actions)
	{
		this.transitions = transitions.clone();
		this.actions = actions.clone();
	}

	public int nextState(int current, int symbol)
	{
		return transitions[current][symbol];
	}

	public Action getAction(int state, int symbol)
	{
		return actions[state][symbol];
	}

	public void modifyAction(int state, int symbol, Action action)
	{
		actions[state][symbol] = action;
	}
}
