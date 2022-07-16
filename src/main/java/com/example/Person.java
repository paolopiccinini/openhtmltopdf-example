package com.example;

public class Person {
	final String name;
	final String surname;
	
	public Person(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

}
