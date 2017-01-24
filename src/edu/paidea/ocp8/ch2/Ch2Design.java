package edu.paidea.ocp8.ch2;

import java.util.function.Predicate;

/**
 * 
 * Checklist:
 * 
 * 1- no redefinir la variable declarada en el lambda
 *
 */
public class Ch2Design {
	private int age;

	public static void main(String[] args) {
		Ch2Design ch2 = new Ch2Design();
		ch2.age = 1;
		ch2.check(ch2, p -> p.age < 5);
	}
	
	private static void check(Ch2Design panda, Predicate<Ch2Design> pred) { // h2
		String result = pred.test(panda) ? "match": "not match"; // h3
		System.out.print(result);
	}

}

interface CanWalk {
	default void walk() { 
		 System.out.println("Walking"); 
		 runi();
	}
	
	static void runi(){}
}
interface CanRun {
	public default void walk() { 
		System.out.println("Walking"); 
		
	}
	public abstract void run();
}

interface CanSprint extends CanWalk, CanRun {
	void sprint();
	
	default void walk() { 
		System.out.println("Walking");
		
	}
}

interface CanFly {
void fly();
}

interface HasWings {
public abstract Object getWingSpan();
}

abstract class Falcon implements CanFly, HasWings {
}


