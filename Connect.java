//Importing necessary Libraries
import java.sql.Connection;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.util.Arrays;
import java.util.Scanner;
import java.time.LocalDate;  
class account {
	//Declaring Variables
	int acc;
    String name;
    double balance;
    String mobile;
    String address;
    String transType;
    LocalDate date;
	double previousBalance;
	//int ac;
	double amount;
	Scanner sc=new Scanner(System.in);
	//Creating Functions
	public void input(Connection c,PreparedStatement p) throws SQLException {
		//Scanner sc=new Scanner(System.in);
		System.out.print("Enter account number:");
		this.acc=Integer.parseInt(sc.nextLine());
		System.out.print("Enter your name:");
		this.name=sc.nextLine();
		System.out.print("Enter your mobile number:");
		this.mobile=sc.nextLine();
		System.out.print("Enter your address:");
		this.address=sc.nextLine();
		this.balance=0.0;
		this.transType="D";
		this.date=LocalDate.now();
		//System.out.print(this.date);
		//details[4]=balance;
		//n+=1;
		/*p = c.prepareStatement("Insert into Details values(?,?,?,?,?,?,?)");
        p.setInt(1, acc);
        p.setString(2, name);
        p.setDouble(3, balance);
        p.setString(4, mobile);
        p.setString(5, address);
        p.setString(6, transType);
        p.setDate(7, date);*/
		p = c.prepareStatement(
                "Insert into Details values("+acc+",'"+name+"',"+balance+","+
		mobile+",'"+address+"','"+transType+"','"+date+"')");
        boolean b=p.execute();
        if(b==true) {
            System.out.println("1 record inserted...");
        }
	}
	public void deposit(Connection c,PreparedStatement p,Statement s,ResultSet r) throws SQLException{
		System.out.print("Enter your account number:");
		//Scanner sc=new Scanner(System.in);
		this.acc=Integer.parseInt(sc.nextLine());
		r = s.executeQuery("select * from Details where AccountNo="+acc);
		r.next();
		balance=r.getDouble("Balance");
		if(r!=null) {
			System.out.print("Enter amount you want to deposit:");
			this.amount=Double.parseDouble(sc.nextLine());
			if(amount!=0.0) {
				previousBalance=balance;
				double fbalance=balance+amount;
				this.date=java.time.LocalDate.now();
				this.transType="D";
				p=c.prepareStatement("Update Details set Balance="
				+fbalance+", LDoT='"+date+"',TransactionType='"+transType+"' where AccountNo="+acc);
		        boolean b=p.execute();
		        if(b==true) {
		            System.out.println("Record updated...");
		        }
				
			}
		}else {
			System.out.println("Account Not Found!");
		}
        
	}
	public void withdraw(Connection c,PreparedStatement p,Statement s,ResultSet r) throws SQLException {
		//Scanner sc=new Scanner(System.in);
		System.out.println("Enter your account number:");
		this.acc=Integer.parseInt(sc.nextLine());
		//System.out.print("AccNo:"+ac);
		r = s.executeQuery("select * from Details where AccountNo="+acc);
		r.next();
		//System.out.print("R:"+r);
		balance=r.getDouble("Balance");
		//System.out.print("Bal"+balance);
		if(r!=null) {
			System.out.print("Enter amount you want to withdraw:");
			this.amount=Double.parseDouble(sc.nextLine());
			//System.out.print(amount+","+balance);
			if(amount<=balance) {
				//System.out.print("Hello");
				previousBalance=balance;
				//this.balance-=amount;
				this.date=java.time.LocalDate.now();
				this.transType="W";
				double fbalance=balance-amount;
				p=c.prepareStatement("Update Details set Balance="+fbalance+
						", LDoT='"+date+"',TransactionType='"+transType+"' where AccountNo="+acc);
				boolean b=p.execute();
		        if(b==true) {
		            System.out.println("Record updated...");
		        }
				
			}
			else {
				System.out.println("Amount you want to withdraw is more than the balance in your account.");
			}
		}	
		else {
			System.out.println("Account Not Found!");
		}
	}
	public void updateMobile(Connection c,PreparedStatement p,Statement s,ResultSet r) throws SQLException{
		System.out.print("Enter your account number:");
		this.acc=Integer.parseInt(sc.nextLine());
		r = s.executeQuery("select * from Details where AccountNo="+acc);
		if(r!=null) {
			System.out.print("Enter New Mobile Number:");
			this.mobile=sc.nextLine();
			p=c.prepareStatement("Update Details set Mobile="+mobile+" where AccountNo="+acc);
			boolean b=p.execute();
	        if(b==true) {
	            System.out.println("Record updated...");
	        }
			
		}
		else {
			System.out.println("Account Not Found!");
		}
	}
	public void updateAddress(Connection c,PreparedStatement p,Statement s,ResultSet r) throws SQLException {
		System.out.print("Enter your account number:");
		this.acc=Integer.parseInt(sc.nextLine());
		r = s.executeQuery("select * from Details where AccountNo="+acc);
		if(r!=null) {
			System.out.print("Enter New Address:");
			this.address=sc.nextLine();
			p=c.prepareStatement("Update Details set Address='"+address+"' where AccountNo="+acc);
			boolean b=p.execute();
	        if(b==true) {
	            System.out.println("Record updated...");
	        }
			
		}
		else {
			System.out.println("Account Not Found!");
		}
	}
	public void print(Statement s,ResultSet r) throws SQLException  {
		//Scanner sc=new Scanner(System.in);
		System.out.println("Information:");
		System.out.print("Enter your account number:");
		this.acc=Integer.parseInt(sc.nextLine());
		r = s.executeQuery(
                "select * from Details where AccountNo="+acc);
		r.next();
        name = r.getString("Name").trim();
        balance=r.getDouble("Balance");
        mobile = r.getString("Mobile").trim();
        address=r.getString("Address");
        transType = r.getString("TransactionType").trim();
        String date=r.getString("LDoT");
	
		System.out.println("Account Number:"+acc+" Name:"+name+" Mobile Number:"+mobile+" Address:"+address+
				" Balance:"+balance+" Transaction Type:"+transType+" Last date of Transaction:"+date);
	}
	public void printall(Statement s,ResultSet r) throws SQLException  {
		r = s.executeQuery(
                "select * from Details");
		//r.next();
        while (r.next()) {
        	acc=r.getInt("AccountNo");
			name = r.getString("Name").trim();
			balance=r.getDouble("Balance");
			mobile = r.getString("Mobile").trim();
			address=r.getString("Address");
			transType = r.getString("TransactionType").trim();
			String date=r.getString("LDoT");

            System.out.println("Account Number:"+acc+" Name:"+name+" Mobile Number:"+mobile+" Address:"+address+
     				" Balance:"+balance+" Transaction Type:"+transType+" Last date of Transaction:"+date);
        }
       
	}
}

