package edu.paidea.ocp8.ch1;

/**
 * 
 * CHECKLIST:
 * 0- verificar control de accesos
 * 1- Las inner class no pueden tener nada static
 * 2- enum constructor private
 *
 */
public class Ch1ClassDesign {
	int x = 5;
	
	enum Flavors {
		VANILLA, CHOCOLATE, STRAWBERRY
	}
		
	protected class Inner1 {
		public int x = 10;
		public void go() { System.out.println(Ch1ClassDesign.this.x); }
	}
	
	protected static class Inner2 {
		public static int x = 10;
		public void go() { System.out.println(this.x); }
	}

	public static void main(String[] args) {
		
		String s1 = "Canada";
		String s2 = new String(s1);
		if(s1 == s2) System.out.println("s1 == s2");
		if(s1.equals(s2)) System.out.println("s1.equals(s2)");
		
		System.out.println(Ch1ClassDesign.Flavors.VANILLA.ordinal());
		
		Flavors f = Flavors.STRAWBERRY;
		switch (f) {
			case VANILLA: System.out.println("vanilla");
			case CHOCOLATE: System.out.println("chocolate");
			case STRAWBERRY: System.out.println("strawberry");
			break;
			default: System.out.println("missing flavor");
		}
		
		Ch1ClassDesign out = new Ch1ClassDesign();
		Ch1ClassDesign.Inner1 in = out.new Inner1();
		in.go();
		
		Ch1ClassDesign.Inner1 in2 = new Ch1ClassDesign().new Inner1();

	}

}
