package edu.paidea.ocp8.ch1;

/**
 * checklist
 * 
 *
 */
public class Employee {
	private int employeeId;
	public String firstName, lastName;
	public int yearStarted;
	
	@Override 
	public int hashCode() {
		return employeeId;
	}

	public boolean equals(Employee e) {
		return this.employeeId == e.employeeId;
	}

	public static void main(String[] args) {
		Employee one = new Employee();
		one.employeeId = 101;
		Employee two = new Employee();
		two.employeeId = 101;
		if (one.equals(two)) 
			System.out.println("Success");
		else 
			System.out.println("Failure");
	} 
}

class Book {
	private int ISBN;
	private String title, author;
	private int pageCount;
	
	public int hashCode() {
		return ISBN;
	}
	
	@Override 
	public boolean equals(Object obj) {
		if ( !(obj instanceof Book)) {
			return false;
		}
		Book other = (Book) obj;
		return this.ISBN == other.ISBN;
	}
	// imagine getters and setters are here
}