package com.proyecto2.general.busquedaordenamiento;

import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;



public class Busqueda{

	public Busqueda() {
	}
	
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
	
	public  static<T extends Comparable<T>,E>NodoDoble<T,E> ubicarPuntero(NodoDoble<T,E> temp,int pasos){
		while(pasos!=0){
			temp=temp.next;
			pasos--;
		}
		return temp;
		
	}
	
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

public static void main(String[] args){
	/*Integer[] lista=new Integer[6];
	lista[0]=1;
	lista[1]=2;
	lista[2]=3;
	lista[3]=4;
	lista[4]=5;
	lista[5]=6;*/
	ListaDoble<Integer,Integer>lista=new ListaDoble<Integer,Integer>();
	lista.addFirst(5,5);
	lista.addFirst(4,4);
	lista.addFirst(3,3);
	lista.addFirst(2,2);
	lista.addFirst(1,1);
	lista.addFirst(0,0);
	lista.imprimir();
	int cont=0;
	while(cont!=lista.size){
		int num=cont+1;
		NodoDoble<Integer,Integer> pos=Busqueda.busquedaBinariaDL(lista, cont, 0, lista.size);
		System.out.println("El numero "+cont+" se encuentra en la posicion: "+pos.valor);
		cont++;
	}
}
}
	