public class Connect {
    public static void main(String arg[])
    {
        Connection connection = null;
        Statement statement;
        //statement = connection.createStatement();
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank",
            		"root", "College@123");
 
            // mydb is database
            // mydbuser is name of database
            // mydbuser is password of database
 
            //Statement statement;
            statement = connection.createStatement();
            //ResultSet resultSet=null;
            //PreparedStatement preparedStatement=null;
            //preparedStatement = connection.prepareStatement(null);
            while(true){
    			account b1=new account();
    			System.out.println("1.Open an Account");
    			System.out.println("2.Deposit");
    			System.out.println("3.Withdraw");
    			System.out.println("4.Update your new Mobile Number");
    			System.out.println("5.Update your new Address details");
    			System.out.println("6.Show particular account details");
    			System.out.println("7.Show all account details");
    			System.out.print("Select yor input:");
    			Scanner sc=new Scanner(System.in);
    			int s=Integer.parseInt(sc.nextLine());
    			switch (s) {
    			case 1:
    				b1.input(connection,preparedStatement);
    				break;
    			case 2:
    				b1.deposit(connection,preparedStatement,statement, resultSet);
    				break;
    			case 3:
    				b1.withdraw(connection,preparedStatement,statement, resultSet);
    				break;
    			case 4:
    				b1.updateMobile(connection, preparedStatement,statement, resultSet);
    				break;
    			case 5:
    				b1.updateAddress(connection, preparedStatement,statement, resultSet);
    				break;
    			case 6:
    				b1.print(statement, resultSet);
    				break;
    			case 7:
    				b1.printall(statement, resultSet);
    				break;
    			default:
    				System.out.println("No Input!!");
    			}
    			System.out.print("Do you want to continue?(yes/no)");
    			String a=sc.nextLine();
    			if(a.equalsIgnoreCase("no")) {
    				break;
    			}
            }
            
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    } // function ends
} // class ends



