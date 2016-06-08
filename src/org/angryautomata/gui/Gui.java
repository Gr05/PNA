package org.angryautomata.gui;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.angryautomata.game.*;

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
	private JSeparator verticalSeparator1;
	private JTextArea info;
	private IntegerCellRenderer renderer;
	private JPopupMenu screenMenu = new JPopupMenu();
	private JMenuItem changeAction = new JMenuItem("Changer l'action");
	private JCheckBoxMenuItem showAutomata = new JCheckBoxMenuItem("Voir les automates");

	public Gui(Game game)
	{
		super("Jeu");

		this.game = game;
		screenHeight = game.getHeight();
		screenWidth = game.getWidth();
		model = new IntegerTableModel(screenHeight, screenWidth);

		game.setGui(this);

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
		info.setAutoscrolls(false);

		screen.setModel(model);
		screen.setCellSelectionEnabled(true);
		screen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		int length = 16;

		for(Enumeration<TableColumn> columns = screen.getColumnModel().getColumns(); columns.hasMoreElements(); )
		{
			TableColumn column = columns.nextElement();

			column.setMinWidth(length);
			column.setMaxWidth(length);
			column.setPreferredWidth(length);
		}

		screen.setRowHeight(length);

		renderer = new IntegerCellRenderer();
		screen.setDefaultRenderer(int.class, renderer);

		screenMenu.add(changeAction);
		screenMenu.add(showAutomata);
		screen.setComponentPopupMenu(screenMenu);
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
				screen.clearSelection();
				screenMenu.setEnabled(false);
				showAutomata.setSelected(false);

				game.resume();
			}
			else
			{
				pauseAndResumeButton.setText("Reprendre");
				screen.setEnabled(true);
				screenMenu.setEnabled(true);

				game.pause();
			}
		});
		stopButton.addActionListener(e -> game.stop());
		screenMenu.addPopupMenuListener(new PopupMenuListener()
		{
			public void popupMenuWillBecomeVisible(PopupMenuEvent e)
			{
				int[] selection = screen.getSelectedRows();
				boolean canInteract = screen.isEnabled();
				boolean hasSelection = selection != null && selection.length > 0;

				changeAction.setEnabled(canInteract && hasSelection);
				showAutomata.setEnabled(canInteract);
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
			{
			}

			public void popupMenuCanceled(PopupMenuEvent e)
			{
			}
		});
	}

	public void update(Board board, Map<Position, Color> highlights, Automaton[] automata)
	{
		SwingUtilities.invokeLater(() ->
		{
			StringBuilder builder = new StringBuilder("Nombre de tours : ").append(game.ticks()).append("\n\n");

			for(Automaton automaton : automata)
			{
				builder.append("Automate '").append(automaton.getName()).append("' :\n");

				int numOfPlayers = automaton.getPlayers().size();

				builder.append("Personnages : ").append(numOfPlayers).append("\n");

				if(numOfPlayers > 0)
				{
					StringBuilder temp = new StringBuilder();
					int totalGradient = 0;
					int count = 1;

					for(Player player : automaton.getPlayers())
					{
						temp.append("- Personnage n°").append(count).append(" :\n");
						temp.append("    Etat : ").append(player.getState()).append("\n");
						temp.append("    Symbole lu : ").append(board.getSceneryAt(game.getPosition(player)).getSymbol()).append("\n");
						temp.append("    Gradient : ").append(player.getGradient()).append("\n");

						totalGradient += player.getGradient();
						count++;
					}

					builder.append("Gradient total : ").append(totalGradient).append("\n");
					builder.append("Gradient moyen : ").append(totalGradient / numOfPlayers).append("\n");
					builder.append(temp).append("\n");
				}
				else
				{
					builder.append("\n");
				}
			}

			info.setText(builder.toString());

			renderer.setHighlight(highlights);

			for(int i = 0; i < screenHeight; i++)
			{
				for(int j = 0; j < screenWidth; j++)
				{
					screen.setValueAt(board.getSceneryAt(board.torusPos(j, i)).getSymbol(), i, j);
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

	private void createUIComponents()
	{
		verticalSeparator1 = new JSeparator(JSeparator.VERTICAL);
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
			return false;
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
							highColor = Color.CYAN.darker();

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
							highColor = Color.GREEN.darker().darker();

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
