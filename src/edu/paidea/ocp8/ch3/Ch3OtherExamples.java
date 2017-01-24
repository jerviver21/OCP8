package edu.paidea.ocp8.ch3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

public class Ch3OtherExamples {
	
	public static void main(String[] args){
		Ch3OtherExamples ch3 = new Ch3OtherExamples();
		ch3.testQueue();
		//ch7.testMap();
		//ch7.testMerge();
	}
	
	public void testCompare(){
		//Comparator pertenece a java.util y Comparable pertenece a java.lang
		Comparable<String> comparable = a -> -1;
		
		Comparator<String> comparator = (a,b) -> a.compareTo(b);
	}
	
	public void testQueue(){
		Queue<Integer> q = new LinkedList<>();
		q.add(10);
		q.add(12);
		q.remove(1);
		System.out.println(q);
		
		List<Integer> l = new LinkedList<>();
		l.add(10);
		l.add(12);
		l.remove(1);
		System.out.println(l);
		
		System.out.println("Queue use remove(Object o) de collection so prints [10, 12]");
		System.out.println("List use remove(int index) de list so prints [10]");
		
		Stream.generate(() -> "1").limit(10).peek(System.out::println);
	}
	
	public void testMerge(){
		Map<Integer, Integer> map = new HashMap<>();
		map.put(1, 10);
		map.put(2, 20);
		map.put(3, null);
		
		map.merge(1, 3, (a,b) -> a + b);
		map.merge(3, 3, (a,b) -> a + b);
		map.merge(4, 50, (a,b) -> a + b);
		map.merge(5, 30, (a,b) -> null);
		
		System.out.println(map);
		System.out.println("Si el valor existe, toma el parametro que se pasa, el que existe entran a la Bifunction y el retorno de la BiFunction es lo que se pone");
		System.out.println("Si el valor es null o no existe, se pone como valor el segundo parámetro");
		System.out.println("Si el retorno del Bifuction es  null y la llave existe se removerá del mapa, si no existe, asi retorne null se insertara el segundo parametro en esa llave");
		System.out.println("putIfAbsent: pone solo en caso de que no exista");
	}
	
	
	public void testMap(){
		System.out.println("Recuerde que Map no tiene contains(), sino containsKey() y containsValue");
		Map m = new HashMap();
		m.put(123, "456");
		m.put("abc", "def");
		System.out.println(m.containsKey("123")+" - "+m.containsKey(123));
		
		List<String> list = Arrays.asList("1", "2", "3");
		Iterator<String> iter = list.iterator();
		while(iter.hasNext())
			System.out.print(iter.next());
		
		System.out.println();
		Set<String> s = new HashSet<>();
		s.add("lion");
		s.add("tiger");
		s.add("bear");
		s.forEach(System.out::println);
		
		//s.forEach(s -> System.out.println(s));
	}
	
	public static <T> T identity(T t) {
		return t;
	}
}
