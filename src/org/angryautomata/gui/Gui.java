package org.angryautomata.gui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.angryautomata.game.Board;
import org.angryautomata.game.Game;
import org.angryautomata.game.Position;

public class Gui extends JFrame
{
	private final Game game;
	private final int screenHeight, screenWidth;
	private final IntegerTableModel model;
	private JPanel contentPane;
	private JButton quitButton;
	private JTable screen;
	private JButton pauseAndResumeButton;
	private JButton stopButton;
	private IntegerCellRenderer renderer;

	public Gui(Game game)
	{
		super("Jeu");

		this.game = game;
		screenHeight = game.getHeight();
		screenWidth = game.getWidth();
		model = new IntegerTableModel(screenHeight, screenWidth);

		setContentPane(contentPane);

		initUIComponents();
		addEventHandlers();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initUIComponents()
	{
		screen.setModel(model);

		int length = 16;

		for(Enumeration<TableColumn> columns = screen.getColumnModel().getColumns(); columns.hasMoreElements(); )
		{
			TableColumn column = columns.nextElement();

			column.setMinWidth(length);
			column.setMaxWidth(length);
			column.setPreferredWidth(length);
		}

		screen.setRowHeight(length);
		screen.setCellSelectionEnabled(true);

		renderer = new IntegerCellRenderer();
		screen.setDefaultRenderer(int.class, renderer);
	}

	private void addEventHandlers()
	{
		quitButton.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
		pauseAndResumeButton.addActionListener(e ->
		{
			if(game.isPaused())
			{
				pauseAndResumeButton.setText("Pause");
				screen.setEnabled(false);

				game.resume();
			}
			else
			{
				pauseAndResumeButton.setText("Reprendre");
				screen.setEnabled(true);

				game.pause();
			}
		});
		stopButton.addActionListener(e -> game.stop());
	}

	public void update(Board board, Map<Position, Color> positions)
	{
		SwingUtilities.invokeLater(() ->
		{
			renderer.setHighlight(positions);

			for(int i = 0; i < screenHeight; i++)
			{
				for(int j = 0; j < screenWidth; j++)
				{
					screen.setValueAt(board.sceneryAt(board.torusPos(j, i)).getSymbol(), i, j);
				}
			}
		});
	}

	public static void setSystemLookAndFeel()
	{
		setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}

	private static void setLookAndFeel(String lookAndFeel)
	{
		JFrame frame = new JFrame();

		try
		{
			UIManager.setLookAndFeel(lookAndFeel);
		}
		catch(Exception e)
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}
			catch(Exception ignored)
			{
			}
		}

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("test"));
		frame.add(panel);

		try
		{
			frame.pack();
		}
		catch(Exception e)
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}
			catch(Exception ignored)
			{
			}
		}

		frame.dispose();

		JFrame.setDefaultLookAndFeelDecorated(false);
	}

	private static class IntegerTableModel extends AbstractTableModel
	{
		private final int rows, columns;
		private final int[][] data;

		IntegerTableModel(int screenHeight, int screenWidth)
		{
			rows = screenHeight;
			columns = screenWidth;
			data = new int[rows][columns];

			for(int row = 0; row < rows; row++)
			{
				for(int column = 0; column < columns; column++)
				{
					setValueAt(-1, row, column);
				}
			}
		}

		@Override
		public int getRowCount()
		{
			return rows;
		}

		@Override
		public int getColumnCount()
		{
			return columns;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex)
		{
			return int.class;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex)
		{
			return data[rowIndex][columnIndex];
		}

		@Override
		public void setValueAt(Object object, int rowIndex, int columnIndex)
		{
			if(object instanceof Integer)
			{
				data[rowIndex][columnIndex] = (int) object;

				fireTableCellUpdated(rowIndex, columnIndex);
			}
			else
			{
				try
				{
					int k = Integer.parseInt(object.toString());
					data[rowIndex][columnIndex] = k;

					fireTableCellUpdated(rowIndex, columnIndex);
				}
				catch(Exception ignored)
				{
				}
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex)
		{
			return true;
		}
	}

	private static class IntegerCellRenderer extends DefaultTableCellRenderer
	{
		private final Map<Position, Color> highlight = new HashMap<>();

		void setHighlight(Map<Position, Color> highlight)
		{
			this.highlight.clear();
			this.highlight.putAll(highlight);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			if(value != null)
			{
				Color highColor = null;

				for(Map.Entry<Position, Color> entry : highlight.entrySet())
				{
					Position position = entry.getKey();

					if(row == position.getY() && column == position.getX())
					{
						highColor = entry.getValue();

						break;
					}
				}

				if(highColor == null)
				{
					switch((int) value)
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
