package es.ieslavereda.Chess.model.common;

public abstract class Pieza {

	protected Tipo tipo;
	protected Tablero tablero;
	protected Coordenada posicion;
	
	public Pieza(Coordenada posicion, Tablero tablero) {
		super();
		this.posicion = posicion;
		this.tablero = tablero;
		colocate(posicion);
	}
	
	private void colocate(Coordenada c) {
		
		tablero.getCeldaAt(posicion).setPieza(null);
		posicion = c;
		tablero.getCeldaAt(posicion).setPieza(this);
		
	}
	
	public void moveTo(Coordenada c) {
		
		if(tablero.getPiezaAt(c)==null) {
			colocate(c);
		} else {
			tablero.eliminarPieza(tablero.getPiezaAt(c));
			colocate(c);
		}
	}
	public boolean canMoveTo(Coordenada c) {
		return this.getNextMovements().contains(c);
	}

	public Color getColor() {
		return tipo.getColor();
	}
	
	@Override
	public String toString() {
		return tipo.getForma();
	}
	
	public abstract Lista<Coordenada> getNextMovements();
}











