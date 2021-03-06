package es.ieslavereda.Chess.model.common;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

public class Player implements Serializable{
	
	private String name;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private InetAddress ip;
	private Color color;

	public Player(String name, Color color, InetAddress ip) {
		this.color = color;
		this.name = name;
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
		this.ip = socket.getInetAddress();
	}

	public InetAddress getIp() {
		return ip;
	}

	public Color getColor() {
		return color;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", socket=" + socket + ", oos=" + oos + ", ois=" + ois + ", ip=" + ip
				+ ", color=" + color + "]";
	}
}