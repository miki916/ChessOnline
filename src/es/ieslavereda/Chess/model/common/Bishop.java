package es.ieslavereda.Chess.model.common;

public class Bishop extends Pieza {

	public Bishop(Color color,Coordenada posicion, Tablero tablero) {
		super(posicion, tablero);
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_BISHOP;
		else
			tipo = Tipo.BLACK_BISHOP;
	}

	@Override
	public Lista<Coordenada> getNextMovements() {
		// TODO Auto-generated method stub
		return getNextMovements(this);
	}
	
	public static Lista<Coordenada> getNextMovements(Pieza p){
		
		Tablero t = p.tablero;
		Lista<Coordenada> lista = new Lista<>();
		Coordenada c;
		
		// UP 

		// Right
		
		// Down
		
		// Left
		
		
		return lista;
	}
}
