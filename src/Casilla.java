/*
 * @Class: Casilla.java
 * @Author: Alexis Hernandez
 * @Version: 1.1
 * @Date: 03/04/2012
 * @Description: Clase dedicada a almacenar las minas que estan cerca y si es visible
 *
**/
public class Casilla	{
		public boolean visible = false;	//la casilla esta destapada
		public boolean locked = false;	//la casilla esta bloqueada
		public byte valor;	//Valor de la casilla (Minas cerca o mina)
}