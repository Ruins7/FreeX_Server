package com.ece651.toolsUnits.h2;
/*Author: Zhechen DU
 Created: Oct 1, 2016
 This class is used to store trade info, each trade request will be assigned with one single trade info
 stored value are ID, amount, rate, time*/ 
public class Traderinfo{
	private int ID;  //user ID
	private int cid;
	private double amount;  //seller selling amount
	private double rate;    //seller selling rate
	private int time;    //time stamp to track number of days in the selling list
	public Traderinfo(int id1, int cid1, double rate1, double amount1, int time1)
	{
		this.ID=id1;
		this.cid=cid1;
		this.amount=amount1;
		this.rate=rate1;
		this.time=time1;
	}
	public int getcid()
	{
		return this.cid;
	}
	public int getID()
	{
		return this.ID;
	}
	public double getamount()
	{
		return this.amount;
	}
	public double getrate()
	{
		return this.rate;
	}
	public int gettime()
	{
		return this.time;
	}
	public void setID(int id)
	{
		this.ID=id;
	}
	public void setamount(double amount1)
	{
		this.amount=amount1;
	}
	public void setrate(double rate1)
	{
		this.rate=rate1;
	}
	public void settime(int time1)
	{
		this.time=time1;
	}
	public void setcid(int cid1)
	{
		this.cid=cid1;
	
	}
	public void setall(int id, int cid1, double amount1, double rate1, int time1)
	{
		this.ID=id;
		this.cid=cid1;
		this.amount=amount1;
		this.rate=rate1;
		this.time=time1;
				
	}
}