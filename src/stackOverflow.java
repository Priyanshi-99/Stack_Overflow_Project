import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class stackOverflow {
	static Scanner sc = new Scanner(System.in);
	static Connection con;
	static void addRecord(){
		try{
			System.out.println("Enter Student Id: ");
			int id =Integer.parseInt(sc.nextLine());
			System.out.println("Enter Student Name: ");
			String name =sc.nextLine();
			System.out.println("Enter Student Address: ");
			String add =sc.nextLine();
			PreparedStatement pst=con.prepareStatement("insert into emp values(?,?,?)");
			pst.setInt(1, id);
			pst.setString(2, name);
			pst.setString(3, add);
			int i=pst.executeUpdate();
			if(i>0)
				System.out.print("DATA INSERTED");
			else
				System.out.print("DATA NOT INSERTED");
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		
	}
	static void showRecord(){
		try{
			Statement st=con.createStatement();
			String max="";
			int id=0;
			String name="";
			ResultSet rs1=st.executeQuery("select max(reputation) b from users");
					if(rs1.next())
					{
						max=rs1.getString("b");
					}
			PreparedStatement pst=con.prepareStatement("select * from users where reputation=?");
			pst.setString(1,max);
			ResultSet rs2=pst.executeQuery();
			if(rs2.next())
			{
				id=rs2.getInt("id");
				name=rs2.getString("displayname");
			}
			pst=con.prepareStatement("select * from posts where owneruserid=?");
			pst.setInt(1,id);
			ResultSet rs3=pst.executeQuery();
			int count=0;
			if(rs3.next())
			{
				
				count++;
				System.out.println(rs3.getInt("posttype")+"\t"+rs3.getString("creationdate")+"\t"+rs3.getInt("viewcount"));
			}
			System.out.println(name);
			System.out.println("total no of posts"+count);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			
		}
		
	}	
	
	static void view()
	{
		try{
		Statement st=con.createStatement();
		ResultSet rs1=st.executeQuery("select avg(age) n from users");
		if(rs1.next())
		{
			double c=rs1.getDouble("n");
			System.out.println(rs1.getDouble((int) c));
		}
		
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] ar){
		try{
			int ch;
			Class.forName("org.apache.hive.jdbc.HiveDriver");
		con=DriverManager.getConnection("jdbc:hive2://localhost:10000/demo","","");
		if(con!=null){
			
			System.out.println("connection is ok!");
		}
		do{
		System.out.println("1. addrecord\n 2. showrecord\n 3. view\n 4.searchrecord\n 5.enter your choice");
		ch= Integer.parseInt(sc.nextLine()); 
		switch(ch){
		case 1:
			addRecord();
			break;
		case 2:
			showRecord();
			break;
		case 3:
			view();
		default:
			break;
		}
			
		}while(ch!=3);
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
