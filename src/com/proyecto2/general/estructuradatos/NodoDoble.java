package com.proyecto2.general.estructuradatos;


/**
 * @author alfredo
 *Estructura de datos de tipo nodo doblemente enlazado
 * @param <E> tipo del primer elemento(llave) que almacena el nodo
 * @param <T>tipo del segundo elemento(valor) que almacena el nodo
 */
public class NodoDoble<E,T> {
	
	public NodoDoble<E,T> next,prev;
	public E llave;
	public T valor;
	
	/**
	 * Constructor de la clase
	 */
	public NodoDoble(){
		
	}
	
	/**
	 * Constructor de la clase
	 * @param Llave llave que estara relacionada a un unico elemento
	 * @param Valor informacion que se desea almacenar dentro de la lista.
	 */
	public NodoDoble(E Llave,T Valor){
		llave=Llave;
		valor=Valor;
		next=null;
		prev=null;
	}

}
