package es.ieslavereda.Chess;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import es.ieslavereda.Chess.sockets.GameManager;

public class ChessServer {

	private HashMap<Integer, Game> waitingGames;
	private HashMap<Integer, Game> gamesPlaying;

	private int connectionActives;
	private int newIdGame;

	private int portNumber;
	private boolean listening;

	public ChessServer(int port) {
		connectionActives = 0;
		newIdGame = 1;
		portNumber = port;
		listening = true;
		waitingGames = new HashMap<Integer, Game>();
		gamesPlaying = new HashMap<Integer, Game>();
	}

	public static void main(String[] args) {

		int port;

		try {
			port = Integer.parseInt(args[0]);

		} catch (Exception e) {
			port = 6000;
		}

		ChessServer chs = new ChessServer(port);
		chs.run();

	}

	private void run() {

		System.out.println("ServerSocket trying to start on the port " + portNumber);

		try (ServerSocket ss = new ServerSocket(portNumber)) {

			while (listening) {

				new Thread(new GameManager(ss.accept(), this)).start();
				System.out.println("Connection nº " + (++connectionActives));

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public synchronized int getNewIdGame() {
		return newIdGame++;
	}

	public synchronized boolean gameFinish(int id) {
		return gamesPlaying.remove(id) != null;
	}

	public synchronized void addGameToGamesWating(Game game) {
		waitingGames.put(game.getIdGame(), game);
	}

	public void startGame(int key) {
		gamesPlaying.get(key).start();
	}

	public HashMap<Integer, Game> getWaitingGames() {
		return new HashMap<Integer,Game>(waitingGames);
	}

	public HashMap<Integer, Game> getGamesPlaying() {
		return new HashMap<Integer,Game>(gamesPlaying);
	}

	public synchronized Game addGameToGamesPlaying(int id) {

		Game game = null;

		game = waitingGames.remove(id);
		
		if (game != null) {
			gamesPlaying.put(id, game);
		}

		return game;
	}

}
