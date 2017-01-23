package edu.paidea.ocp8.ch3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

/**
 * checklist:
 * 
 * 1. Colleccion con métodos que existan
 *
 */
public class Ch3Collections {

	public static void main(String[] args) {
		Ch3Collections ch3 = new Ch3Collections();
		//ch3.testDeque();
		//ch3.testList();
		//ch3.testSet();
		ch3.testBinarySearch();
	}
	
	public void testDeque(){
		ArrayDeque<String> greetings = new ArrayDeque<>();
		greetings.push("Hello!");
		greetings.push("Hi!");
		greetings.push("ola!");
		greetings.pop();
		greetings.peek();
		while(greetings.peek() != null)
			System.out.println(greetings.pop());
	}
	
	public void testList(){
		HashSet<? super ClassCastException> set = new HashSet<Exception>();
		List<String> list = new Vector<String>();
		Map<String, ? extends Number> hm = new HashMap<String, Integer>();	
	}
	
	public void testSet(){
		Set<Number> numbers = new HashSet<>();
		numbers.add(new Integer(86));
		numbers.add(75);
		numbers.add(new Integer(86));
		numbers.add(null);
		numbers.add(309L);
		numbers.add(3.1416);
		Iterator iter = numbers.iterator();
		while (iter.hasNext())
			System.out.println(iter.next());
		
		TreeSet<String> tree = new TreeSet<String>();
		tree.add("one");
		tree.add("One");
		tree.add("ONE");
		System.out.println(tree.ceiling("On"));
		System.out.println(tree.higher("On"));
		System.out.println(tree.lower("On"));
		System.out.println(tree.floor("On"));
		
		Map<String, Double> map = new HashMap<>();
		map.put("pi", 3.14159);
		//map.put("e", 2);
		map.put("log(1)", new Double(0.0));
		
		System.out.println("Number acepta cualquier literal autoboxing a cualquier literal");
		System.out.println("Un generico acepta autoboxing pero no acepta widening");
	}
	
	public void testListGeneric(){
		ArrayDeque<?> list1 = new ArrayDeque<String>();
		ArrayList<? super Date> list2 = new ArrayList<Date>();
		//List<?> list3 = new ArrayList<?>();
		//List<Exception> list4 = new LinkedList<java.io.IOException>();
		Vector<? extends Number> list5 = new Vector<Integer>();
		//showSize(list1);
		showSize(list2);
		//showSize(list3);
		//showSize(list4);
		showSize(list5);
	}
	
	public void testBinarySearch(){
		System.out.println("binarySearch por defecto usa el orden natural");
		System.out.println("binarySearch(list, 1, Comparator) puede usar un comparator pero para que funcione la lista debe ordenarse con ese comparator");
		System.out.println("si el elemento no está entonces el resultado es la posición que le tocaria + 1 en negativo");
		Comparator<Integer> c = (o1, o2) -> o2-o1;
		List<Integer> list = Arrays.asList(5, 4, 7, 1);
		Collections.sort(list, c);
		System.out.println(Collections.binarySearch(list,2,c));
	}
	
	public void showSize(List<?> list) {
		System.out.println(list.size());
	}
	
	

}
