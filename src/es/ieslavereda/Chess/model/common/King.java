package es.ieslavereda.Chess.model.common;

public class King extends Pieza {

	public King(Color color, Coordenada posicion, Tablero tablero) {
		super(posicion, tablero);
		if (color == Color.WHITE)
			tipo = Tipo.WHITE_KING;
		else
			tipo = Tipo.BLACK_KING;
	}

	@Override
	public Lista<Coordenada> getNextMovements() {

		Lista<Coordenada> lista = new Lista<Coordenada>();

		addCoordenada(posicion.up(), lista);
		addCoordenada(posicion.right(), lista);
		addCoordenada(posicion.down(), lista);
		addCoordenada(posicion.left(), lista);
		addCoordenada(posicion.diagonalUpRight(), lista);
		addCoordenada(posicion.diagonalUpLeft(), lista);
		addCoordenada(posicion.diagonalDownRight(), lista);
		addCoordenada(posicion.diagonalDownLeft(), lista);

		return lista;
	}

	private void addCoordenada(Coordenada c, Lista<Coordenada> lista) {
		if (tablero.contiene(c)) {
			if (tablero.getCeldaAt(c).contienePieza()) {
				if (tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					lista.addHead(c);
			} else {
				lista.addHead(c);
			}
		}
	}

}
