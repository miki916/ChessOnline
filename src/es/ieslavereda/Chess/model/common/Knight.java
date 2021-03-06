package es.ieslavereda.Chess.model.common;

public class Knight extends Pieza{

	public Knight(Color color, Coordenada posicion, Tablero tablero) {
		super(posicion, tablero);
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_KNIGHT;
		else
			tipo = Tipo.BLACK_KNIGHT;
		
	}

	@Override
	public Lista<Coordenada> getNextMovements() {
		// TODO Auto-generated method stub
		return null;
	}

}
