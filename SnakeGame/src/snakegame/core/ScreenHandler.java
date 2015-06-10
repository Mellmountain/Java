package snakegame.core;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScreenHandler {

	JPanel _screens;
	CardLayout _screenLayout;
	JFrame _window;
	
	public ScreenHandler(int width, int height)
	{
		_screens = new JPanel();
		_screenLayout = new CardLayout();
		_screens.setLayout(_screenLayout);
		_window = new JFrame("Snake Game");
		_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		_window.setResizable(false);
		_window.setSize(width, height);
		_window.add(_screens);
	}
	
	public void addScreen(JPanel screen, String desc)
	{
		_screens.add(screen, desc);
	}
	
	public void showScreen(String desc)
	{
		_screenLayout.show(_screens, desc);
	}
	
	public void showWindow()
	{
		_window.setVisible(true);
	}
	
}
