package edu.paidea.ocp8.ch1;

public class FourLegged {
	String walk = "walk,";
	
	static class BabyRhino extends FourLegged {
		String walk = "toddle,";
		
		public void m1(){
			System.out.println(walk);
		}
	}
	
	public static void main(String[] args) {
		FourLegged f = new BabyRhino();
		BabyRhino b = new BabyRhino();
		System.out.println(f.walk);
		System.out.println(b.walk);

		f.m1();
		b.m1();
	}
	
	public void m1(){
		System.out.println(walk);
	}

}


interface Otter {
	default void play() { }
}
class RiverOtter implements Otter {
	@Override public boolean equals(Object o) { return false; }
	@Override public int hashCode() { return 42; }
	@Override public void play() { }
}