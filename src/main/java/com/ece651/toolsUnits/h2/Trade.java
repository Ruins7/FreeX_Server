package com.ece651.toolsUnits.h2;
/*Author: Zhechen DU
 Created: Oct 1, 2016
 This the the trade matching algorithm, where a trade request from user will be matched with trade request
 from database using h2*/
 
//USD ID: 1
//CAD ID: 2
//RMB ID: 3
import java.sql.SQLException;
import java.util.*;

public class Trade {

	public static void no_Trade (Stack<Integer> trans)
	{
		
	}
	public static int notrade(Seller_Stack lists)
	{
		int insertsus=0;
		Traderinfo sellerinfo=lists.stackpop();
    	while(sellerinfo.getID()!=0)
    	{
    		System.out.println("ID: " + sellerinfo.getID() + "CID: " + sellerinfo.getcid() +" rate: " + sellerinfo.getrate() + " amount: "
    				+ sellerinfo.getamount() + " time: " + sellerinfo.gettime());
    		try {
				H2currenyPool.update_notrade(sellerinfo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("update_notrade not successful");
				return insertsus;
			}
    		sellerinfo=lists.stackpop();
    	}
    	insertsus=1;
		return insertsus;
	}
	
	public static Seller_Stack match (Traderinfo buyerinfo, Seller_Stack lists)
	{
		int result_id=0;
		double totalAmount=0;
		//get buyerinfo
		int currencyid=buyerinfo.getcid();
		//double buyerID= buyerinfo.getID();
		double amountleft=buyerinfo.getamount();
		
//		double buyeramount=amountleft;
//		//swith to seller exchang rate
//		double buyerrate=1/buyerinfo.getrate();
//		double buyerextotamount=0;		
		System.out.println("ID: " + buyerinfo.getID() + " rate: " + buyerinfo.getrate() + " amount: "
		+ amountleft + " time: " + buyerinfo.gettime());
		
		int exratebig=0;
		Traderinfo sellerinfo=new Traderinfo(0,0,0,0,0);
		while (amountleft>0)
		{
			System.out.println("amountleft:"+amountleft);
			try {
				sellerinfo=H2currenyPool.poolQuary(currencyid, amountleft);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("poolQuary not found");
				
				break;
			}
			double selleramount=sellerinfo.getamount()*sellerinfo.getrate();
			//int sellererID= sellerinfo.getID();
			//double sellerrate=sellerinfo.getrate();
			System.out.println("ID: " + sellerinfo.getID() + " C_ID: " + sellerinfo.getcid() 
			+ " rate: " + sellerinfo.getrate() + " amount: "
			+ selleramount + " time: " + sellerinfo.gettime());
			
			//if return is zero, break the while loop
			if (selleramount==0)
			{
				break;
			}
			//if amount left is qual to amount 
			else if (Math.round(amountleft*10000)==Math.round(selleramount*10000))
			{
				amountleft=0;
				lists.stackpush(sellerinfo);
				totalAmount+=sellerinfo.getamount();
				
			}
			//if selleramount is smaller than amount left, push amount left into stack
			else if (amountleft>selleramount)
			{
				amountleft=amountleft-selleramount;
				lists.stackpush(sellerinfo);
				totalAmount+=sellerinfo.getamount();
			}
			//the amountleft is smaller than seller amount
			else
			{
				lists.stackpush(sellerinfo);
				totalAmount+=sellerinfo.getamount();
				amountleft=0;
//				sellerinfo.setamount((selleramount-amountleft)/sellerinfo.getrate());
//				try {
//					H2currenyPool.update_notrade(sellerinfo);
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					System.out.println("update trade did not complete");
//				}
//				sellerinfo.setamount((amountleft)/sellerinfo.getrate());
//				seller_Stack.stackpush(sellerinfo);
				//System.out.println("match error");
				break;
			}



		}
		lists.stackpushs(amountleft);
		lists.stackpushs(totalAmount);
		
		//output the updated table
		System.out.println("Read table");
		try {
			H2currenyPool.readtable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (amountleft==0)
		{
			result_id=1;
			
		}
		lists.stackpushtradere(result_id);
		return lists;
		//System.out.println(buyerextotamount);
		//small seller exchange=good 
	}

}