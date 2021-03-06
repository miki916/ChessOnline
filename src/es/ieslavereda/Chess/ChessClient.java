package es.ieslavereda.Chess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TreeMap;

import es.ieslavereda.Chess.model.common.Color;
import es.ieslavereda.Chess.model.common.Coordenada;
import es.ieslavereda.Chess.model.common.Player;
import es.ieslavereda.Chess.sockets.Message;
import es.ieslavereda.Chess.tools.Input;

public class ChessClient {

	private String ip;
	private int port;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Player player;

	public static void main(String[] args) {

		ChessClient cc = new ChessClient();
		cc.run();

	}

	private void run() {

		int option;

		do {
			showStartMenu();
			option = Input.getInt("Enter option (0-Exit):");
			switch (option) {
			case 1:
				connect();
				if (socket != null) {
					getPlayer();
					createGame();
				}
				break;
			case 2:
				connect();
				if (socket != null) {
					getPlayer();
					addToGame();
				}
				break;
			default:
				showStartMenu();
				System.out.println("Option not valid. Enter option (0-Exit):");
				break;
			}
		} while (option != 0);

	}

	private void addToGame() {
		Input sc = new Input();
		Message mOut, mIn;
		int game;

		mOut = new Message(Message.Type.GET_CREATED_GAMES,
				"Requesting the list of games waiting for the player " + player.getName());
		mOut.setPlayer(player);
		
		
		mIn = sendMessageAndWaitResponse(mOut);
	
		TreeMap<Integer, String[]> listadoTotal = mIn.getListOfGames();
		TreeMap<Integer, String[]> listadoPosible = new TreeMap<Integer, String[]>();
		
		for (Integer key : listadoTotal.keySet()) {
			if (player.getColor() == Color.WHITE && listadoTotal.get(key)[0] == null
					|| player.getColor() == Color.BLACK && listadoTotal.get(key)[1] == null)
			
				listadoPosible.put(key, listadoTotal.get(key));
			
		}
		
		if (listadoPosible.isEmpty()) {
			System.out.println("No hay partidas en espera para ese color.");
		} else {
			
			do {
				imprimirPartidas(listadoPosible);
				game = Input.getInt("Select the id of the desired game");
				if (!mIn.getListOfGames().keySet().contains(game))
					System.out.println("The game does not exist.");

			} while (!mIn.getListOfGames().keySet().contains(game) && game != 0);

			mOut = new Message(Message.Type.ADD_TO_GAME,
					"Requesting to add the player " + player.getName() + " to the game " + game);
			mOut.setGameId(game);
			mOut.setPlayer(player);

			mIn = sendMessageAndWaitResponse(mOut);
			if (mIn.getMessageType() == Message.Type.ADDED_TO_GAME)
				play();
		}
	}

	private void createGame() {

		Message mIn, mOut;
		mOut = new Message(Message.Type.CREATE_GAME, "Crear un nuevo juego.");
		mOut.setPlayer(player);

		mIn = sendMessageAndWaitResponse(mOut);

		if (mIn.getMessageType() == Message.Type.GAME_CREATED_WAITING) {
			System.out.println(mIn.getDescription());
			play();
		} else {
			showStartMenu();
			System.out.println("The game could not be created.");
		}

	}

	private void play() {

		try {

			Message mOut = null, mIn = null;
			boolean exit = false;
			Input sc = new Input();
			Coordenada c;
			
			// To do
			// While you don't have to exit and the message read is not null
			// If the message is to show information, you only have to show it,
			// but if the message request a coordinate, your have to provide it.
			
			while(mOut == null){
				
				
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void connect() {

		ip = Input.getString("Introduce la direccion IP: ");
		port = Input.getInt("Introduce el puerto:");

		try {

			this.socket = new Socket(ip, port);

			this.ois = new ObjectInputStream(socket.getInputStream());
			this.oos = new ObjectOutputStream(socket.getOutputStream());

			System.out.println("Conexion establecida correctamente!!");

		} catch (Exception e) {
			System.out.println("No se ha podido realizar la conexion.");
		}

	}

	private void getPlayer() {

		String nombre = Input.getString("Dame tu nombre");

		String c = Input.getString("Dime tu color [w|b]:").toLowerCase().substring(0, 1);
		try {
			if (c.equals("b"))
				player = new Player(nombre, Color.BLACK, socket.getLocalAddress());
			else
				player = new Player(nombre, Color.WHITE, socket.getLocalAddress());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Message sendMessageAndWaitResponse(Message mOut) {

		Message mIn = null;

		try {
			
			System.out.println(mOut);
			oos.writeObject(mOut);
			mIn = (Message) ois.readObject();
		
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return mIn;
	}

	private void showStartMenu() {

		update();
		System.out.println(" ╔════════════════════════════╗");
		System.out.println(" ║            Menu            ║");
		System.out.println(" ╟────────────────────────────╢");
		System.out.println(" ║      1- Create game        ║");
		System.out.println(" ║      2- Add to game        ║");
		System.out.println(" ╟────────────────────────────╢");
		System.out.println(" ║      0- Exit               ║");
		System.out.println(" ╚════════════════════════════╝");

	}

	private void imprimirPartidas(TreeMap<Integer, String[]> listado) {

		update();
		
		// To do
		// Show the list of games
		
		System.out.println(listado.toString());


	}

	private void update() {
		System.out.flush();
	}

}
