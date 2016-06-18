
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import java.io.*;

public class Help	extends	Form	implements CommandListener	{
	//
	private static final Command CMD_OK = new Command("Ok", Command.ITEM, 1);
	//
	
	/**
	 * Apuntador al midlet
	**/
	private static Main midlet;
	
	public Help(Main m)	{
		super("Help");
		midlet = m;
		addCommand(CMD_OK);
		setCommandListener(this);
		try	{
		InputStream is = getClass().getResourceAsStream("/help.txt");
		byte b [] = new byte [ is.available() ];
		is.read(b);
		append( new String(b) );
		}	catch(Exception e)	{}
	}
	public void commandAction(Command c, Displayable item) {
		midlet.showMenu();
	}
}