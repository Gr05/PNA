package org.angryautomata.gui;

import javax.swing.*;

public class Gui extends JFrame
{
	private final JTextArea screen = new JTextArea();

	public Gui()
	{
		super("Jeu");

		add(screen);

		setSize(256, 256);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void update(String s)
	{
		screen.setText(s);
	}
}
