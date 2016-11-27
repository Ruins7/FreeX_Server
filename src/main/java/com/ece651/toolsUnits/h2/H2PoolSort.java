package com.ece651.toolsUnits.h2;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import org.h2.jdbcx.JdbcConnectionPool;

public class H2PoolSort {
	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:mem:poolsort;DB_CLOSE_DELAY=-1";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "pass";// this need to be stored somewhere
	private static final int table_length=1000;
	public static void sort(int cid) throws SQLException
	{
		Connection connection = getPoolConnection();
		Statement stmt = connection.createStatement();


		int index=cid%10+((int)(cid/10))%10-3;
		int startp=index*table_length+1;
		int endp=index*table_length+table_length;
		int point=startp;
		int pointend=startp;
		ResultSet rs = stmt.executeQuery("Select * FROM Currency_pool WHERE id BETWEEN "+
				startp +" AND "+endp +" ORDER by time");
		//System.out.println("sort print");
		Queue<Integer> q = new LinkedList<Integer> ();
		while (rs.next()) {
			int rsuserid=rs.getInt("user_id");
			int reex_rate=rs.getInt("ex_rate");
			int rsamount=rs.getInt("amount");
			int rstime=rs.getInt("time");
			if (rsamount==0)
			{

			}
			else
			{
				q.add(rs.getInt("user_id"));
				q.add(rs.getInt("ex_rate"));
				q.add(rsamount);
				q.add(rs.getInt("time"));
				pointend++;
			}
//			System.out.println("Id " + rs.getInt("id") + " user_id " + rs.getInt("user_id")
//			+" ex_rate " + rs.getInt("ex_rate")+ "amount"+rs.getInt("amount")
//			+"time " + rs.getInt("time"));
		}
		for (int i=point; i<pointend; i++)
		{
			stmt.execute("UPDATE Currency_pool SET( user_id,ex_rate,amount,time)=("
					+ ""+q.poll()+", "+q.poll()+", "+q.poll()+", "+q.poll()+")WHERE id= "+(i)+"");
		}
		for (int i=pointend; i<endp+1; i++)
		{
			
			stmt.execute("UPDATE Currency_pool SET( user_id,ex_rate,amount,time)=(0,0,0,0)WHERE id= "
			+(i)+"");
		}
//		ResultSet rs1 = stmt.executeQuery("Select * FROM Currency_pool WHERE id BETWEEN "+
//				startp +" AND "+endp +" ORDER by id");
//		while (rs1.next()) {
//			System.out.println("Id " + rs1.getInt("id") + " user_id " + rs1.getInt("user_id")
//			+" ex_rate " + rs1.getInt("ex_rate")+ "amount"+rs1.getInt("amount")
//			+"time " + rs1.getInt("time"));
//		}


	}
	public static void initialize() throws Exception {
		int [] trade_id ={12, 13, 21, 23, 31, 32};

		int [][] cur_pool=readtxtfile("src/cur_pool.txt",1);
		int [][] cur_loc=readtxtfile("src/cur_loc.txt",2);
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
					Scanner colReaders = new Scanner(input.nextLine());
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
}
