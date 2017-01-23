package edu.paidea.ocp8.ch3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MyComparator implements Comparator<String> {
	
	public int compare(String a, String b) {
		return b.toLowerCase().compareTo(a.toLowerCase());
	}
	
	public static void main(String[] args) {
		String[] values = { "123", "Abb", "aab" };
		Arrays.sort(values, new MyComparator());
		for (String s: values)
			System.out.print(s + " ");
		
		System.out.println();
		Set<String> a = new TreeSet<>();
		a.add("AAA");
		a.add("aaa");
		a.add("AA");
		a.add("aa");
		a.add("123");
		for(String b :a){
			System.out.println(b);
		}
		
		Map<Integer, Integer> map = new HashMap<>(10);
		for (int i = 1; i <= 12; i++) {
			map.put(i, i * i);
		}
		System.out.println(map.get(4));
		
	}

}
