/*
 * @Class: Panel.java
 * @Author: Alexis Hernandez
 * @Version: 2.0
 * @Date: 28/04/2012
 * @Description: 
 *
**/

import com.alx.lcdui.*;
import javax.microedition.lcdui.*;
import java.util.Random;

public class Panel	extends	Frame	implements	SoftkeyListener	{
	/**
	 * Apuntador a objeto para generar numeros aleatorios
	**/
	private static Random RNG;
	/**
	 * Apuntador al midlet
	**/
	private static Main midlet;
	/**
	 * Colores en las casillas
	**/
	private static int FONT_COLOR [] = {
		Constantes.NEGRO,	//0
		0x000080, 0x008000, 0x800000,	//1,2,3 (AZUL, VERDE, ROJO) obscuro
		Constantes.AZUL, Constantes.VERDE, Constantes.ROJO,	//4,5,6 (AZUL, VERDE, ROJO) fuerte
		Constantes.AMARILLO, 0x80FF80	//7,8 (AMARILLO, GRIS)
	};
	/**
	 * Colores en el panel
	**/
	private static int BACKGROUND_COLOR = Constantes.GRIS_CLARO;
	private static int LINE_COLOR = Constantes.GRIS;
	private static int FRAME_COLOR = Constantes.NEGRO;
	private static int CURSOR_COLOR = Constantes.ROJO;
	/**
	 * Matriz que contiene las casillas
	**/
	private static Casilla [][] PANEL;
	/**
	 * Porcentage y total de minas en el panel
	**/
	public static int MINAS_PERCENT = 20;
	private static int N_MINAS;
	private static int N_CASILLAS;
	/**
	 * Dimensiones de los bloques en pixeles
	**/
	private static int BLOCK_SIZE_X = 18;
	private static int BLOCK_SIZE_Y = 18;
	/**
	 * Posicion de las casilla iluminada por el cursor
	**/
	private static int currentX;
	private static int currentY;
	/**
	 * Iconos
	**/
	private static Image mina, bandera, boton, mina_roja;
	
	public final Softkey CMD_NEW	= new Softkey("New",Softkey.LEFT,1);
	public final Softkey CMD_BACK	= new Softkey("Back",Softkey.RIGHT,2);
	/**
	 * Constructor que recive un apuntador midlet
	**/
	public Panel(Main m)	{
		super("Buscando Minas",true);
		midlet = m;
		RNG = new Random();
		addSoftkey(CMD_NEW);
		addSoftkey(CMD_BACK);
		addSoftkeyListener(this);
		start();
	}
	public void softkeyAction(Softkey sk, Frame f)	{
		if	(sk==CMD_BACK)	midlet.showMenu();
		else if	(sk==CMD_NEW)	{
			start();
		}
	}
	/**
	 * Inicia un nuevo juego
	**/
	public void start()	{
	//	currentX = currentY = 0;
		//Crear matriz
		if	( PANEL==null )	PANEL = new Casilla [getHeight() / BLOCK_SIZE_Y] [getWidth() / BLOCK_SIZE_X];
		for	(byte ren=0;	ren<PANEL.length;	ren++)	{
			for	(byte col=0;	col<PANEL[ren].length;	col++)
				if	( PANEL[ren][col] == null )
					PANEL[ren][col] = new Casilla();
				else	{
					PANEL[ren][col].visible = false;
					PANEL[ren][col].locked = false;
					PANEL[ren][col].valor = 0;
				}
		}
		//cargar iconos
		try	{
			if	( bandera==null )	bandera = Image.createImage(getClass().getResourceAsStream("/flag.PNG"));
			if	( boton==null )	boton = Image.createImage(getClass().getResourceAsStream("/boton.PNG"));
			if	( mina==null )	mina = Image.createImage("/mina.PNG");
			if	( mina_roja==null )	mina_roja = Image.createImage("/mina_roja.PNG");
		}	catch (Exception e)	{	System.out.println("Error al cargar iconos");	return;	}
		//igualar colores de la interfaz
		setSoftkeyBackground(BACKGROUND_COLOR);
		setTitleBackground(BACKGROUND_COLOR);
		setSoftkeyForeground(FRAME_COLOR);
		setTitleForeground(FRAME_COLOR);
		setSoftkeyShadow(LINE_COLOR);
		//
		midlet.CURRENT_STATE = midlet.STATE_INIT;
		repaint();
	}
	

