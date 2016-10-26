package com.proyecto2.general.estructuradatos;

import com.proyecto2.general.busquedaordenamiento.OrdenamientoDLL;

public class ColaPrioridad<T extends Comparable<T>,E> extends ListaDoble<T,E> {
		
	public ColaPrioridad() {
		super();
	}
	
	public void insertar(T llave,E valor){
		addFirst(llave,valor);
		OrdenamientoDLL.ShellSort(this);
	}
	
	public E quitarElemento(){
		
		E valor= tail.valor;
		tail=tail.prev;
		tail.next=null;
		return valor;
	}
	
	public static void main(String[] args){
		ColaPrioridad<Integer,Integer> cola= new ColaPrioridad<Integer,Integer>();
		
		cola.insertar(2, 50);
		cola.insertar(2, 4);
		cola.insertar(1, 66);
		cola.insertar(34, 12);
		cola.insertar(2, 54);
		
		cola.imprimir();
		cola.quitarElemento();
		cola.imprimir();
		
	}
	
	
	

}
