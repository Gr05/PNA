package org.angryautomata.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
	private final int screenHeight, screenWidth;
	private final Game game;
	private JPanel contentPane;
	private JButton quitButton;
	private JTable screen;
	private JButton pauseAndResumeButton;
	private JButton stopButton;
	private CustomCellRenderer renderer;

	public Gui(Game game)
	{
		super("Jeu");

		screenHeight = game.getHeight();
		screenWidth = game.getWidth();
		this.game = game;

		setContentPane(contentPane);

		initUIComponents();
		addEventHandlers();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initUIComponents()
	{
		DefaultTableModel model = (DefaultTableModel) screen.getModel();
		model.setRowCount(screenHeight);
		model.setColumnCount(screenWidth);

		int length = 16;

		for(Enumeration<TableColumn> columns = screen.getColumnModel().getColumns(); columns.hasMoreElements(); )
		{
			TableColumn column = columns.nextElement();

			column.setMinWidth(length);
			column.setMaxWidth(length);
			column.setPreferredWidth(length);
		}

		screen.setRowHeight(length);

		renderer = new CustomCellRenderer();
		screen.setDefaultRenderer(Object.class, renderer);
	}

	private void addEventHandlers()
	{
		quitButton.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
		pauseAndResumeButton.addActionListener(e ->
		{
			if(game.isPaused())
			{
				pauseAndResumeButton.setText("Pause");

				game.resume();
			}
			else
			{
				pauseAndResumeButton.setText("Reprendre");

				game.pause();
			}
		});
		stopButton.addActionListener(e ->
		{
			game.stop();
		});
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

		JDialog.setDefaultLookAndFeelDecorated(false);
	}

	private static class CustomCellRenderer extends DefaultTableCellRenderer
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
