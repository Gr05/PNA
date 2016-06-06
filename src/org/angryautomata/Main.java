package org.angryautomata;

import java.util.Scanner;

import org.angryautomata.game.*;

public class Main
{
	public static void main(String[] args)
	{
		Position origin = new Position(0, 0);
		int[][] transitions = {{1, 0, 3, 2}, {2, 2, 0, 1}, {1, 0, 0, 1}, {0, 3, 3, 2}};
		int[][] actions = {{1, 0, 3, 2}, {2, 2, 0, 1}, {1, 0, 0, 1}, {0, 3, 3, 2}, {0, 1, 2, 3}};

		Automaton automaton = new Automaton(transitions, origin);
		Player player = new Player(automaton, 0);

		Board board = new Board(16, 16);

		board.addScenery(origin, actions, transitions.length);

		Game game = new Game(board, player);

		Thread gameThread = new Thread(game);
		gameThread.setDaemon(true);
		gameThread.start();

		try(Scanner scanner = new Scanner(System.in))
		{
			while(scanner.hasNext())
			{
				String input = scanner.next();

				if(input.equals("stop") || input.equals("exit") || input.equals("close"))
				{
					game.stop();
					System.out.println("Simulation ended.");

					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
