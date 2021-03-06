package es.ieslavereda.Chess.sockets;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import es.ieslavereda.Chess.ChessServer;
import es.ieslavereda.Chess.Game;
import es.ieslavereda.Chess.model.common.Color;
import es.ieslavereda.Chess.model.common.Player;

public class GestionMensajes {

	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ChessServer chessServer;
	private Game game;

	public GestionMensajes(Socket socket, ObjectOutputStream oos, ObjectInputStream ois, ChessServer chessServer) {

		this.socket = socket;
		this.oos = oos;
		this.ois = ois;
		this.chessServer = chessServer;

	}

	public Message procesInput(Message m) {
		Message out = null;

		switch (m.getMessageType()) {

		case CREATE_GAME:
			out = createGame(m);
			break;
		case ADD_TO_GAME:
			out = addToGame(m);
			break;
		case GET_CREATED_GAMES:
			out = getCreatedGames(m);
			break;
		default:
			System.out.println("Tipo de mensaje desconocido" + m);
			break;
		}

		return out;
	}


	private Message createGame(Message m) {
		
		Message mOut;
		Player player;

		if (m.getPlayer() == null) {
			// No se deberia de producir
			mOut = new Message(Message.Type.ASKING_FOR_PLAYER, "Asking for player");
			mOut.setGameId(game.getIdGame());

		} else {

			game = new Game(chessServer);

			player = m.getPlayer();
			player.setSocket(socket);
			m.getPlayer().setOos(oos);
			m.getPlayer().setOis(ois);

			if (player.getColor() == Color.WHITE)
				game.setWhite(player);
			else
				game.setBlack(player);

			chessServer.addGameToGamesWating(game);
			mOut = new Message(Message.Type.GAME_CREATED_WAITING,
					"Game created as id " + game.getIdGame() + ".\n" + "Wait to start playing...");
			mOut.setGameId(game.getIdGame());
		}

		return mOut;
	}
	
	private Message addToGame(Message m) {
		Game game;
		Message mOut = null;
		
		System.out.println(true);
		game = chessServer.addGameToGamesPlaying(m.getGameId());
		
		if (game != null) {
		
			Player player = m.getPlayer();

			player.setSocket(socket);
			player.setOos(oos);
			player.setOis(ois);

			if (m.getPlayer().getColor() == Color.BLACK && game.getBlack() == null) {
				game.setBlack(player);
				mOut = new Message(Message.Type.ADDED_TO_GAME, "Added to the game " + m.getGameId());
				mOut.setGameId(m.getGameId());
			} else if (m.getPlayer().getColor() == Color.WHITE && game.getWhite() == null) {
				game.setWhite(player);
				mOut = new Message(Message.Type.ADDED_TO_GAME, "Added to the game " + m.getGameId());
				mOut.setGameId(m.getGameId());
			} else {
				mOut = new Message(Message.Type.NOT_ADDED,
						"Not added to the game " + m.getGameId() + " , the game has already started.");
			}

		} else {
			mOut = new Message(Message.Type.NOT_ADDED,
					"Not added to the game " + m.getGameId() + " , the game has already started.");
		}

		return mOut;
	}
	
	private Message getCreatedGames(Message m) {
		
		Message mOut = new Message(Message.Type.LIST_GAMES, "List of games");
		mOut.setListOfGames(listGames());
		
		return mOut;
	}
	
	private TreeMap<Integer, String[]> listGames() {
		
		String[] gameInfo;
		Game game;
		TreeMap<Integer, String[]> summary = new TreeMap<Integer, String[]>();
		HashMap<Integer, Game>  games = chessServer.getWaitingGames();
		
		
		// To do
		// Get games from server and make the summary
		// [0] The name for white
		// [1] The name for black
		
		for (Integer gamesId : games.keySet()) {
			
			gameInfo = new String[2];
			game = games.get(gamesId);
			
			if(game.getBlack() == null) {
				
				gameInfo[0] = game.getWhite().getName();
				summary.put(gamesId, gameInfo);
				
			}else {
				
				gameInfo[1] = game.getBlack().getName();
				summary.put(gamesId, gameInfo);
				
			}	
		
		}
			
		return summary;
	} 
}
