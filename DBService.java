import java.sql.*;
import java.util.Scanner;
import java.io.*;

class DBService
	{
	static Connection con;
	
	static 
		{
		Console cnsl = null;
		try
			{
			String user = "";
			String pass;
			
			Scanner scService = new Scanner(System.in);
			
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Enter your mySql Username:");
			user=scService.next();
			cnsl=System.console();
			char[] pswd = cnsl.readPassword("Enter your mySql Password: \n");
			pass = new String(pswd);
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cart",user,pass);
			if(con!=null)
				System.out.println("CONNECTION SUCCESSFUL!");
				else
				System.out.println("CONNECTION FAILED!");
			}
			catch(ClassNotFoundException g){
				System.out.println(g.getMessage()+" : missing Driver");
				System.exit(0);
			}
			catch(Exception e)
			{
			System.out.println(e.getMessage());
			System.exit(0);
			}
		}
		
	}	