package com.proyecto2.general.busquedaordenamiento;

import java.util.ArrayList;
import java.util.Arrays;

import sun.misc.Queue;

import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;



public class OrdenamientoDLL {

	public OrdenamientoDLL(){
		
	}
	
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
	
	public static <T extends Comparable<T>,E> void intercambio(NodoDoble<T,E> a, NodoDoble<T,E> b) {
		T tmp = a.llave;
		a.llave = b.llave;
		b.llave = tmp;
		E vtmp=a.valor;
		a.valor=b.valor;
		b.valor=vtmp;
		}
	
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
		
	public static <T extends Comparable<T>,E> NodoDoble<T,E> buscar(ListaDoble<T,E> lista,int num){
		NodoDoble<T,E> temp=lista.head;
		while(num!=0){
			temp=temp.next;
			num--;
		}
		return temp;
	}
	
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
	
	private static <T extends Comparable<T>,E> int particion(ListaDoble<T,E> lista, int inicio, int fin){
		T pivote = buscar(lista,inicio).llave;
		while(inicio<fin){
			while(buscar(lista,inicio).llave.compareTo(pivote)<0){inicio++;}
			while(buscar(lista,fin).llave.compareTo(pivote)>0){fin--;}
			intercambio(buscar(lista,inicio),buscar(lista,fin));
		}
		return inicio;
	}
	
	private static <T extends Comparable<T>,E> void QuickSort(ListaDoble<T,E> lista,int inicio,int fin){
		if(inicio>=fin)return;
		int indicePivot=particion(lista,inicio,fin);
		QuickSort(lista,inicio,indicePivot);
		QuickSort(lista,indicePivot+1,fin);
	}
	
	public static <T extends Comparable<T>,E> void QuickSort(ListaDoble<T,E> lista){
		QuickSort(lista,0,lista.size-1);
	}		
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


		public static void rellenar(String[] array){
		int largo = maxLength(array);
		for(int i = 0; i < array.length; i++){
		int plus = largo - array[i].length();
		array[i] = array[i] + addN(plus);
		}
		}

		public static String addN(int n){
		String str = "";
		for(int i = 0; i < n; i++){
		str += "#";
		}
		return str;
		}

		public static void limpiar(String[] array){
		for(int i = 0; i < array.length; i++){
		String index = array[i];
		String text[] = index.split("#");
		array[i]= text[0];

		}
		}
		
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

