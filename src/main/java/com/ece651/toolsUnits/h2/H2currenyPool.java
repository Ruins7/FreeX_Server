package com.ece651.toolsUnits.h2;
/*Author: Zhechen DU
 Created: Oct 8, 2016
 this file is used for accessing h2 ram currency pool
 exchange data will be kept in here
 two table are used: Currency_pool and Currency_loc
 currency_loc is used to track each currency's location in currency pool
 curreny_poll is used to store user trade request, 
 each currency will have two list, one for current list, the other is old.
 The rest of the data will be stored in a back up database for sort and etc.
 */ 
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.h2.jdbcx.JdbcConnectionPool;


public class H2currenyPool {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:pool;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "pass";// this need to be stored somewhere
    private static final int table_length=1000;
    //trade id between three currencies
   
    //initialze table for currency poll and currency id 
	public static void initialize() throws Exception {
    	int [] trade_id ={12, 13, 21, 23, 31, 32};
    	
    	int [][] cur_pool=readtxtfile("F:\\Administrator\\workspaces\\FreeX_Server\\src\\main\\java\\cur_pool.txt",1);
    	int [][] cur_loc=readtxtfile("F:\\Administrator\\workspaces\\FreeX_Server\\src\\main\\java\\cur_loc.txt",2);
    	//System.out.println("cur_pool");
        try {
            createpoolloc(trade_id);
            createpool(trade_id);
            updatetable(cur_loc,cur_pool);
            readtable();
            //insertWithStatement();

            //insertWithPreparedStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        
        
	}
	//this is used to update if user decid not to trade
	public static void update_notrade(Traderinfo sellinfo) throws SQLException
	{
		int cur_id = sellinfo.getcid();
		int user_id = sellinfo.getID();
		int rate = (int) Math.round(sellinfo.getrate() * 10000);
		int amount_t = (int) Math.round(sellinfo.getamount() * 10000);
		int time = sellinfo.gettime();
		Connection connection = getPoolConnection();
		Statement stmt = connection.createStatement();
		
		ResultSet rs = stmt.executeQuery("select * from Currency_loc WHERE trade_id="+(cur_id)+"" );
		//System.out.println(rs);
		rs.next();
		int pool_loc=rs.getInt("pool_loc");
		int pool_preloc=rs.getInt("pool_preloc");
		//check if there is room to append to the table at the top
		//30 need to be changed
		for (int i=0; i<30;i++)	
		{
			ResultSet rs_p = stmt.executeQuery("select * from Currency_pool WHERE "
					+ "id="+(pool_loc+i)+"" );
			rs_p.next();
			int rs_p_amount=rs_p.getInt("amount");
			if (rs_p.getInt("user_id")==user_id&&(rate==rs_p.getInt("ex_rate")))
			{
				stmt.execute("UPDATE Currency_pool SET (amount) =("+(rs_p_amount+amount_t)+") "
						+ "WHERE id ="+(pool_loc+i)+"");
				stmt.close();
		        connection.commit();
				connection.close();
				return;
			}
		}
		//check if we can append to the table at the bottom
		for (int i=0; i<20;i++)	
		{
			ResultSet rs_p = stmt.executeQuery("select * from Currency_pool WHERE "
					+ "id="+(pool_preloc+i)+"" );
			rs_p.next();
			int rs_p_amount=rs_p.getInt("amount");
			if (rs_p.getInt("user_id")==user_id&&(rate==rs_p.getInt("ex_rate")))
			{
				stmt.execute("UPDATE Currency_pool SET (amount) =("+(rs_p_amount+amount_t)+") "
						+ "WHERE id ="+(pool_preloc+i)+"");
				stmt.close();
		        connection.commit();
				connection.close();
				return;
			}
		}
		int modl=rs.getInt("pool_loc")%table_length;
		if (modl==0)
		{
			//append to sale pool
		}
		for (int i=1; i<modl;i++)
		{
			ResultSet rs_p = stmt.executeQuery("select * from Currency_pool WHERE "
					+ "id="+(pool_loc-i)+"" );
			if (rs_p.getInt("user_id")==0)
			{
				stmt.execute("INSERT INTO Currency_pool( user_id,ex_rate,amount,time"
						+ ")VALUES( "+user_id+","+rate+","+amount_t+","+time+""); 
				stmt.close();
		        connection.commit();
				connection.close();
				return;

			}
		}
		//append to sale pool
		//amount =0, check next row,update currency location

        stmt.close();
        connection.commit();
		connection.close();
	}
	
	
	//create table for currency pool
	//buyer info will be compared with seller info from the pool
	private static void createpool(int [] trade_id) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = getPoolConnection();
		Statement stmt = connection.createStatement();
		//creating table with id, user_id, ex_rate,amount, time
		stmt.execute("CREATE TABLE Currency_pool(id int auto_increment primary key, user_id int, ex_rate int,"
        		+ "amount int, time int)" );
		for (int i=0; i<trade_id.length; i++)
		{
			//this will be changed to j<2000 for real testing
			for (int j=0; j<table_length; j++)
			{
            stmt.execute("INSERT INTO Currency_pool( user_id,ex_rate,amount,time)VALUES( 0,0,0,0)");            
			}
		}
/*		ResultSet rs = stmt.executeQuery("select * from Currency_pool");
        System.out.println("H2 In-Memory Database Currency_pool Table");
        while (rs.next()) {
            System.out.println("Id " + rs.getInt("id") + " user_id " + rs.getInt("user_id")
            +" ex_rate " + rs.getInt("ex_rate")+ time " + rs.getInt("time"));
        }*/
        stmt.close();
        connection.commit();
		connection.close();
	}
	
