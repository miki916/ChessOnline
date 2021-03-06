package es.ieslavereda.Chess.test;

import es.ieslavereda.Chess.model.common.Color;
import es.ieslavereda.Chess.model.common.Coordenada;
import es.ieslavereda.Chess.model.common.Tablero;

public class Test {

	public static void main(String[] args) {
		Coordenada c = new Coordenada('E',3);
		
		Tablero t = new Tablero();
	
		System.out.println(t.print(Color.WHITE));
		
		System.out.println(t.getCeldaAt(c).getPieza().getNextMovements());
		//System.out.println(t.print(Color.BLACK));
	}

}
