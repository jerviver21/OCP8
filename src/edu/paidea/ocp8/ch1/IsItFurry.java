package edu.paidea.ocp8.ch1;

import java.util.ArrayList;

public class IsItFurry {
	static interface Mammal { }
	
	static class Furry implements Mammal { }
	
	static class Chipmunk extends Furry { }
	
	public static void main(String[] args) {
		Chipmunk c = new Chipmunk();
		Mammal m = c;
		Furry f = c;
		int result = 0;
		if (c instanceof Mammal) 
			result += 1;
		if (c instanceof Object) 
			result += 2;
		if (null instanceof Chipmunk) 
			result += 4;
		
		System.out.println(result);
		System.out.println("Una sublase SI es instanceOf de las interfaces que implementa y de las superclases que ehereda");
		System.out.println("cualquier interface se puede poner en el instanceOf, para clases si tiene que haber relación de herencia");
	} 
}


class IsItFurry1 {
	static class Chipmunk { }
	
	public static void m1() {
		Chipmunk c = new Chipmunk();
		ArrayList <Chipmunk> l = new ArrayList<>();
		Runnable r = new Thread();
		int result = 0;
		if (c instanceof Chipmunk) result += 1;
		//if (l instanceof Chipmunk) result += 2;
		if (r instanceof Chipmunk) result += 4;
		if (c instanceof Runnable) result += 5;
		System.out.println(result);
		
		
	} 
}