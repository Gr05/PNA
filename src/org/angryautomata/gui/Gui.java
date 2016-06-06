package org.angryautomata.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.angryautomata.game.Board;
import org.angryautomata.game.Position;

public class Gui extends JFrame
{
	private final JTextArea info = new JTextArea(4, 0);
	private final CustomCellRenderer renderer;
	private final JTable screen;

	public Gui(Board board)
	{
		super("Jeu");

		screen = new JTable(board.getHeight(), board.getWidth());
		renderer = new CustomCellRenderer();
		screen.setDefaultRenderer(Object.class, renderer);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		add(info, BorderLayout.NORTH);
		add(screen, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void update(String s, Board board, List<Position> positions)
	{
		SwingUtilities.invokeLater(() ->
		{
			List<Position> fpositions = new ArrayList<>(positions);

			info.setText(s);
			renderer.setHighlight(fpositions);

			for(int i = 0; i < board.getHeight(); i++)
			{
				for(int j = 0; j < board.getWidth(); j++)
				{
					screen.setValueAt(board.sceneryAt(board.torusPos(j, i)).getSymbol(), i, j);
				}
			}
		});
	}

	private static class CustomCellRenderer extends DefaultTableCellRenderer
	{
		private final List<Position> highlight = new ArrayList<>();

		public void setHighlight(List<Position> highlight)
		{
			this.highlight.clear();
			this.highlight.addAll(highlight);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			if(value != null)
			{
				Color highColor = null;

				for(Position position : highlight)
				{
					if(row == position.getY() && column == position.getX())
					{
						highColor = Color.RED;

						break;
					}
				}

				if(highColor == null)
				{
					switch((Integer) value)
					{
						case 0:
						{
							highColor = Color.YELLOW;

							break;
						}

						case 1:
						case 4:
						{
							highColor = Color.BLUE.brighter();

							break;
						}

						case 2:
						case 5:
						{
							highColor = Color.GREEN;

							break;
						}

						case 3:
						case 6:
						{
							highColor = Color.GREEN.darker();

							break;
						}

						default:
						{
							highColor = Color.WHITE;
						}
					}
				}

				c.setBackground(highColor);
			}

			return c;
		}
	}
}
