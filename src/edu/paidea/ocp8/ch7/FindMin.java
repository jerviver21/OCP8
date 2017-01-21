package edu.paidea.ocp8.ch7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindMin extends MyTask{
	private Integer[] elements;
	private int a;
	private int b;
	
	public FindMin(Integer[] elements, int a, int b) {
		this.elements = elements;
		this.a = a;
		this.b = b;
	}
	
	@Override
	protected Integer compute() {
		if((b-a) < 2)
			return Math.min(elements[a], elements[b]);
		else{
			int m = a + ((b-a)/2);
			System.out.println(a+" - "+m+" - "+b);
			
			MyTask t1 = new FindMin(elements, a, m);
			System.out.println("Si se tiene un solo hilo se debe llamar y esperar para evitar que se cuelgue");
			int result = t1.fork().join();
			System.out.println("El fork() llama otro hilo, el join() espera el resultado en un futuro");
			return Math.min(new FindMin(elements, m, b).compute(), result);
		}
	}

	public static void main(String[] args) {
		System.out.println("If extends from RecursiveTask has to implement T compute()");
		Integer[] elements = new Integer[]{8, -3, 2, -54};
		MyTask task = new FindMin(elements, 0, elements.length-1);
		ForkJoinPool pool = new ForkJoinPool(1);
		System.out.println("ForkJoinPool.invoke llama al método compute() para RecursiveTask");
		Integer sum = pool.invoke(task);
		System.out.println("Min: "+sum);
		System.out.println("RecursiveTask - T - compute{fork() join()  compute()} ");

	}

	

}

abstract class MyTask extends RecursiveTask<Integer>{
	
}
