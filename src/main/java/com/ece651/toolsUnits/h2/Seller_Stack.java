package com.ece651.toolsUnits.h2;
import java.util.Stack;

public class Seller_Stack {

	public static Stack<Integer> ac = new Stack<Integer>();
	public void stackpush( Traderinfo sellinfo) {
		int id = sellinfo.getID();
		
		int rate = (int) Math.round(sellinfo.getrate() * 10000);
		int amount = (int) Math.round(sellinfo.getamount() * 10000);
		int time = sellinfo.gettime();
		int cid = sellinfo.getcid();
		ac.push(new Integer(id));
		ac.push(new Integer(cid));
		ac.push(new Integer(rate));
		ac.push(new Integer(amount));
		ac.push(new Integer(time));
	}
	public void stackpushs( double amount) {
		int amounts = (int) Math.round(amount * 10000);
		ac.push(new Integer(amounts));
	}
	public void stackpushtradere( int tradere) {
		ac.push(new Integer(tradere));
	}
	public double stackpops( ) {
		//int amounts = (int) Math.round(amount * 10000);
		double amounts = ((double) ac.pop()) / 10000;
		return amounts;
	}
	public int stackpoptradere( ) {
		//int amounts = (int) Math.round(amount * 10000);
		int amounts = ac.pop();
		return amounts;
	}

	public  Traderinfo stackpop() {
		// int rate1=int(Math.round(rate*10000));
		// Traderinfo info= new Traderinfo();
		Traderinfo sellinfo = new Traderinfo(0 ,0,0,0,0);
		if (ac.empty()==true)				
		{
			return sellinfo;
		}
		else
		{
			sellinfo.settime((Integer) ac.pop());
			
			
			double amount =   ((double) ac.pop()) / 10000;
			System.out.println("amount pop"+amount);
			sellinfo.setamount(amount);
			double rate = ((double) ac.pop()) / 10000;
			sellinfo.setrate(rate);
			sellinfo.setcid((Integer) ac.pop());
			sellinfo.setID((Integer) ac.pop());
			//add exception
			
	
			// System.out.println(rate);
			return sellinfo;
		}
	}
}
