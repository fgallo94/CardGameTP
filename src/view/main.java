package view;

import java.util.ArrayList;

import controller.Table;
import dao.DaoMatch;
import models.Card;
import models.Dealer;
import models.Player;

public class main {

	static DaoMatch dao = new DaoMatch();
	static ArrayList<String> list=new ArrayList<String>();
	
	public static void main(String [ ] args) {
		Dealer d1= new Dealer("Timador");
		
		Player p1= new Player("Juan");
		Player p2= new Player("Paco");
		Player p3= new Player("Pedro");
		Player p4= new Player("Dela Mar");
		
		Table table = new Table("Mesa 1");
		
		System.out.println("\n Partidas anteriores \n");
		try {
			list = dao.getMatchs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(String string : list) {
			System.out.println(string);
		}
		System.out.println("\n ");
		
		p1.addObserver(d1);
		p2.addObserver(d1);
		p3.addObserver(d1);
		p4.addObserver(d1);
		table.addObserver(d1);
		
		p1.setTable(table);
		p2.setTable(table);
		p3.setTable(table);
		p4.setTable(table);
		d1.setTable(table);
		
		table.setPlayer(p1);
		table.setPlayer(p2);
		table.setPlayer(p3);
		table.setPlayer(p4);
		
		Thread t1 = new Thread(d1);
		Thread t2 = new Thread(p1);
		Thread t3 = new Thread(p2);
		Thread t4 = new Thread(p3);
		Thread t5 = new Thread(p4);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
		
	}
}
