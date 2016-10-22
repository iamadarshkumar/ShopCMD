import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.awt.*;

class Cart{
	static Connection con;
	static ResultSet rs;
	static PreparedStatement pst,dst;
	static FileOutputStream fos;
	static String billdetails = "Product name\tProduct Price\tDiscount(%)\n";
	
	static void addRecord()
			{
		try{
		Scanner sc=new Scanner(System.in);
		pst=con.prepareStatement("insert into products values(?,?,?,?)");

		System.out.println("Enter Product ID:");
		int pid=Integer.parseInt(sc.nextLine());
		System.out.println("Enter Product name:");
		String pname=sc.nextLine();
		System.out.println("Enter Product price:");
		float prp=Float.parseFloat(sc.nextLine());
		System.out.println("Enter discount on the product:");
		float dscnt=Float.parseFloat(sc.nextLine());

		pst.setInt(1,pid);
		pst.setString(2,pname);
		pst.setFloat(3,prp);
		pst.setFloat(4,dscnt);
		
		int i=pst.executeUpdate();
		if(i>0)
		System.out.println("DATA INSERTED....");
		else
		System.out.println("DATA NOT INSERTED...!!!!");
			}
			catch(Exception e)
			{
				System.out.println("Error "+e.getMessage());
			}	
		}
		
		static void showRecord()
			{
			try
				{
			pst=con.prepareStatement("select * from products");
			rs=pst.executeQuery();
			System.out.println("Product ID\tProduct Name\tProduct Price\tDiscount\n");
		while(rs.next())
		{
	System.out.print(rs.getInt("Product_ID")+"\t\t");
	System.out.print(rs.getString("Product_Name")+"\t\t");
	System.out.print(rs.getFloat("Product_price")+"\t\t");
	System.out.print(rs.getFloat("Discount")+"%"+"\n");
		}
				}
			catch(Exception e)
				{
					
				}
			}
			
		static float addToCart(int id){
			float final_amt=0.0f;
			float val=0.0f;
			float disc=0.0f;
			String prname="";
			try{
				
				pst=con.prepareStatement("select Product_Name,Product_price,Discount from products where Product_ID="+id);
				rs=pst.executeQuery();
				while(rs.next())
				{
					val=rs.getFloat("Product_price");
					disc=rs.getFloat("Discount");
					final_amt=val-(disc*val/100);
					prname=rs.getString("Product_Name");
					try{
					fos.write((prname+"\t\t\t"+val+"\t\t\t"+disc+"\n").getBytes());
					}catch(Exception e){}
					
				}
				/*else
				{
				val=0.0f;
				}
				*/
			}
			catch(Exception e){
				
			}
			return final_amt;
		}
		
	public static void main(String ...ar)
		{
		int pid;
		float sum=0.0f,isum=0.0f;
		Console cnsl1 = null;
		try{
		fos=new FileOutputStream("Bill.txt");
		fos.write(billdetails.getBytes());
		fos.write("--------------------------------------------\n".getBytes());
		}catch(Exception e){
		
		}
		con=DBService.con;
		showRecord();
		System.out.println("\n-----------------------GUIDELINES--------------------------");
		System.out.println("1.Enter, in every line, the Product Id of the product you wish to buy.");
		System.out.println("2.From anywhere on the screen, Enter \"checkout\" to checkout.");
		System.out.println("3.To update items, Enter \"login\". (Authentication Required)\n");
		
		do{
		System.out.println("\nEnter the product ID to add to your cart, \"login\", or \"checkout\"");
		Scanner rqst = new Scanner(System.in);
		String rq=rqst.nextLine();
		try{
			if(rq.equalsIgnoreCase("login")){
				String uname="";
				System.out.println("Enter admin username:");
				uname=rqst.next();
				cnsl1=System.console();
				char[] pswd = cnsl1.readPassword("Enter the Password: \n");
				String pswrd = new String(pswd);
				if(uname.equals("admin")&&pswrd.equals("admin123"))
				{
				System.out.println("\nAuthentication successful");
				addRecord();
				System.out.println("\nUpdated List:");
				showRecord();
				}
				else
				{
					System.out.println("Invalid username or password");
					System.out.println("****************************************\n");
				}
			}
			else if(rq.equalsIgnoreCase("checkout")){
				System.out.println("\nYour cart:");
				
				FileInputStream fis = new FileInputStream("Bill.txt");
		
				int i=0;
				String data="";
				
				while((i=fis.read())>-1)
					data+=(char)i;
				System.out.println(data);
				System.out.println("Your total amount after discount is: "+sum);
				System.out.println("\nPlease confirm the above products and press Y/N");
				String confirm = rqst.next();
				if (confirm.equalsIgnoreCase("y"))
				{
				fos.write("********************************************".getBytes());
				fos.write(("\nYour total amount after discount is "+sum).getBytes());
				fos.write("\n***********THANKS FOR SHOPPING***************".getBytes());
				System.out.println();
				System.out.println("Thank you for purchasing items worth "+sum);
				System.out.println("\nBill generated in file Bill.txt");
				System.out.println("*****************Thanks for shopping***********************");
				Desktop.getDesktop().open(new File(".\\Bill.txt"));
				System.exit(0);
				}
				else if(confirm.equalsIgnoreCase("n")){
					System.out.println();
					main();		//reload product contents of file "Bill.txt"
				}
				else
				{
					System.out.println("Invalid Input");
				}
			}	
		
	else{

		pid=Integer.parseInt(rq);
		isum=sum;
		sum=sum+addToCart(pid);
		if (isum==sum)
		{
			System.out.println("Invalid input");
		}
		else
			System.out.println("The total price after discount is "+sum);
		
		
		}
			}
			catch(IOException i){
				System.out.println("Unable to open file");
			}
			catch(Exception e){
					System.out.println("Invalid input");
				}
		
		}while(true);
		}

}