/*
 * @Class: Panel.java
 * @Author: Alexis Hernandez
 * @Version: 2.0
 * @Date: 28/04/2012
 * @Description: 
 * 		Lista simple en pantalla completa con opcion 'ok' y 'atras'
 *
**/

import com.alx.lcdui.*;
import javax.microedition.lcdui.*;

public class MainMenu	extends	ListMenu	implements	SoftkeyListener	{
	/**
	 * Apuntador al midlet
	**/
	private static Main midlet;
	
	public final Softkey CMD_OK		= new Softkey("Ok",Softkey.LEFT,3);
	public final Softkey CMD_EXIT	= new Softkey("Exit",Softkey.RIGHT,4);
	public final Softkey CMD_CENTER		= new Softkey("Accept",Softkey.CENTER,5);
	private String [] menuItems = {
		"Game",
		"Settings",
		"Help",
		"About",
		"Exit"
	};
	
	public MainMenu(Main m)	{
		super("Mine Sweeper");
		midlet = m;
		addSoftkey(CMD_OK);
		addSoftkey(CMD_EXIT);
		addSoftkey(CMD_CENTER);
		addSoftkeyListener(this);
		for	(int i=0;	i<5;	i++)	append( menuItems[i] );
	}
	
	
	public void softkeyAction(Softkey sk, Frame f)	{
		if	( sk == CMD_EXIT )	midlet.notifyDestroyed();
		selectItem( getFocusedItemIndex() );
	}
	
	public void selectItem(int item)	{
		switch	(item)	{
			case 0:	midlet.showGame();	break;
			case 1:	midlet.showSettings();	break;
			case 2:	midlet.showHelp();	break;
			case 3:	midlet.showAbout();	break;
			case 4:	midlet.notifyDestroyed();
		}	
	}
}


