package snakegame.screens;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GameOverScreen extends JPanel{

	public GameOverScreen()
	{
		super();
	}
	
	public void paintComponent(Graphics g)
	{	
		int width = getWidth();
		int height = getHeight();
		super.paintComponent(g);
		
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("GAME OVER", width / 2, 250);
	}
	
	private static final long serialVersionUID = 1L;

}
