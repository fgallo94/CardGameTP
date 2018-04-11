package models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.Collectors;

import controller.Table;

public class Player extends Observable implements Runnable{

	private String name;
	private ArrayList<Card> hand;
	private Table table;
	
	public Player(String name) {
		this.name=name;
		this.hand=new ArrayList<Card>();
		this.table=null;
	}
	
	public Player(Player player) {
		this.name=player.name;
		this.hand=player.hand;
		this.table=player.table;
	}
	
	public void setTable(Table table) {
		this.table=table;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setCard(Card card) {
		Card oldCard=null;
		if(this.getHand().size()!=0) {
			oldCard= this.getHand().get(hand.size()-1);
		}
		this.hand.add(card);
		this.setChanged();
		this.notifyObservers(oldCard);
	}
	
	public String getHandToString() {
		 String listString = hand.stream().map(Object::toString).collect(Collectors.joining(", "));
		 return listString;
	}
	
	public int sumHand() {
		int value=0;
		for(Card card : hand) {
			value+=card.getValue();
		}
		return value; 
	}
	
	public ArrayList<Card> getHand(){
		return this.hand;
	}
	@Override
	public void run() {
		try {	
		while (!table.isEnd()) {
				table.match(this);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
