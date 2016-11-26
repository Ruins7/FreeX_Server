package com.ece651.toolsUnits.h2;
/*Author: Zhechen DU
 Created: Oct 1, 2016
 This class is used to test trade algorithm*/

import java.util.*;
//import h2db.*;w

public class Test {

	public static void main(String[] args) throws Exception {
        System.out.println("This is debug test");
        //System.out.println("Do you want to initialize lists?");
        //System.out.println("enter 1 to initialize");
        //int i=0;
/*        Scanner scan = new Scanner(System.in);
        while (i != 1)
        {
        i=scan.nextInt();
        H2currenyPool.initialize();
        		}
        scan.close();*/
        
        H2currenyPool.initialize();

        //if i=1, initial
        
        //Testtrade.input();
        //Testtrade.stacktransfer();
        Seller_Stack lists=new Seller_Stack(); 
        lists=Testtrade.trade(lists);
        
        System.out.println(lists.stackpops());
        System.out.println(lists.stackpops());
        //Traderinfo sellerinfo=Seller_Stack.stackpop();
//        while(sellerinfo.getID() !=0)
//        {
//        	System.out.println("seller info");
//    		System.out.println("ID: " + sellerinfo.getID() + "CID: " + sellerinfo.getcid() +" rate: " + sellerinfo.getrate() + " amount: "
//    				+ sellerinfo.getamount() + " time: " + sellerinfo.gettime());
//    		sellerinfo=Seller_Stack.stackpop();
//        }
        System.out.println("enter 1 for no trade");
        Scanner scan = new Scanner(System.in);
        int i=scan.nextInt();
        if (i == 1)
        {
        	System.out.println("no trade");
        	int sus=Trade.notrade(lists);
        	if (sus==1)
        	{
        		System.out.println("insert into database successful");
        	}
        
        }
        else
        {
        	Traderinfo sellerinfo=lists.stackpop();
        	while(sellerinfo.getID()!=0)
        	{
        		sellerinfo=lists.stackpop();
        	}
        }
        scan.close();
//        H2PoolSort.initialize();
//        H2PoolSort.sort(12);
        H2currenyPool.readtable();
    }

}

class Testtrade {
	public static Stack<Integer> ac = new Stack<Integer>();
//	public static void stacktransfer()
//	{
//		Trade.stacktransfer(ac);
//	}
	public static Seller_Stack trade(Seller_Stack lists)
	{
		//trading 5000 RMB for USD 
		Traderinfo buyinfo = new Traderinfo(1,12,0.167,6000,7);
		Seller_Stack listout=Trade.match(buyinfo,lists);
		int tradere=listout.stackpoptradere();
		//check if there is amount left.
		if (tradere==1)
		{
			
			System.out.println("no amount left");
			
		}
		else
		{
			
			System.out.println("Smount left");
		}
		return listout;
		
	}
	public static void input() {
		Traderinfo sellerinfo = new Traderinfo(1,12, 6, 1000, 7);
		// create stack USD to RMB
		
		// rate and amount should have accuracy of 4 decimal places.
		// multiple by four for calculation
		// push id, rate, amount, time on list
		//stackpush( sellerinfo);
		//sellerinfo.setall(2, 5, 3000, 3);
		//stackpush( sellerinfo);
//		sellerinfo = stackpop();
//		System.out.println("seller info");
//		System.out.println("ID: " + sellerinfo.getID() + " rate: " + sellerinfo.getrate() + " amount: "
//				+ sellerinfo.getamount() + " time: " + sellerinfo.gettime());
//
//		sellerinfo = stackpop();
//		System.out.println("seller info");
//		System.out.println("ID: " + sellerinfo.getID() + " rate: " + sellerinfo.getrate() + " amount: "
//				+ sellerinfo.getamount() + " time: " + sellerinfo.gettime());
		
	}

	public static void stackpush( Traderinfo sellinfo) {
		int id = sellinfo.getID();
		int cid = sellinfo.getcid();
		int rate = (int) Math.round(sellinfo.getrate() * 10000);
		int amount = (int) Math.round(sellinfo.getamount() * 10000);
		int time = sellinfo.gettime();
		ac.push(new Integer(id));
		ac.push(new Integer(cid));
		ac.push(new Integer(rate));
		ac.push(new Integer(amount));
		ac.push(new Integer(time));
	}

	public static Traderinfo stackpop() {
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
			double amount = (double) ((Integer) ac.pop() / 10000);
			sellinfo.setamount(amount);
			double rate = (double) ((Integer) ac.pop() / 10000);
			sellinfo.setrate(rate);
			sellinfo.setcid((Integer) ac.pop());
			sellinfo.setID((Integer) ac.pop());
	
			// System.out.println(rate);
			return sellinfo;
		}
	}
}