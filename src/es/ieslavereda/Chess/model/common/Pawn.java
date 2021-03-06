package es.ieslavereda.Chess.model.common;

public class Pawn extends Pieza {

	public Pawn(Color color, Coordenada posicion, Tablero tablero) {
		super(posicion, tablero);
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_PAWN;
		else
			tipo = Tipo.BLACK_PAWN;
		
	}

	@Override
	public Lista<Coordenada> getNextMovements() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void moveTo(Coordenada c) {
		super.moveTo(c);
		
		if(getColor()==Color.WHITE && posicion.getRow()==8) {
			tablero.eliminarPieza(this);
			tablero.getBlancas().addHead(new Queen(Color.WHITE,c,tablero));
		} else if (getColor()==Color.BLACK && posicion.getRow()==8){
			
		}
		
	}

}