	//create table for currency pool id
	//This table is used to determine the length of pool table
	//The pool table length will be added dynamically with respect to the number of currency
	private static void createpoolloc(int [] trade_id) throws SQLException
	{
		//creating table
		Connection connection = getPoolConnection();
		Statement stmt = connection.createStatement();
		stmt.execute("CREATE TABLE Currency_loc(id int auto_increment primary key, trade_id int unique, pool_loc int,"
        		+ "pool_preloc int)" );
		for (int i=0; i<trade_id.length; i++)
		{

            stmt.execute("INSERT INTO Currency_loc( trade_id,pool_loc,pool_preloc)VALUES( "+trade_id[i]+",0,0)");            

		}
/*		ResultSet rs = stmt.executeQuery("select * from Currency_loc");
        System.out.println("H2 In-Memory Database Currency_loc Table");
        while (rs.next()) {
            System.out.println("Id " + rs.getInt("id") + " trade_id " + rs.getInt("trade_id")
            +" pool_loc " + rs.getInt("pool_loc")+" pool_preloc " + rs.getInt("pool_preloc"));
        }*/
        stmt.close();
        connection.commit();
		connection.close();
	}
	
	//access pool connection
	private static Connection getPoolConnection() throws SQLException {
		// TODO Auto-generated method stub
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            
        }

