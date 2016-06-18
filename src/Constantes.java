/*
 * @Interface: Constantes.java
 * @Author: Alexis Hernandez
 * @Version: 1.0
 * @Date: 30/04/2012
 * @Description: Interfas que contiene las constantes usadas en las demas clases
 *
**/


public interface Constantes	{
	/*
	 * Colores
	*/
	int BLACNO = 0xFFFFFF;
	int NEGRO = 0x000000;
	int ROJO = 0xFF0000;
	int VERDE = 0x00FF00;
	int AZUL = 0x0000FF;
	int MAGENTA = 0xFF00FF;
	int AMARILLO = 0xFFFF00;
	int CYAN = 0x00FFFF;
	int NARANJA = 0xFFC800;
	int ROSA = 0xFFAFAF;
	int GRIS_OBSCURO = 0x404040;
	int GRIS = 0x808080;
	int GRIS_CLARO = 0xC0C0C0;
	/*
	 * Arreglo de colores
	*/
	int [] COLORES = {
		BLACNO, NEGRO,
		ROJO, VERDE, AZUL,
		MAGENTA, AMARILLO, CYAN,
		NARANJA, ROSA,
		GRIS_OBSCURO, GRIS, GRIS_CLARO
	};
	/*
	 * Nombres de los colores en el arreglo
	*/
	String [] COLORES_STR = {
		"Blanco", "Negro",
		"Rojo", "Verde", "Azul",
		"Magenta", "Amarillo", "Cyan",
		"Naranja", "Rosa",
		"Gris obscuro", "Gris", "Gris claro"
	};
}