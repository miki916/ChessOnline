package es.ieslavereda.Chess.model.common;

public enum Tipo {
	
	WHITE_KING(Color.WHITE,"♔" ),
	WHITE_QUEEN(Color.WHITE,"♕" ),
	WHITE_ROOK(Color.WHITE,"♖" ),
	WHITE_KNIGHT(Color.WHITE,"♘" ),
	WHITE_BISHOP(Color.WHITE,"♗" ),
	WHITE_PAWN(Color.WHITE,"♙" ),
	BLACK_KING(Color.BLACK,"♚" ),
	BLACK_QUEEN(Color.BLACK,"♛" ),
	BLACK_ROOK(Color.BLACK,"♜" ),
	BLACK_KNIGHT(Color.BLACK,"♞" ),
	BLACK_BISHOP(Color.BLACK,"♝" ),
	BLACK_PAWN(Color.BLACK,"♟︎" )
	;
	
	private Color color;
	private String forma;
	
	Tipo(Color color, String forma){
		this.color = color;
		this.forma = forma;
	}

	public Color getColor() {
		return color;
	}

	public String getForma() {
		return forma;
	}
}