        JdbcConnectionPool dbConnection = JdbcConnectionPool .create(DB_CONNECTION, DB_USER, DB_PASSWORD);
        return dbConnection.getConnection();

	}
	

	//This is used to insert new currency
	//currently unused
	private static void insertNewCurreny(int currency_id) throws SQLException {
		Connection connection = getPoolConnection();
		Statement stmt = connection.createStatement();
		stmt.close();
		connection.commit();
		connection.close();
		
	}
	//test insert input
	private static void updatetable(int[][] cur_loc, int [][]cur_pool) throws SQLException {
        Connection connection = getPoolConnection();
        Statement stmt = null;
        int index=cur_loc[0][0]%10+((int)(cur_loc[0][0]/10))%10-3;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            
            for (int i=0; i<cur_loc.length; i++)
            {
            	//System.out.println(i);
            stmt.execute("UPDATE Currency_loc SET( trade_id,pool_loc,pool_preloc) =("
            		+ ""+cur_loc[i][0]+", "+cur_loc[i][1]+", "+cur_loc[i][2]+")WHERE id="+(i+index+1)+"" );
            }

            for (int i=0; i<cur_pool.length; i++)
            {
            stmt.execute("UPDATE Currency_pool SET( user_id,ex_rate,amount,time)=("
            		+ ""+cur_pool[i][0]+", "+cur_pool[i][1]+", "+cur_pool[i][2]+", "+cur_pool[i][3]+")WHERE id="+(i+1+index*1000)+"");
            }
            //stmt.execute("INSERT INTO Currency_loc( trade_id,pool_loc,pool_preloc) VALUES(11,3000,4000)");



            //stmt.execute("DROP TABLE Currency_loc");
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
		// TODO Auto-generated method stub
		
	}
	public static void readtable() throws SQLException
	{
        Connection connection = getPoolConnection();
        Statement stmt=null;
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ResultSet rs = stmt.executeQuery("Select * FROM Currency_loc");
        System.out.println("H2 In-Memory Database Currency_loc Table");
        while (rs.next()) {
            System.out.println("Id " + rs.getInt("id") + " trade_id " + rs.getInt("trade_id")
            +" pool_loc " + rs.getInt("pool_loc")+" pool_preloc " + rs.getInt("pool_preloc"));
        }
        ResultSet rs1 = stmt.executeQuery("Select * from Currency_pool");
        System.out.println("H2 In-Memory Database Currency_pool Table");
        while (rs1.next()) {
            System.out.println("Id " + rs1.getInt("id") + " user_id " + rs1.getInt("user_id")
            +" ex_rate " + rs1.getInt("ex_rate")+ "amount"+rs1.getInt("amount")
            +"time " + rs1.getInt("time"));
        }
        stmt.close();
	}
	//switchtable is used to update pretable
	public static void switchtable()
	{

	}
	//this function is used to
	public static int[][] update()
	{
		int length=1;
		int [][] table = new int [length][5];
		return table;
	}
	
	//poolquary return lowest trader info from current pool
	//The input is currency id, amount and (table mode??? may not need it)
	//table mode is either one or two, 1= upper half of the currency, 2=lower half
	//depend on timing, either one can be current and the other one will be previous
	public static Traderinfo poolQuary(int cur_id, double amount_t1) throws SQLException
	{
		int amount_t=(int)Math.round(amount_t1*10000);
		Connection connection = getPoolConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select * FROM Currency_loc WHERE trade_id="+cur_id+"" );
		rs.next();
		int pool_loc=rs.getInt("pool_loc");
		if (pool_loc==0)
		{
			Traderinfo seller=new Traderinfo(0,0,0,0,0);
			return seller;
		}
		System.out.println(pool_loc);
		ResultSet rspool = stmt.executeQuery("select * FROM Currency_pool WHERE id="
				+ ""+pool_loc+"" );
		rspool.next();
		int user_id=rspool.getInt("user_id");
		int user_time=rspool.getInt("time");
		int amount_sell=rspool.getInt("amount");
		//int rate_fi=rspool.getInt("ex_rate");
		int rate_f=rspool.getInt("ex_rate");
		System.out.println("rate_f: "+rate_f+" amount_t: "+amount_t);
		double amount_t2=((double)amount_t)/rate_f;
		amount_t=(int )Math.round(amount_t2*10000);
		
		System.out.println("amount_sell: "+amount_sell+" amount_t: "+amount_t);
		double amount_f=0;
		if (amount_sell>amount_t)
		{
			int amount_new=amount_sell-amount_t;
			stmt.execute("UPDATE Currency_pool SET ( amount) =("+amount_new+") "
					+ "WHERE id ="+pool_loc+"");
			amount_f=(double)(amount_t);
		}
		//amount_sell =amount buy, check next row,update currency location
		else
		{
			amount_f=(double)(amount_sell);
			//amount_f=Math.round(amount_f*10000)/10000;
			int pool_locn=pool_loc+1;
			//update the row to zero and quarry next row
			stmt.execute("UPDATE Currency_pool SET (user_id, ex_rate, amount,time) =(0,0,0,0) "
					+ "WHERE id ="+pool_loc+"");
			ResultSet rs_new = stmt.executeQuery("select * from Currency_pool WHERE id="
					+ ""+pool_locn+"" );
			rs_new.next();
			int amount_next=rs_new.getInt("amount");

			//if new row =0
			//update currency location if next amount is not zero
			if (amount_next==0)
			{
				stmt.execute("UPDATE Currency_loc SET (pool_loc) =(0) "
						+ "WHERE trade_id ="+cur_id+"");
			}
			else
			{
				stmt.execute("UPDATE Currency_loc SET (pool_loc) =("+pool_locn+") "
						+ "WHERE trade_id ="+cur_id+"");
			}
		}
        stmt.close();
        connection.commit();
		connection.close();
		
		amount_f=(amount_f)/10000;
		double rate_f1=(double)rate_f/10000;
		//user_id,ex_rate,amount,time
		Traderinfo seller=new Traderinfo(user_id,cur_id,rate_f1,amount_f,user_time);
		

		return seller;
	}
	
	
	//this is used for testing
	//it uses generated text file 
	//default array intput: cur_pool: 24*4 cur_loc:6*3
	private static int[][] readtxtfile(String filepath, int filenum) 
	{
		//a=cur_pool.txt filenum=1
		//b=cur_loc.txt filenum=2
		File file=new File(filepath);
        Scanner input=null;
		if (filenum==1)
		{
			int [][] a= new int [6000][4];
			try {
				input = new Scanner (file);
		        int i =0;
		        while(input.hasNextLine())
		        {
		        	int j=0;
		            Scanner colReader = new Scanner(input.nextLine());
		            //ArrayList<Integer> col = new ArrayList<Integer>();
		            while(colReader.hasNextInt())
		            {
		                a [i][j]=colReader.nextInt();
		                j++;
		            }
		            i++;
		            System.out.println(i);
		            //Scanner colReaders = new Scanner(input.nextLine());
		            //System.out.println(a [i][2]);
		            //a.add(col);
		            colReader.close();
		        }
		        input.close();
		        //System.out.println("file found");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("file not found");
			}
			System.out.println(a[21][0]+" "+a[1][1]+" "+a[1][2]+" "+ a[1][3]);
	        return a;
		}
		else
		{
			int [][] b= new int [6][3];
			try {
				input = new Scanner (file);
		        int i =0;
		        while(input.hasNextLine())
		        {
		        	int j=0;
		            Scanner colReader = new Scanner(input.nextLine());
		            //ArrayList<Integer> col = new ArrayList<Integer>();
		            while(colReader.hasNextInt())
		            {
		                b [i][j]=colReader.nextInt();
		                j++;
		            }
		            i++;
		            //a.add(col);
		            colReader.close();
		        }
		        input.close();
		        //System.out.println("file found");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("file not found");
			}
			//System.out.println(a[1][0]+" "+a[1][1]+" "+a[1][2]+" "+ a[1][3]);
	        return b;
		}

	}
	

