package es.ieslavereda.Chess.sockets;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import es.ieslavereda.Chess.ChessServer;

public class GameManager implements Runnable {

	private Socket socket;
	private ChessServer chessServer;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public GameManager(Socket socket, ChessServer chessServer) {
		super();
		this.socket = socket;
		this.chessServer = chessServer;
	}

	@Override
	public void run() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-uuuu H:m:s");
		String instante = LocalDateTime.now().format(dtf);

		System.out
				.println(instante + " -> Connection successful from IP " + socket.getRemoteSocketAddress().toString());

		try {

			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			Message mIn=null, mOut=null;
			boolean exit = false;
			GestionMensajes gm = new GestionMensajes(socket,oos,ois,chessServer);

			while (!exit && (mIn = (Message) ois.readObject()) != null) {

				System.out.println("<- GameManager receives the message: " + mIn);

				mOut = gm.procesInput(mIn);

				System.out.println("-> GameManager send the message: " + mOut.getDescription());

				oos.writeObject(mOut);

				// Cases in which we decided to close GameManager
				switch (mOut.getMessageType()) {

				case GAME_CREATED_WAITING:
				case ADDED_TO_GAME:
				case NOT_ADDED:
					exit = true;
				default:
					break;
				}
			}

			// If both players are already in the game, we start
			if (mOut.getMessageType() == Message.Type.ADDED_TO_GAME) {
				chessServer.startGame(mOut.getGameId());
			}

			System.out.println("GameManager finalize for the IP " + socket.getRemoteSocketAddress().toString());
			

		} catch (Exception e) {

		}

	}

}
