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

public class LevelMenu	extends	ListMenu	implements	SoftkeyListener	{
	/**
	 * Apuntador al midlet
	**/
	private static Main midlet;
	
	public final Softkey CMD_OK		= new Softkey("Ok",Softkey.LEFT,3);
	public final Softkey CMD_BACK	= new Softkey("Back",Softkey.RIGHT,4);
	public final Softkey CMD_CENTER	= new Softkey("Accept",Softkey.CENTER,5);
	private String [] menuItems = {
		"Easy",
		"Medium",
		"Hard"
	};
	
	public LevelMenu(Main m)	{
		super("Level");
		midlet = m;
		addSoftkey(CMD_OK);
		addSoftkey(CMD_BACK);
		addSoftkey(CMD_CENTER);
		addSoftkeyListener(this);
		for	(int i=0;	i<3;	i++)	append( menuItems[i] );
	}
	public void show(Display d)	{
		super.show(d);
		setFocusedItemIndex( midlet.getLevel() - 1 );
	}
	
	public void softkeyAction(Softkey sk, Frame f)	{
		if	( sk != CMD_BACK )	midlet.setLevel( 1 + getFocusedItemIndex() );
		midlet.showSettings();
	}
}


