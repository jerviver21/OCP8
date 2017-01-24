package edu.paidea.ocp8.ch1;

public enum AnimalClasses {
	MAMMAL(true), FISH(Boolean.FALSE), BIRD(false),
	REPTILE(false), AMPHIBIAN(false), INVERTEBRATE(false);
	boolean hasHair;
	
	private AnimalClasses(Boolean hasHair) {
		System.out.println("Recuerde que el autoboxing y unboxing funciona perfecto en paso de parámetros, pero no el widening y boxing");
		this.hasHair = hasHair;
	}
	public boolean hasHair() {
		return hasHair;
	}
	public void giveWig() {
		hasHair = true;
	} 
}

class LearnToWalk {
	public void toddle() {}
	class BabyRhino extends LearnToWalk {
		//public void toddle() {}
		public void Toddle() {}
		public final void toddle() {
			System.out.println("Si se puede sobreescribir un método como final");
		}
		//public static void toddle() {}
		//public void toddle() throws Exception {}
		public void toddle(boolean fall) {}
	}
}
