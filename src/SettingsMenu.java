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

public class SettingsMenu	extends	ListMenu	implements	SoftkeyListener	{
	/**
	 * Apuntador al midlet
	**/
	private static Main midlet;
	
	public final Softkey CMD_OK		= new Softkey("Ok",Softkey.LEFT,3);
	public final Softkey CMD_BACK	= new Softkey("Back",Softkey.RIGHT,4);
	public final Softkey CMD_CENTER	= new Softkey("Accept",Softkey.CENTER,5);
	private String [] menuItems = {
		"Level",
		"Sound"
	};
	
	public SettingsMenu(Main m)	{
		super("Settings");
		midlet = m;
		addSoftkey(CMD_OK);
		addSoftkey(CMD_BACK);
		addSoftkey(CMD_CENTER);
		addSoftkeyListener(this);
		for	(int i=0;	i<2;	i++)	append( menuItems[i] );
	}
	
	
	public void softkeyAction(Softkey sk, Frame f)	{
		if	( sk == CMD_BACK )	midlet.showMenu();
		else	selectItem( getFocusedItemIndex() );
	}
	
	public void selectItem(int item)	{
		switch	(item)	{
			case 0:	midlet.showLevel();	break;
			case 1:	midlet.sound();	break;
		}	
	}
}


