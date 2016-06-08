package org.angryautomata;

import org.angryautomata.game.Automaton;
import org.angryautomata.game.Board;
import org.angryautomata.game.Game;
import org.angryautomata.game.Position;
import org.angryautomata.gui.Gui;

public class Main
{
	public static void main(String[] args)
	{
		Position origin = new Position(0, 0);
		int[][] transitions = new int[32][32];
		int[][] actions = new int[32][32];

		for(int i = 0; i < 32; i++)
		{
			for(int j = 0; j < 32; j++)
			{
				transitions[i][j] = (int) (Math.random() * 32);
				actions[i][j] = (int) (Math.random() * 7);
			}
		}

		Automaton automaton = new Automaton(transitions, actions, origin, "MonAutomateDébile");

		Board board = new Board(32, 32);

		Game game = new Game(board, automaton);

		Gui.setSystemLookAndFeel();

		Gui gui = new Gui(game);

		Thread gameThread = new Thread(game);
		gameThread.setDaemon(true);
		gameThread.start();
	}
}
