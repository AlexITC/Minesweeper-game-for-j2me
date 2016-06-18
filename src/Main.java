

import com.alx.lcdui.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class Main	extends	MIDlet	{
	//
	private Display display;
	private Panel panel;
	private MainMenu mainMenu;
	private SettingsMenu settingsMenu;
	private LevelMenu levelMenu;
	private Help help;
	//
	public Main()	{
		display = Display.getDisplay(this);
		mainMenu = new MainMenu(this);
		settingsMenu = new SettingsMenu(this);
		levelMenu = new LevelMenu(this);
		panel = new Panel(this);
		help = new Help(this);
	}
	//
	protected void startApp()	{
		showMenu();
	}
	protected void pauseApp()	{}
	protected void destroyApp(boolean force)	{	notifyDestroyed();	}
	//
	/*
	*/
	
	/**
	 * Identificador para el estado actual del juego
	 * 0 - no inicializado
	 * 1 - inicializado sin movimientos
	 * 2 - pausado
	 * 3 - finalizado y ganado
	 * 4 - finalizado y perdido
	**/
	public int CURRENT_STATE;
	public static final int STATE_OFF = 0;
	public static final int STATE_INIT = 1;
	public static final int STATE_PLAYING = 2;
	public static final int STATE_WIN = 3;
	public static final int STATE_LOST = 4;
	//
	public void showMenu()	{
		int index = 0;
		Displayable d = display.getCurrent();
		if	( d==help )	index = 2;
		mainMenu.show(display);
		mainMenu.setFocusedItemIndex(index);
	}
	public void showGame()	{	panel.show(display);	}
	public void showSettings()	{	settingsMenu.show(display);	}
	public void showHelp()	{	display.setCurrent(help);	}
	public void showAbout()	{	display.setCurrent(help);	}
	public void showLevel()	{	levelMenu.show(display);	}
	
	public void sound()	{}
	public void setLevel(int n)	{	panel.MINAS_PERCENT = n * 20;	}
	public int getLevel()	{	return	(panel.MINAS_PERCENT / 20);	}
	/*
	*/
}