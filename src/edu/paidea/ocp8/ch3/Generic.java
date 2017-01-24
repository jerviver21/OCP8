package edu.paidea.ocp8.ch3;

public class Generic<T> {
	public static void main(String[] args) {
		Generic<String> g = new Generic<>();
		Generic<Object> g2 = new Generic();
	}
}

class A {}
class B extends A {}
class C extends B {}
class D<C> {
	A a1 = new A();
	A a2 = new B();
	//A a3 = new C();
	//C c1 = new A();
	//C c2 = new B();
	//C c1 = new C();
}
