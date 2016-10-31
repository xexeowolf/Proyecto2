package com.proyecto2.general.busquedaordenamiento;

import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;



/**
 * Clase generica que implementa la busqueda binaria sobre lista enlazadas dobles
 * @author alfredo
 *
 */
public class Busqueda{

	/**
	 *Constructor de la clase 
	 */
	public Busqueda() {
	}
	
	/**
	 * Metodo que realiza la busqueda binaria sobre una lista doblemente enlazada de numeros enteros
	 * @param lista lista sobre la cual se realizara la busqueda
	 * @param valor elemento que se desea obtener
	 * @param min posicion inicial
	 * @param max posicion final
	 * @return posicion del elemento que se desea encontrar
	 */
	public static <T extends Comparable<T>> int busquedaBinaria(T[] lista, T valor, int min, int max) {
		if (min < max) {
		    int mitad = min+((max-min)/2);
		    if((max-min)==1 && min!=0){
		    	mitad++;
		    }
		    int cmp = lista[mitad].compareTo(valor);
		    if (cmp > 0) 
		    	{return busquedaBinaria(lista, valor, min, mitad);}
		    else if (cmp < 0) 
		    	{return busquedaBinaria(lista, valor, mitad, max);}
		    else{
		    	return mitad;	
		    }
		    
		} 
		return -1;
	}
	
	/**
	 * Metodo que coloca un puntero sobre un nodo especifico
	 * @param temp puntero que se desea colocar
	 * @param pasos posicion del nodo
	 * @return puntero ubicado en la posicion deseada
	 */
	public  static<T extends Comparable<T>,E>NodoDoble<T,E> ubicarPuntero(NodoDoble<T,E> temp,int pasos){
		while(pasos!=0){
			temp=temp.next;
			pasos--;
		}
		return temp;
		
	}
	
	/**
	 * Metodo que realiza la busqueda binaria sobre una lista doblemente enlazada generica
	 * @param lista lista sobre la cual se realizara la busqueda
	 * @param valor elemento que se desea obtener
	 * @param min posicion inicial
	 * @param max posicion final
	 * @return puntero al nodo que contiene el elemento deseado
	 */
	public static <T extends Comparable<T>,E> NodoDoble<T,E> busquedaBinariaDL(ListaDoble<T,E> lista, T valor, int min, int max) {
		if (min < max) {
		    int mitad = min+((max-min)/2);
		    if((max-min)==1 && min!=0){
		    	mitad++;
		    }
		    int cmp = ubicarPuntero(lista.head,mitad).llave.compareTo(valor);
		    if (cmp > 0) 
		    	{return busquedaBinariaDL(lista, valor, min, mitad);}
		    else if (cmp < 0) 
		    	{return busquedaBinariaDL(lista, valor, mitad, max);}
		    else{
		    	return ubicarPuntero(lista.head,mitad);	
		    }
		    
		} 
		return null;
	}
}
	

