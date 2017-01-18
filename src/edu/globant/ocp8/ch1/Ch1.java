package edu.globant.ocp8.ch1;


public class Ch1 {
	
	int id = 1;
	
	public Ch1(){
		
	}
	
	public Ch1(int id){
		this.id = id;
	}
	
	@Override
	public int hashCode(){
		return (int)(Math.random()*100);
	}
	
	public boolean equals(Ch1 ch1) {
		return this.id == ch1.id;
	}

	public static void main(String[] args) {
		Ch1 c1 = new Ch1();
		Ch1 c2 = new Ch1(1);
		
		System.out.println(c1.hashCode()+" - "+c2.hashCode());
		
		if(c1.equals(c2))
			System.out.println("c1 equals c2");

	}

}


