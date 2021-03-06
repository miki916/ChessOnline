package es.ieslavereda.Chess;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import es.ieslavereda.Chess.model.common.Color;
import es.ieslavereda.Chess.model.common.Coordenada;
import es.ieslavereda.Chess.model.common.Pieza;
import es.ieslavereda.Chess.model.common.Player;
import es.ieslavereda.Chess.model.common.Tablero;
import es.ieslavereda.Chess.sockets.Message;

public class Game extends Thread implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idGame;
	private Player white;
	private Player black;
	private Tablero board;
	private ChessServer chessServer;

	public Game(ChessServer server) {
		super("GameThread");
		this.chessServer = server;
		this.idGame = server.getNewIdGame();
		board = new Tablero();
	}

	public Player getWhite() {
		return white;
	}

	public Player getBlack() {
		return black;
	}

	public void setWhite(Player white) {
		this.white = white;
	}

	public void setBlack(Player black) {
		this.black = black;
	}

	private String updateScreen(Player player) {

		return "\033[H\033[2J" + board.print(player.getColor());

	}

	public int getIdGame() {
		return idGame;
	}

	public void start() {

		sendInformation("Let's go.");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Color turn = Color.WHITE;

		sendInformation(white, updateScreen(white));
		sendInformation(black, updateScreen(black));

		do {
			switch (turn) {
			case WHITE:
				movePiece(white);
				break;
			case BLACK:
				movePiece(black);
				break;
			}
			sendInformation(black, updateScreen(black));
			sendInformation(white, updateScreen(white));

			turn = Color.values()[(turn.ordinal() + 1) % Color.values().length];

		} while (board.blackKingIsAlive()  && board.whiteKingIsAlive()  && black.getSocket().isConnected()
				&& white.getSocket().isConnected());

		if (board.whiteKingIsAlive())
			sendInformation(Color.WHITE + " wins.");
		else
			sendInformation(Color.BLACK + " wins.");

		try {
			white.getSocket().close();
			black.getSocket().close();
			chessServer.gameFinish(idGame);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void movePiece(Player player) {

		Coordenada c;
		Pieza p;

		sendInformation("Move -> " + player.getColor() + "  (" + player.getName() + ").");
		sendInformation((player.getColor() == Color.WHITE) ? black : white,"Wait for the other player to finish moving...");

		boolean moved = false;

		do {
			
			c = requestCoordinate(player); 
			
			if(board.getPiezaAt(c) != null) {
				
				p =  board.getPiezaAt(c);
								
				if(board.contiene(c) && p.getColor() == player.getColor())	{
					
					System.out.println(board.print(player.getColor()));
					
					System.out.println("Movimientos posibles de " + p + ": ");
					System.out.println(p.getNextMovements());
					
					if(!p.getNextMovements().isEmpty()) {
					
						System.out.println("Donde quieres mover? ");
						c =  requestCoordinate(player); 
						
						if(p.getNextMovements().contains(c)) {
						
							if(board.getPiezaAt(c) != null) {
								
								System.out.println("Ficha eliminada: " + board.getPiezaAt(c));
								p.moveTo(c);	
								moved = true;
								System.out.println("Ficha movida");
								
							}else {
								
								p.moveTo(c);
								moved = true;
								System.out.println("Ficha movida");
								
							}
						}				
					}else
						
						System.out.println("No tienes movimientos posibles");
				}else
					
					System.out.println("No existe esa coordenada o estas eligiendo el color equivocado");

			}else System.out.println("Esta vacio");

		} while (!moved);

	}

	public Coordenada requestCoordinate(Player player) {
		
		Coordenada c = null;

		char col;
		int fil;
		
		
		try {
			
			// To do 
			// Send to the player the request coordinate message and wait for the response.
			// Check if the answer is a correct message, and return the coordinate received
		
			sendInformation(player,"Introduce la letra de la coord");
			col = (char) player.getOis().read();		
			
			sendInformation(player,"Introduce el numero de la coord");
			fil = player.getOis().read();	
			
			c = new Coordenada(col,fil);
			

		} catch (Exception e) {

			e.printStackTrace();
		}

		return c;
	}

	private void sendInformation(String information) {

		sendInformation(white, information);
		sendInformation(black, information);

	}

	public void sendInformation(Player player, String information) {
		
		// To do
		// Send to the player the information
		
		try {
			
			player.getOos().writeObject(information);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String toString() {
	
		
		
		return "[Negras: " + black.getName() + "Blancas: " + white.getName() + "]";
		
	}
}
