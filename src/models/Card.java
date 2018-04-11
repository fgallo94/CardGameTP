package models;

public class Card {

	private String number;
	private int value;
	
	public Card(String number, int value) {
		this.number=number;
		this.value=value;
	}
	
	public String getNumber() {
		return this.number;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String toString() {
		return this.number;
	}
}
