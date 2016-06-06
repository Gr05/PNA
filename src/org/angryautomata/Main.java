package org.angryautomata;

import org.angryautomata.game.*;
import org.angryautomata.gui.Gui;

public class Main
{
	public static void main(String[] args)
	{
		Position origin = new Position(0, 0);
		int[][] transitions = {
				{1, 2, 3, 0},
				{1, 2, 3, 0},
				{1, 2, 3, 0},
				{1, 2, 3, 0}
		};
		int[][] actions = {
				{0, 0, 0, 0}, // desert
				{2, 2, 2, 2}, // lake
				{4, 4, 4, 4}, // meadow
				{6, 6, 6, 6}  // forest
		};

		Automaton automaton = new Automaton(transitions, origin);
		Player player = new Player(automaton, 0);

		Board board = new Board(16, 16);

		board.addScenery(origin, actions);

		Gui gui = new Gui(board);

		Game game = new Game(gui, board, player);

		Thread gameThread = new Thread(game);
		gameThread.setDaemon(true);
		gameThread.start();
	}
}
