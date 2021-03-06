package es.ieslavereda.Chess.model.common;

public class Lista<T> {

	private int size;
	private Nodo<T> cabeza;
	private Nodo<T> cola;

	public Lista() {
		super();

		size = 0;
		cabeza = null;
		cola = null;
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void addHead(T info) {

		Nodo<T> nodo = new Nodo<T>(info);

		if (cabeza == null) {
			cabeza = nodo;
			cola = nodo;
		} else {
			nodo.setSiguiente(cabeza);
			cabeza.setAnterior(nodo);
			cabeza = nodo;
		}

		size++;
	}

	public Lista<T> addAll(Lista<T> lista) {

		Nodo<T> aux = lista.cabeza;
		while (aux != null) {
			addHead(aux.getInfo());
			aux = aux.getSiguiente();
		}

		return this;

	}

	public boolean contains(T elemento) {
		boolean contiene = false;
		Nodo<T> nodo = cabeza;
		while (nodo != null && !contiene) {
			if (nodo.getInfo().equals(elemento))
				contiene = true;
			nodo = nodo.getSiguiente();
		}

		return contiene;
	}

	public void addTail(T info) {

		Nodo<T> aux = new Nodo<T>(info);

		if (cabeza == null) {
			cabeza = aux;
			cola = aux;
		} else {
			aux.setAnterior(cola);
			cola.setSiguiente(aux);
			cola = aux;
		}
		size++;
	}

	public T getHead() {
		T valor = null;

		if (cabeza == null) {
			return null;
		} else if (cabeza.equals(cola)) {
			valor = cabeza.getInfo();
			cabeza = null;
			cola = null;
			size--;
		} else {
			valor = cabeza.getInfo();
			cabeza = cabeza.getSiguiente();
			cabeza.setAnterior(null);
			// cabeza.getSiguiente().setAnterior(null);

			size--;
		}

		return valor;
	}

	public T getTail() {
		T valor = null;

		if (cabeza == null) {
			return null;
		} else if (cabeza == cola) {
			valor = cabeza.getInfo();
			cabeza = null;
			cola = null;
			size--;
		} else {
			valor = cola.getInfo();
			cola.getAnterior().setSiguiente(null);
			cola = cola.getAnterior();
			size--;
		}

		return valor;

	}

	public int search(T info) {

		int posicion = -1;

		Nodo<T> aux = cabeza;
		int i = 0;

		while (aux != null && posicion == -1) {
			if (info.equals(aux.getInfo()))
				posicion = i;
			i++;
			aux = aux.getSiguiente();
		}

		return posicion;
	}

	public T get(int index) {

		if (index == 0)
			return getHead();
		else if (index == size - 1)
			return getTail();
		else if (index > 0 && index < size) {

			Nodo<T> aux = cabeza;
			int i = 0;
			while (i < index) {
				aux = aux.getSiguiente();
				i++;
			}

			aux.getAnterior().setSiguiente(aux.getSiguiente());
			aux.getSiguiente().setAnterior(aux.getAnterior());
			size--;

			return aux.getInfo();

		} else
			return null;
	}

	public T getAndRemove(T elemento) {

		if (elemento.equals(cabeza))
			return getHead();
		else if (elemento.equals(cola))
			return getTail();
		else {

			Nodo<T> aux = cabeza;
			T valor = null;

			while (aux != null && valor == null) {
				if (aux.getInfo().equals(elemento))
					valor = aux.getInfo();
				else
					aux = aux.getSiguiente();
			}
			if (valor != null) {
				aux.getAnterior().setSiguiente(aux.getSiguiente());
				aux.getSiguiente().setAnterior(aux.getAnterior());
				size--;
			}
			return valor;

		}
	}

	@Override
	public String toString() {

		String salida = "";
		salida = "size: " + size + "\n";
		salida += "valores:\n ";

		Nodo<T> aux = cabeza;

		while (aux != null) {
			salida += aux.toString() + " ";
			aux = aux.getSiguiente();
		}

		return salida;
	}

}
