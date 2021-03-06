package es.ieslavereda.Chess.model.common;

public class Rook extends Pieza {

	public Rook(Color color, Coordenada posicion, Tablero tablero) {
		super(posicion, tablero);
		// TODO Auto-generated constructor stub
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_ROOK;
		else
			tipo = Tipo.BLACK_ROOK;
		
	}

	@Override
	public Lista<Coordenada> getNextMovements() {
		
		return getNextMovements(this);
	}
	
	public static Lista<Coordenada> getNextMovements(Pieza p){
		
		Tablero t = p.tablero;
		Lista<Coordenada> lista = new Lista<>();
		Coordenada c;
		
		// UP 
		c= p.posicion.up();
		while(t.contiene(c) && t.getPiezaAt(c)==null) {
			lista.addHead(c);
			c=c.up();
		}
		if(t.contiene(c) && t.getPiezaAt(c).getColor() == p.getColor()) 
			lista.addHead(c);
		
		// Right
		
		// Down
		
		// Left
		
		
		return lista;
	}

}