	/*
	 * Coloca las minas en la matriz aleatoriamente excepto en la posicion dada
	**/
	private void colocaMinas(int y, int x)	{
		N_MINAS = ( (PANEL.length * PANEL[0].length * MINAS_PERCENT) / 100 );
		N_CASILLAS = ( (PANEL.length * PANEL[0].length) - N_MINAS );
		int i=0, ren, col;
		while	(i<N_MINAS)	{
			ren = RNG.nextInt(PANEL.length);
			col = RNG.nextInt(PANEL[0].length);
			if	( (ren==y	&&	col==x)	||	PANEL[ren][col].valor == -1 )	continue;
			PANEL[ren][col].valor = -1;
			i++;
		}
		//Coloca el numero de minas cerca de cada elemento
		for	(ren=0;	ren<PANEL.length;	ren++)
			for	(col=0;	col<PANEL[ren].length;	col++)	{
				if	( PANEL[ren][col].valor == -1 )	continue;
				PANEL[ren][col].valor = minasCerca(ren,col);
			}
	}
	/*
	 * Calcula el numero de minas que estan cerca de la casilla dada
	**/
	private byte minasCerca(int ren, int col)	{
		int [] rPos = { ren-1,ren-1,ren-1,ren,ren,ren+1,ren+1,ren+1 };
		int [] cPos = { col-1,col,col+1,col-1,col+1,col-1,col,col+1 };
		byte n = 0;
		for	(byte i=0;	i<8;	i++)
			try	{
				if	( PANEL[rPos[i]] [cPos[i]].valor == -1 )
					++n;
			}	catch(Exception e)	{}
		return n;
	}
	/*
	 * Destapar la casilla dada
	**/
	private void destapar(int ren, int col)	{
		if	( PANEL[ren][col].visible )	return;	//Si ya esta visible
		if	( PANEL[ren][col].valor == -1 )	{	boom();	return;	}	//Si es una mina
		PANEL[ren][col].visible = true;
		PANEL[ren][col].locked = false;
		if	( --N_CASILLAS == 0 )	{	win();	return;	}	//Si es la u,ltima casilla
		if	( PANEL[ren][col].valor == 0 )	destaparCerca(ren,col);
	}
	/*
	 * Destapar las casillas que esten cerca de la casilla dada
	**/
	private void destaparCerca(int ren, int col)	{
		int [] rPos = { ren-1,ren-1,ren-1,ren,ren,ren+1,ren+1,ren+1 };
		int [] cPos = { col-1,col,col+1,col-1,col+1,col-1,col,col+1 };
		//
		for	(byte i=0;	i<8;	i++)	{
			try	{
				if	( PANEL[rPos[i]][cPos[i]].visible )	continue;
				destapar( rPos[i], cPos[i] );
			}	catch(Exception e)	{}
		}
	}
	/*
	 * Al ganar el juego
	**/
	private void win()	{
		midlet.CURRENT_STATE = midlet.STATE_WIN;
		AlertType.INFO.playSound( Display.getDisplay(midlet) );
		repaint();
	}
	/*
	 * Al encontrar una mina
	**/
	private void boom()	{
		midlet.CURRENT_STATE = midlet.STATE_LOST;
		AlertType.ERROR.playSound(Display.getDisplay(midlet));
		Display.getDisplay(midlet).vibrate(500);
		repaint();
	}
	/*
	 * Dibuja la imagen en todas las casillas que contentan minas
	**/
	private void drawMinas(Image img)	{
		for	(byte ren=0;	ren<PANEL.length;	ren++)
			for	(byte col=0;	col<PANEL[ren].length;	col++)
				if	( PANEL[ren][col].valor == -1 )
					drawImage(img,ren,col);
	
	}

	/*
	 * Dibuja todo el panel
	**/
	private static Graphics GRAP = null;
	public void paintMe(Graphics g) 	{
	//	Graphics g = getGraphics();
		if	(GRAP != g)	GRAP = g;
		int x1, y1;
		//Pintar fondo
		g.setColor(BACKGROUND_COLOR);
		g.fillRect( 1, 1, getWidth()-1, getHeight()-1 );
		//Pintar marco
		g.setColor(FRAME_COLOR);
		g.drawRect( 0, 0, getWidth(), getHeight() );
		//Pintar casillas
		for	(int ren=0;	ren<PANEL.length;	ren++)	{
			for	(int col=0;	col<PANEL[ren].length;	col++)	{
				//Si la casilla esta visible
				if	( PANEL[ren][col].visible )	{
					//pintar bloque
					g.setColor(LINE_COLOR);
					x1 = 1 + (BLOCK_SIZE_X * col);
					y1 = 1 + (BLOCK_SIZE_Y * ren);
					g.drawRect( x1, y1, BLOCK_SIZE_X, BLOCK_SIZE_Y );
					//Pintar numero si la casilla es mayor a 0
					if	( PANEL[ren][col].valor == 0 )	continue;
					g.setColor( FONT_COLOR[ PANEL[ren][col].valor ] );
					g.drawString(
						String.valueOf( PANEL[ren][col].valor),
						x1 + (BLOCK_SIZE_X >> 1), y1,
						g.TOP | g.HCENTER
					);	continue;
				}
				//Si esta bloqueada, pintar bandera
				if	( PANEL[ren][col].locked )	drawImage(bandera,ren,col);
				//Si no esta visible, pintar boton
				else if	( !PANEL[ren][col].visible )	drawImage(boton,ren,col);
			}
		}
		//Si se esta jugando, dibujar cursor
		if	( midlet.CURRENT_STATE==midlet.STATE_PLAYING	||	midlet.CURRENT_STATE==midlet.STATE_INIT )	{
			//Pintar cursor
			g.setColor(CURSOR_COLOR);
			x1 = ( 1 + (BLOCK_SIZE_X * currentX) );
			y1 = ( 1 + (BLOCK_SIZE_Y * currentY) );
			g.drawRect( x1, y1, BLOCK_SIZE_X, BLOCK_SIZE_Y );
		}
		//
		if	( midlet.CURRENT_STATE==midlet.STATE_WIN )	{
			drawMinas(bandera);
			drawState("You Won!");
		}
		//
		else if	( midlet.CURRENT_STATE==midlet.STATE_LOST )	{
			drawMinas(mina);
			drawImage(mina_roja,currentY,currentX);
			drawState("You Lost!");
		}
		
	}
	/*
	 * Dibuja la imagen en la posicion dada
	**/
	private void drawImage(Image img, int ren, int col)	{
		if	( img == null )	return;
		int x1 = ( 1 + (BLOCK_SIZE_X * col) );
		int y1 = ( 1 + (BLOCK_SIZE_Y * ren) + (BLOCK_SIZE_Y >> 1) );
		GRAP.drawImage(
			img,
			x1 + (BLOCK_SIZE_X >> 1), y1,
			GRAP.HCENTER | GRAP.VCENTER
		);
	}
	
