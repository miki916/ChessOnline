package es.ieslavereda.Chess.model.common;

public class Queen extends Pieza{

	public Queen(Color color, Coordenada posicion, Tablero tablero) {
		super(posicion, tablero);
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_QUEEN;
		else
			tipo = Tipo.BLACK_QUEEN;
	}

	@Override
	public Lista<Coordenada> getNextMovements() {
		
		Lista<Coordenada> lista = Rook.getNextMovements(this);
		
		lista.addAll(Bishop.getNextMovements(this));
		
		return lista;
	}
	
	

}
