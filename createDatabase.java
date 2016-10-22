import java.sql.*;
import java.util.Scanner;
import java.io.*;

class createDatabase{
	
		public static void main(String args[]){
		Connection con=null;
		Statement stmt = null;
		Console cnsl2=null;
		String USER,PASS;
		try
			{
			Scanner scAuth = new Scanner(System.in);
			System.out.println("Enter your mySQL username:");
			USER=scAuth.next();
			cnsl2=System.console();
			char[] pswd = cnsl2.readPassword("Enter your mySQL password: \n");
			PASS= new String(pswd);
			
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/",USER,PASS);
			if(con!=null){
				System.out.println("CONNECTION SUCCESSFUL!");
				stmt = con.createStatement();
				String sql = "CREATE DATABASE cart";
				stmt.executeUpdate(sql);
				System.out.println("Database created successfully...");
				System.out.println("\n        You may now compile and run the program Cart.java...");
				String stmt2="use cart";
				String tables = "Create table products(Product_ID int, Product_Name varchar(10), Product_price float, Discount float)";
				
				String insert1 = "insert into products values (1,\"Dove\",35.76,2.4)";
				String insert2 = "insert into products values (2,\"Complan\",190.68,10.0)";
				String insert3 = "insert into products values (104,\"Kellogs\",98.0,16.0)";
				String insert4 = "insert into products values (453,\"All Out\",60.0,2.0)";
				String insert5 = "insert into products values (12,\"Nutrela\",38.97,3.68)";
				String insert6 = "insert into products values (679,\"Bourbon\",56.0,9.3)";
				String insert7 = "insert into products values (314,\"Bottle\",45.5,4.5)";
				
				stmt.executeUpdate(stmt2);
				stmt.executeUpdate(tables);
				stmt.executeUpdate(insert1);
				stmt.executeUpdate(insert2);
				stmt.executeUpdate(insert3);
				stmt.executeUpdate(insert4);
				stmt.executeUpdate(insert5);
				stmt.executeUpdate(insert6);
				stmt.executeUpdate(insert7);
				
			}
				else
				System.out.println("CONNECTION FAILED");
			}
		catch(Exception e)
			{
	System.out.println(e.getMessage());	
			}
		}
	}