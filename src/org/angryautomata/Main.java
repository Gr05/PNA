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
		Position origin1 = new Position(0, 0);
		int[][] transitions1 = {
				{0},
				{0},
				{0},
				{0}
		};
		int[][] actions1 = {
				{0}, // desert
				{2}, // lac
				{4}, // prairie
				{6}  // foret
		};

		Automaton automaton1 = new Automaton(transitions1, actions1, origin1, "MonAutomateCool", 255);

		Position origin2 = new Position(16, 16);
		int[][] transitions2 = new int[4][12];
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 12; j++)
			{
				transitions2[i][j] = (int) (Math.random() * 12);
			}
		}
		int[][] actions2 = {
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // desert
				{2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1}, // lac
				{4, 4, 3, 4, 3, 4, 4, 3, 4, 4, 3, 4}, // prairie
				{5, 6, 5, 5, 6, 5, 6, 5, 5, 6, 5, 6}  // foret
		};

		Automaton automaton2 = new Automaton(transitions2, actions2, origin2, "TonAutomateNase", 159);

		Board board = new Board(32, 32);

		Game game = new Game(board, automaton1, automaton2);

		Gui.setSystemLookAndFeel();

		Gui gui = new Gui(game);

		Thread gameThread = new Thread(game);
		gameThread.setDaemon(true);
		gameThread.start();
	}
}
