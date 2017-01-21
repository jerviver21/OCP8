package edu.paidea.ocp8.ch7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CountNumbers extends RecursiveAction{

	private int start;
	private int end;
	
	public CountNumbers(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected void compute() {
		if (start<2) 
			return;
		else{
			int middle = start + ((end - start) / 2);
			invokeAll(new CountNumbers(start, middle), new CountNumbers(middle, end)); // m1
		}
	}
	
	public static void main(String[] args) {
		System.out.println("If extends from RecursiveAction has to implement void compute()");
		ForkJoinTask<?> task  = new CountNumbers(0, 4);
		ForkJoinPool pool = new ForkJoinPool();
		Object obj = pool.invoke(task);
		System.out.println("RecursiveAction retornaría: "+obj);
		System.out.println("RecursiveAction - void - compute{invokeAll(ForkJointask, ForkJoinTask)}");

	}

}