/*	private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;
        PreparedStatement insertPreparedStatement = null;
        PreparedStatement selectPreparedStatement = null;

//        String CreateQuery = "CREATE TABLE Currency_loc(id int primary key, trade_id int, pool_loc int,"
//        		+ "pool_preloc int)" ;
        String InsertQuery = "INSERT INTO Currency_loc" + "(id, trade_id,pool_loc,pool_preloc) values" + "(?,?,?,?)";
        String SelectQuery = "select * from Currency_loc";

        try {
            connection.setAutoCommit(false);

            createPreparedStatement = connection.prepareStatement(CreateQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();

            insertPreparedStatement = connection.prepareStatement(InsertQuery);
            insertPreparedStatement.setInt(1, 1);
            insertPreparedStatement.setInt(2, 1);
            insertPreparedStatement.setInt(3, 1);
            insertPreparedStatement.setInt(4, 1);
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            System.out.println("H2 In-Memory Database inserted through PreparedStatement");
            while (rs.next()) {
                System.out.println("Id " + rs.getInt("id") + " trade_id " + rs.getInt("trade_id")
                +" pool_loc " + rs.getInt("pool_loc")+" pool_preloc " + rs.getInt("pool_preloc"));
            }
            selectPreparedStatement.close();

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
		// TODO Auto-generated method stub
		
	}
*/



}
