package es.ieslavereda.Chess.model.common;

public class Coordenada {

	private int row;
	private char column;
	

	public Coordenada( char column,int row) {
		super();
		this.row = row;
		this.column = column;
		
	}

	public Coordenada up() {
		return new Coordenada( column,row + 1);
	}

	public Coordenada down() {
		return new Coordenada( column,row - 1);
	}

	public Coordenada left() {
		return new Coordenada((char) (column - 1),row);
	}

	public Coordenada right() {
		return new Coordenada( (char) (column + 1),row);
	}
	
	public Coordenada diagonalUpLeft() {
		return this.up().left();
	}
	
	public Coordenada diagonalUpRight() {
		return up().right();
	}
	
	public Coordenada diagonalDownRight() {
		return down().right();
	}
	
	public Coordenada diagonalDownLeft() {
		return down().left();
	}
	
	
	
	public int getRow() {
		return row;
	}

	public char getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return String.valueOf(column) + row;
	}
}
