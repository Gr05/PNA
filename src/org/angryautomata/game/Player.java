package org.angryautomata.game;

import java.awt.*;

public class Player
{
	private final Automaton automaton;
	private final int initGradient = 50;
	private final int team;
	private final Color color;
	private int state;
	private int gradient = initGradient;

	public Player(Automaton automaton, int state, int team, int red)
	{
		this.automaton = automaton;
		this.state = state;
		this.team = team;
		color = new Color(red, 0, 0);
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

	public int getTeam()
	{
		return team;
	}

	public Color getColor()
	{
		return color;
	}

	public void updateGradient(int grad)
	{
		gradient += grad;
	}

	public boolean isDead()
	{
		return gradient <= 0;
	}

	public boolean canClone()
	{
		return gradient >= 100;
	}

	public Player createClone()
	{
		Player clone = new Player(automaton, 0, team, color.getRed());

		clone.updateGradient(-initGradient);

		int splitGradient = gradient / 2;

		updateGradient(-splitGradient);
		clone.updateGradient(splitGradient);

		return clone;
	}

	@Override
	public String toString()
	{
		return state + ", " + gradient;
	}
}