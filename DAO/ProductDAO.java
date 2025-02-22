package controller.DAO;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;

import Java.core.classcode.Product;

public class ProductDAO {

	private Connection myConn;
	DecimalFormat df = new DecimalFormat("#.00"); 

	public ProductDAO() throws Exception {

		// Get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("sql/database.properties"));

		String user = props.getProperty("username");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");

		this.myConn = DriverManager.getConnection(dburl, user, password);

		System.out.println("Connceted successfully to " + dburl);
	}

	// Add New Product items
	public void addProduct(Product product) throws Exception {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("INSERT INTO products"
					+ " (`id`, `tag`, `name`, `company`, `cost_price`,`selling_price`,`profit`, `status`)" + " VALUES (NULL, ?,?,?,?,?,?,?)");

			myStmt.setString(1, product.getTag());
			myStmt.setString(2, product.getName());
			myStmt.setString(3, product.getCompany());
			myStmt.setDouble(4, product.getCp());
			myStmt.setDouble(5, product.getSp());
			myStmt.setDouble(6, product.getProfit());
			myStmt.setBoolean(7, product.getStatus());

			myStmt.executeUpdate();

		} finally{
			if(myStmt !=null)
				myStmt.close();
		}
	}


	// Get All product
	public ArrayList<Product> getAllProduct() throws Exception {
		ArrayList<Product> list = new ArrayList<Product>();

		Statement myStmt = null;
		ResultSet myRst = null;

		try {
			myStmt = myConn.createStatement();
			myRst = myStmt.executeQuery("select * from products");

			while (myRst.next()) {
				Product tempEmployee = converRowToProduct(myRst);
				list.add(tempEmployee);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myStmt, myRst);
		}
		return list;

	}

	// Search Product
	public ArrayList<Product> searchProduct(String name) throws SQLException {
		ArrayList<Product> list = new ArrayList<Product>();

		java.sql.PreparedStatement mystmt = null;
		ResultSet myRst = null;

		try {
			name += "%";
			mystmt = myConn.prepareStatement("SELECT * FROM products WHERE name LIKE ?");

			mystmt.setString(1, name);

			myRst = mystmt.executeQuery();

			while (myRst.next()) {
				Product tmp = converRowToProduct(myRst);
				list.add(tmp);
			}
			return list;
		} finally {
			close(mystmt, myRst);
		}
	}

	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);
	}

	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {

		}

		if (myConn != null) {
			myConn.close();
		}
	}

	private Product converRowToProduct(ResultSet myRst) throws SQLException {

		int id = myRst.getInt("id");
		String tag = myRst.getString("tag");
		String name = myRst.getString("name");
		String company = myRst.getString("company");
		double cprice = myRst.getDouble("cost_price");
		double sprice = myRst.getDouble("selling_price");
		boolean status = myRst.getBoolean("status");

		Product product = new Product(id, tag, name, company, cprice,sprice, status);
		return product;
	}

	public void UpdateProduct(Product product) throws SQLException{
		PreparedStatement myStmt =null;
		
		try {
			myStmt=myConn.prepareStatement("UPDATE products"+
					" set name= ?, company=?, cost_price=?, selling_price=?, status=?"+
					" where tag=?");
			
			myStmt.setString(1, product.getName());
			myStmt.setString(2, product.getCompany());
			myStmt.setDouble(3, product.getCp());
			myStmt.setDouble(4, product.getSp());
			myStmt.setDouble(5, product.getProfit());
			myStmt.setString(6, product.getTag());
			
			myStmt.executeUpdate();
			
			System.out.println(myStmt.getWarnings());
		} finally{
			myStmt.close();
		}
	}
	
	public String getTotalItems() throws SQLException{
		Statement myStmt = null;
		ResultSet myRst = null;
		String total=null;
		
		try {
			myStmt = myConn.createStatement();
			myRst = myStmt.executeQuery("SELECT COUNT(*) FROM products");
			while(myRst.next())
				total=myRst.getString("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRst);
		}
		return total;
		
	}
	
	
	public String getAvaiItems() throws SQLException{
		Statement myStmt = null;
		ResultSet myRst = null;
		String avai=null;
		
		try {
			myStmt = myConn.createStatement();
			myRst = myStmt.executeQuery("SELECT COUNT(*) FROM products where status=1");
			while(myRst.next())
				avai=myRst.getString("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRst);
		}
		return avai;
	}
	
	public String getSoldItems() throws SQLException{
		Statement myStmt = null;
		ResultSet myRst = null;
		String sold=null;
		
		try {
			myStmt = myConn.createStatement();
			myRst = myStmt.executeQuery("SELECT COUNT(*) FROM products where status=0");
			while(myRst.next())
				sold=myRst.getString("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRst);
		}
		return sold;
	}
	
	public String getTotalInvest() throws SQLException{
		Statement myStmt = null;
		ResultSet myRst = null;
		String totalInv =null;
		
		
		try {
			myStmt = myConn.createStatement();
			myRst = myStmt.executeQuery("SELECT SUM(cost_price) FROM products");
			while(myRst.next())
				totalInv=df.format(myRst.getDouble("sum(cost_price)")).toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRst);
		}
		return totalInv;
	}
	
	public String getTotalSell() throws SQLException{
		Statement myStmt = null;
		ResultSet myRst = null;
		String totalsell = null;
		
		try {
			myStmt = myConn.createStatement();
			myRst = myStmt.executeQuery("SELECT SUM(selling_price) FROM products");
			while(myRst.next())
				totalsell=df.format(myRst.getDouble("sum(selling_price)")).toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRst);
		}
		return totalsell;
	}
	
	public String getProfit() throws SQLException{
		Statement myStmt = null;
		ResultSet myRst = null;
		String profit =null;
		
		try {
			myStmt = myConn.createStatement();
			myRst = myStmt.executeQuery("SELECT SUM(profit) FROM products");
			while(myRst.next())
				profit=df.format(myRst.getDouble("sum(profit)")).toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(myStmt, myRst);
		}
		return profit;
	}
}
