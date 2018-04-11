package models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import controller.Table;

public class Dealer extends Thread implements Observer {
	private String alias;
	private ArrayList<Card> deck;
	private Table table;
	
	public Dealer(String alias) {
		this.alias=alias;
		this.deck=createDeck();
		this.table=null;
	}
	
	public void setTable(Table table) {
		this.table=table;
	}
	
	public String getAlias() {
		return this.alias;
	}
	
	public ArrayList<Card> getDeck(){
		return this.deck;
	}

	@Override
	public void run() {
		try {
		while(deck.size()!=0) {
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
	
	 private int random(int min,int max){
	        Random r = new Random();
	        int result = r.nextInt(max-min) + min;
	        return result;
	    }
	
	public void deal(ArrayList<Player> players) {
		for(Player pl : players) {
			if(deck.size()>1) {
			pl.setCard(deck.remove(random(0,deck.size()-1)));
			}else {
				pl.setCard(deck.remove(0));
				table.setNoCards();
			}
			
		}
	}
	
	private ArrayList<Card> createDeck(){
		deck=new ArrayList<Card>();
		int auxValue=0;
		for(int i =1;i<=48;i++) {
			if(i<=12) {
				auxValue++;
				deck.add(new Card(auxValue+" Espada", auxValue));
				if(auxValue==12) {
					auxValue=0;
				}
			}else if(i<=24) {
				auxValue++;
				deck.add(new Card(auxValue+" Oro", auxValue));
				if(auxValue==12) {
					auxValue=0;
				}
			}else if(i<=36) {
				auxValue++;
				deck.add(new Card(auxValue+" Basto", auxValue));
				if(auxValue==12) {
					auxValue=0;
				}
			}else {
				auxValue++;
				deck.add(new Card(auxValue+" Copa", auxValue));
				if(auxValue==12) {
					auxValue=0;
				}
			}
		}
		return this.deck;
	}
	
	@Override
	public void update(Observable obj, Object oldObj) {
		if (obj instanceof Player && oldObj instanceof Card) {
			Player actualPlayer = (Player) obj;
			Card oldCard= (Card) oldObj;
			if(oldCard==null ||actualPlayer.getHand().get(actualPlayer.getHand().size()-1)!=oldCard  ) {
				stepper();
				System.out.println(actualPlayer.getName() + " recibio una carta esta es :"+ actualPlayer.getHand().get(actualPlayer.getHand().size()-1).getNumber() +"\n");
				stepper();
			}
		} if (obj instanceof Table && oldObj instanceof Table) {
			Table actualTable= (Table) obj;
			Table notActualTable = (Table)oldObj;
			if(actualTable.getWinner() != notActualTable.getWinner()) {
				stepper();
				if(actualTable.getWinner().sumHand()==0) {
					System.out.println("/////////// \n");
					System.out.println("El nuevo ganador es "+actualTable.getWinner().getName());
					System.out.println("///////////");
				}else {
					System.out.println("/////////// \n");
					System.out.println("El nuevo ganador es "+actualTable.getWinner().getName()+ " y tiene "+actualTable.getWinner().sumHand()+" puntos.\n");
					System.out.println("/////////// \n");
				}
			}else if(actualTable.isEnd() != notActualTable.isEnd()){
				System.out.println("**********\n");
				System.out.println("La partida finalizó, el ganador es "+actualTable.getWinner().getName()+ " con "+actualTable.getWinner().sumHand()+" puntos.\n");
				System.out.println("**********\n");
				
			}
		}
	}
	
		private void stepper() {
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
}
