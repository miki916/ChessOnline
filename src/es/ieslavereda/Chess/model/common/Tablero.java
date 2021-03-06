package es.ieslavereda.Chess.model.common;

public class Tablero {

	private Celda[][] tablero;
	private Lista<Pieza> blancas;
	private Lista<Pieza> blancasEliminadas;
	private Lista<Pieza> negras;
	private Lista<Pieza> negrasEliminadas;
	private Pieza blackKing;
	private Pieza whiteKing;

	public Tablero() {
		super();
		tablero = new Celda[8][8];
		blancas = new Lista<>();
		blancasEliminadas = new Lista<>();
		negras = new Lista<>();
		negrasEliminadas = new Lista<>();
		inicializar();
	}

	private void inicializar() {

		// Inicializamos el tablero
		for (int fila = 0; fila < tablero.length; fila++) {
			for (int col = 0; col < tablero[0].length; col++)
				tablero[fila][col] = new Celda();
		}

		whiteKing = new King(Color.WHITE, new Coordenada('E', 1), this);
		blancas.addHead(whiteKing);

		blancas.addHead(new Rook(Color.WHITE, new Coordenada('A', 1), this));
		blancas.addHead(new Knight(Color.WHITE, new Coordenada('B', 1), this));
		blancas.addHead(new Bishop(Color.WHITE, new Coordenada('C', 1), this));
		blancas.addHead(new Queen(Color.WHITE, new Coordenada('D', 1), this));
		blancas.addHead(new Bishop(Color.WHITE, new Coordenada('F', 1), this));
		blancas.addHead(new Knight(Color.WHITE, new Coordenada('G', 1), this));
		blancas.addHead(new Rook(Color.WHITE, new Coordenada('H', 1), this));

		blackKing = new King(Color.BLACK, new Coordenada('E', 8), this);
		negras.addHead(blackKing);
		negras.addHead(new Rook(Color.BLACK, new Coordenada('A', 8), this));
		negras.addHead(new Knight(Color.BLACK, new Coordenada('B', 8), this));
		negras.addHead(new Bishop(Color.BLACK, new Coordenada('C', 8), this));
		negras.addHead(new Queen(Color.BLACK, new Coordenada('D', 8), this));
		negras.addHead(new King(Color.BLACK, new Coordenada('E', 8), this));
		negras.addHead(new Bishop(Color.BLACK, new Coordenada('F', 8), this));
		negras.addHead(new Knight(Color.BLACK, new Coordenada('G', 8), this));
		negras.addHead(new Rook(Color.BLACK, new Coordenada('H', 8), this));

		for (int i = 0; i < tablero.length; i++) {
			blancas.addHead(new Pawn(Color.WHITE, new Coordenada((char) ('A' + i), 2), this));
			negras.addHead(new Pawn(Color.BLACK, new Coordenada((char) ('A' + i), 7), this));
		}

	}

	public boolean contiene(Coordenada c) {
		return !(c.getRow() > 8 || c.getRow() < 1 || c.getColumn() < 'A' || c.getColumn() > 'H');
	}

	public Pieza getPiezaAt(Coordenada c) {
		if (!contiene(c))
			return null;
		else
			return getCeldaAt(c).getPieza();
	}

	public Lista<Pieza> getBlancas() {
		return blancas;
	}

	public void eliminarPieza(Pieza p) {

		if (p.getColor() == Color.WHITE) {
			blancasEliminadas.addHead(blancas.getAndRemove(p));
		} else
			negrasEliminadas.addHead(negras.getAndRemove(p));

	}

	public Celda getCeldaAt(Coordenada c) {

		if (contiene(c))
			return tablero[8 - c.getRow()][c.getColumn() - 'A'];

		return null;
	}

	public String print(Color color) {

		switch (color) {
		case WHITE:
			return printAsWhite();
		default:
			return printAsBlack();
		}
	}

	private String printAsBlack() {
		String salida = "           H   G   F   E   D   C   B   A\n";

		salida += obtenerParteSuperior();

		for (int fila = tablero.length - 1; fila > 0; fila--) {
			salida += obtenerParteFichaNegra(fila);
			salida += obtenerParteDivisoria();
		}
		salida += obtenerParteFichaNegra(0);
		salida += obtenerParteInferior() + "\n";
		salida += "           H   G   F   E   D   C   B   A\n";

		return salida;
	}

	private String printAsWhite() {
		String salida = "           A   B   C   D   E   F   G   H\n";

		salida += obtenerParteSuperior();

		for (int fila = 0; fila < tablero.length - 1; fila++) {
			salida += obtenerParteFichaBlanca(fila);
			salida += obtenerParteDivisoria();
		}
		salida += obtenerParteFichaBlanca(tablero.length - 1);
		salida += obtenerParteInferior() + "\n";
		salida += "           A   B   C   D   E   F   G   H\n";

		return salida;
	}

	private String obtenerParteSuperior() {

		return "         ╔═══╤═══╤═══╤═══╤═══╤═══╤═══╤═══╗\n";
	}

	private String obtenerParteFichaNegra(int fila) {
		// String I = "\u2502";
		String salida = "       " + (8 - fila) + " ║";

		for (int col = tablero[0].length - 1; col > 0; col--) {
			salida = salida + " " + tablero[fila][col] + " │";
		}

		salida = salida + " " + tablero[fila][0] + " ║ " + (8 - fila) + "\n";

		return salida;
	}

	private String obtenerParteFichaBlanca(int fila) {
		// String I = "\u2502";
		String salida = "       " + (8 - fila) + " ║";

		for (int col = 0; col < tablero[0].length - 1; col++) {
			salida = salida + " " + tablero[fila][col] + " │";
		}

		salida = salida + " " + tablero[fila][tablero[0].length - 1] + " ║ " + (8 - fila) + "\n";

		return salida;
	}

	private String obtenerParteDivisoria() {

		return "         ╟───┼───┼───┼───┼───┼───┼───┼───╢ \n";
	}

	private String obtenerParteInferior() {

		return "         ╚═══╧═══╧═══╧═══╧═══╧═══╧═══╧═══╝\n";
	}

	public boolean blackKingIsAlive() {
		return negras.contains(blackKing);
	}

	public boolean whiteKingIsAlive() {
		return blancas.contains(whiteKing);
	}

}
