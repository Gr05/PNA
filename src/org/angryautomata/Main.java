package org.angryautomata;

import org.angryautomata.game.*;
import org.angryautomata.gui.Gui;

public class Main
{
	public static void main(String[] args)
	{
		Position origin1 = new Position(0, 0);
		int[][] transitions1 = {
				{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0},
				{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0},
				{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0},
				{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0}
		};
		int[][] actions1 = {
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // desert
				{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // lac
				{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}, // prairie
				{6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6}  // foret
		};

		Automaton automaton1 = new Automaton(transitions1, origin1);
		Player player1 = new Player(automaton1, 0, 0, 255);


		Position origin2 = new Position(16, 16);
		int[][] transitions2 = {
				{2, 1, 3, 0, 2, 2, 1, 0, 1, 3, 3, 1},
				{1, 1, 0, 2, 1, 1, 1, 0, 2, 3, 3, 3},
				{0, 2, 3, 3, 3, 3, 1, 0, 2, 0, 0, 3},
				{1, 0, 3, 3, 0, 0, 0, 0, 2, 0, 2, 0}
		};
		int[][] actions2 = {
				{0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 0}, // desert
				{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // lac
				{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}, // prairie
				{6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6}  // foret
		};

		Automaton automaton2 = new Automaton(transitions2, origin2);
		Player player2 = new Player(automaton2, 0, 1, 127);


		Board board = new Board(32, 32);
		board.addScenery(origin1, actions1);
		board.addScenery(origin2, actions2);

		Game game = new Game(board, player1, player2);

		Gui.setSystemLookAndFeel();

		Gui gui = new Gui(game);
		game.setGui(gui);

		Thread gameThread = new Thread(game);
		gameThread.setDaemon(true);
		gameThread.start();
	}
}