	private void drawState(String s)	{
		Font f = GRAP.getFont();
		GRAP.setColor( 148,255,208 );
		GRAP.setFont( Font.getFont( f.FACE_PROPORTIONAL, f.STYLE_BOLD, f.SIZE_LARGE ) );
		int x1 = getWidth() >> 1;
		int y1 = getHeight() >> 1;
		GRAP.drawString(
			s, x1, y1, GRAP.BASELINE | GRAP.HCENTER
		);
		GRAP.setFont(f);
	}
	
/*	private void repaint(int ren, int col)	{
		int x1 = ( 1 + (BLOCK_SIZE_X * col) );
		int y1 = ( 1 + (BLOCK_SIZE_Y * ren) + (BLOCK_SIZE_Y >> 1) );
		repaint( x1 - 10, y1 - 10, 30 + BLOCK_SIZE_X, 20 + BLOCK_SIZE_Y );
	}*/

	//
	
	/*
	 * Al presionar una tecla
	**/
	public void keyRepeated(int keyCode)	{
		super.keyRepeated(keyCode);
	//	if	( keyCode == 42	||	keyCode == 35 )	return;
		keyPressed(keyCode);
	}
	public void keyPressed(int keyCode)	{
		super.keyPressed(keyCode);
	//	System.out.println("Se presiono una tecla " + keyCode);
		if	( midlet.CURRENT_STATE!=midlet.STATE_PLAYING	&&	midlet.CURRENT_STATE!=midlet.STATE_INIT )
			return;
		if	( keyCode==-8	||	keyCode==48 )		{	//KEY_CLEAR
			lock();
		}	else switch	( getGameAction(keyCode) )	{
			//arriva
			case KEY_NUM2:
			case UP:	{
				if	( currentY == 0 )	currentY = (byte)(PANEL.length - 1);
				else	currentY --;
				break;
			}	//abajo
			case KEY_NUM8:
			case DOWN:	{
				if	( 1 + currentY == PANEL.length )	currentY = 0;
				else	currentY ++;
				break;
			}	//izquierda
			case KEY_NUM4:
			case LEFT:	{
				if	( currentX == 0 )	currentX = (byte)(PANEL[currentY].length - 1);
				else	currentX --;
				break;
			}	//Derecha
			case KEY_NUM6:
			case RIGHT:	{
				if	( 1 + currentX == PANEL[currentY].length )	currentX = 0;
				else	currentX ++;
				break;
			}	//Centro
			case KEY_NUM5:
			case FIRE:	{
				//Si es la primera casilla en destapar, se colocal las minas
				if	(midlet.CURRENT_STATE==midlet.STATE_INIT)	{
					midlet.CURRENT_STATE = midlet.STATE_PLAYING;
					colocaMinas(currentY,currentX);
				}	//Si no esta visible ni bloqueada
				if	( !PANEL[currentY][currentX].visible	&&	!PANEL[currentY][currentX].locked )
					destapar(currentY,currentX);
				break;
			}	default:	return;
		}	repaint();
	}
	/*
	** Bloquea/Desbloquea la casilla
	*/
	private void lock()	{
		//Si no esta visible
		if	( !PANEL[currentY][currentX].visible )
			PANEL[currentY][currentX].locked = !PANEL[currentY][currentX].locked;
	}
}







