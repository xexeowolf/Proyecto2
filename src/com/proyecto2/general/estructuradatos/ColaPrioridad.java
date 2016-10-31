package com.proyecto2.general.estructuradatos;

import com.proyecto2.general.busquedaordenamiento.OrdenamientoDLL;


/**
 * Estructura de datos de tipo cola de prioridad
 * @author alfredo
 * @param <T> tipo de la prioridad que se almacenara en el nodo
 * @param <E>tipo de la informacion que se almacenara en el nodo
 */
public class ColaPrioridad<T extends Comparable<T>,E> extends ListaDoble<T,E> {
		
	/**
	 * Constructor de la clase
	 */
	public ColaPrioridad() {
		super();
	}
	
	/**
	 * Metodo para insertar en la cola de prioridad
	 * @param llave prioridad relacionada al elemento
	 * @param valor elemento que se desea almacenar
	 */
	public void insertar(T llave,E valor){
		addFirst(llave,valor);
		OrdenamientoDLL.ShellSort(this);
	}
	
	/**
	 * Metodo para remover el elemento de mayor prioridad de la cola.
	 * @return elemento con la mayor prioridad.
	 */
	public E quitarElemento(){
		
		E valor= tail.valor;
		tail=tail.prev;
		tail.next=null;
		return valor;
	}
}
