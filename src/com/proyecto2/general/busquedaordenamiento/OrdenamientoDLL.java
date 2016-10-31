package com.proyecto2.general.busquedaordenamiento;


import sun.misc.Queue;

import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;



/**
 * Clase que implementa algoritmos de ordenamiento
 * @author alfredo
 *
 */
public class OrdenamientoDLL {

	/**
	 * Constructor de la clase
	 */
	public OrdenamientoDLL(){
		
	}
	
	/**
	 * Metodo que realiza el insertion sort sobre una lista doblemente enlazada
	 * @param lista lista que se desea ordenar
	 */
	public static <T extends Comparable<T>,E> void InsertionSort(ListaDoble<T,E> lista){
	NodoDoble<T,E> i=null,j=null;
	for(i=lista.head.next;i!=null;i=i.next){
		T tmp=i.llave;
		E valorTmp=i.valor;
		for(j=i; j.prev!=null && tmp.compareTo(j.prev.llave)<0;j=j.prev){
			j.llave=j.prev.llave;
			j.valor=j.prev.valor;
			j.prev.llave=tmp;
			j.prev.valor=valorTmp;
			}
		}
	}
	
	/**
	 * Metodo que intercambio dos elementos de lugar en una lista enlazada doble
	 * @param a puntero al primer elemento
	 * @param b puntero al segundo elemento
	 */
	public static <T extends Comparable<T>,E> void intercambio(NodoDoble<T,E> a, NodoDoble<T,E> b) {
		T tmp = a.llave;
		a.llave = b.llave;
		b.llave = tmp;
		E vtmp=a.valor;
		a.valor=b.valor;
		b.valor=vtmp;
		}
	
	/**
	 * Metodo que realiza el bubble sort sobre una lista doblemente enlazada
	 * @param lista lista que se desea ordenar
	 */
	public static <T extends Comparable<T>,E> void BubbleSort(ListaDoble<T,E> lista){
		int remaining = lista.size - 1;
	      for(int x = 0; x < (lista.size-1); x++) {
	         for(int y = 0; y < (remaining); y++) {
	            int tmp;
	            if ( buscar(lista,y).llave.compareTo(buscar(lista,y+1).llave)>0) {
	              intercambio(buscar(lista,y),buscar(lista,y+1));
	            }
	         }
	         remaining--;
	      }
		}
		
	/**
	 * Metodo que busca una posicion especifica dentro de una lista enlazada doble
	 * @param lista lista sobre la cual se realizara la busqueda
	 * @param num posicion que se desea obtener
	 * @return puntero al nodo en la posicion deseada
	 */
	public static <T extends Comparable<T>,E> NodoDoble<T,E> buscar(ListaDoble<T,E> lista,int num){
		NodoDoble<T,E> temp=lista.head;
		while(num!=0){
			temp=temp.next;
			num--;
		}
		return temp;
	}
	
	/**
	 * Metodo que realiza el shell sort sobre una lista doblemente enlazada
	 * @param lista lista que se desea ordenar
	 */
	public  static <T extends Comparable<T>,E> void ShellSort (ListaDoble<T,E> lista) {
		int adentro,afuera;
		T temp;
		E vtemp;
		int h=1;
		while(h<=lista.size/3){
			h=h*3+1;
		}
		while(h>0){
			for(afuera=h;afuera<lista.size;afuera++){
				temp=buscar(lista,afuera).llave;
				vtemp=buscar(lista,afuera).valor;
				adentro=afuera;
				
				while(adentro>h-1 && buscar(lista,adentro-h).llave.compareTo(temp)>=0){
					buscar(lista,adentro).llave=buscar(lista,adentro-h).llave;
					buscar(lista,adentro).valor=buscar(lista,adentro-h).valor;
					adentro -=h;
				}
				buscar(lista,adentro).llave=temp;
				buscar(lista,adentro).valor=vtemp;
			}
			h=(h-1)/3;
		}
	}
	
