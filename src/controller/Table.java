package controller;

import java.util.ArrayList;
import java.util.Observable;

import dao.DaoMatch;
import models.Dealer;
import models.Player;

public class Table extends Observable{
	private String name;
	private boolean avaible;
	private boolean noCards;
	private ArrayList<Player> players;
	private Player winner;
	private boolean isEnd;
	private DaoMatch dao = new DaoMatch();
	
	public Table(String name) {
		this.name=name;
		this.avaible=true;
		this.noCards=false;
		this.winner=null;
		this.players=new ArrayList<Player>();
		this.isEnd=false;
	}
	
	public Table(Table table) {
		this.name=table.name;
		this.avaible=table.avaible;
		this.noCards=table.noCards;
		this.winner=table.winner;
		this.players=table.players;
		this.isEnd=table.isEnd;
	}

	public void setPlayer(Player player) {
		this.players.add(player);
	}
	
	public void setIsEnd() {
		Table oldTable = new Table(this);
		this.isEnd=true;
		try {
			dao.saveMatch(winner, winner.getHandToString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setChanged();
		this.notifyObservers(oldTable);
	}
	
	public boolean isEnd() {
		return this.isEnd;
	}
	
	public void setWinner(Player winner) {
		Table oldTable = new Table(this);
		this.winner=winner;
		this.setChanged();
		this.notifyObservers(oldTable);
	}
	
	public Player getWinner() {
		return this.winner;
	}
	
	public void setNoCards() {
		this.noCards=true;
	}
	
	public boolean getNoCards() {
		return this.noCards;
	}
	
	public boolean getAvailable() {
		return this.avaible;
	}
	
	public void setAvailable(boolean avaible) {
		this.avaible=avaible;
	}
	public synchronized void match(Object obj) throws InterruptedException {
		if(obj instanceof Dealer) {
			if (!this.getAvailable()) {
				wait();
			} else {
				this.setAvailable(false);
				((Dealer) obj).deal(players);
				this.setAvailable(true);
				notifyAll();
			}
		}else {
			if (!this.getAvailable() || !this.getNoCards()) {
				wait();
			} else {
				this.setAvailable(false);
				if(winner==null) {
					this.setWinner((Player)obj);
				}else {
					if(getWinner().sumHand()<((Player)obj).sumHand()) {
						this.setWinner((Player)obj);
					}else if(getWinner().equals(((Player)obj))) {
						this.setIsEnd();
					}
				}
				this.setAvailable(true);
				notifyAll();
			}
		}
	}

}