	/**
	 * Metodo que devuelve una sublista a partir de una lista doblemente enlazada
	 * @param lista lista de la cual se obtendra la sublista
	 * @param inicio posicion inicial
	 * @param fin posicion final
	 * @return sublista
	 */
	private static <T extends Comparable<T>,E> int particion(ListaDoble<T,E> lista, int inicio, int fin){
		T pivote = buscar(lista,inicio).llave;
		while(inicio<fin){
			while(buscar(lista,inicio).llave.compareTo(pivote)<0){inicio++;}
			while(buscar(lista,fin).llave.compareTo(pivote)>0){fin--;}
			intercambio(buscar(lista,inicio),buscar(lista,fin));
		}
		return inicio;
	}
	
	
	/**
	 * Metodo que realiza el quicksort sobre una lista doblemente enlazada
	 * @param lista lista que se desea ordenar
	 * @param inicio posicion inicial
	 * @param fin posicion final
	 */
	private static <T extends Comparable<T>,E> void QuickSort(ListaDoble<T,E> lista,int inicio,int fin){
		if(inicio>=fin)return;
		int indicePivot=particion(lista,inicio,fin);
		QuickSort(lista,inicio,indicePivot);
		QuickSort(lista,indicePivot+1,fin);
	}
	
	/**
	 * Metodo que realiza el quick sort sobre una lista doblemente enlazada
	 * @param lista lista que se desea ordenar
	 */
	public static <T extends Comparable<T>,E> void QuickSort(ListaDoble<T,E> lista){
		QuickSort(lista,0,lista.size-1);
	}		
	
	
	/**
	 * Metodo que realiza el radix sort sobre una lista enlazada
	 * @param array arreglo utilizada para almacenar los datos segun el digito que se este analizando
	 * @param length largo de la lista
	 * @param wordLen largo de las palabras
	 */
	public static void radixSort(String[] array, int length, int wordLen) {
		    Queue[] queueArray = new Queue[256];
		    for (int queueNum = 0; queueNum < 256; queueNum++) {
		        queueArray[queueNum] = new Queue();
		    }
		    for (int len = wordLen - 1; len >= 0; len--) {
		        for (int item = 0; item < length; item++) {
		            int letter = array[item].charAt(len);
		            queueArray[letter].enqueue(new String(array[item]));
		        }
		        int item = 0;
		        for (int queueNum = 0; queueNum < 256; queueNum++) {
		            while (!queueArray[queueNum].isEmpty()) {
		                try {
							array[item] = ((String) queueArray[queueNum].dequeue()).toString();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                item++;
		            }   
		        }   
		    }
		}
		/**
		 * Metodo la cantidad de caracteres de la palabra con mas digitos en una lista
		 * @param array lista sobre la cual se realizara la busqueda y comparacion
		 * @return numero entero que representa el largo de la palabra
		 */
		public static int maxLength (String[] array){
		int largo = 0;
		for (int i = 0; i < array.length; i++){
		int tmp = array[i].length();
		if (tmp > largo){
		largo = tmp;
		}
		}
		return largo;
		}


		/**
		 * Metodo que deja todas las palabras almacenadas dentro de una lista del mismo tamano
		 * @param array lista enlazada con las palabras
		 */
		public static void rellenar(String[] array){
		int largo = maxLength(array);
		for(int i = 0; i < array.length; i++){
		int plus = largo - array[i].length();
		array[i] = array[i] + addN(plus);
		}
		}

		/**
		 * Metodo que cambia el largo de una cadena de texto
		 * @param n numero de caracteres que tendra la cadena de texto
		 * @return cadena de texto modificada
		 */
		public static String addN(int n){
		String str = "";
		for(int i = 0; i < n; i++){
		str += "#";
		}
		return str;
		}

		/**
		 * Metodo que eliminar caracteres secuenciales repetidos de una cadena de texto
		 * @param array lista enlazada de palabras
		 */
		public static void limpiar(String[] array){
		for(int i = 0; i < array.length; i++){
		String index = array[i];
		String text[] = index.split("#");
		array[i]= text[0];

		}
		}
		
		/**
		 * Metodo que realiza el radix sort sobre una lista doblemente enlazada
		 * @param lista lista que se desea ordenar
		 */
		public static<E> ListaDoble<String,E> RadixSort(ListaDoble<String,E> lista,String[]arr){
			NodoDoble<String,E>temp=lista.head;
			int i=0;
			while(temp!=null){
				arr[i]=temp.llave;
				temp=temp.next;
				i++;
			}
			rellenar(arr);
			radixSort(arr,arr.length,maxLength(arr));
			limpiar(arr);
			ListaDoble<String,E>nuevo=new ListaDoble<String,E>();
			for(int y=0;y<arr.length;y++){
				nuevo.addLast(arr[y],null);
			}
			return nuevo;
		}
}

