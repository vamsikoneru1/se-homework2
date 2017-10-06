package com.hm2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.mysql.jdbc.Driver;
import com.sun.net.httpserver.Authenticator.Failure;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import javax.faces.bean.ManagedBean;

@ManagedBean
@SessionScoped
public class Login {
	
	Integer userid;
	private String password;
	private String email;
	private String firstname; 
	private String username; 
	
	
	

	
			
	public Integer getUserid() {
		return userid;
	}



	public void setUserid(Integer userid) {
		this.userid = userid;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getFirstname() {
		return firstname;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String login() {
		Connection con=null;
		String res="";
		if(this.username.equals(null)&& this.password.equals(null))
		{
				return "failure";
		}
		else
		{
		try {
			// Setup the DataSource object
			System.out.println("Inside database");
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(System.getenv("ICSI518_SERVER"));
			ds.setPortNumber(3306);
			//ds.setDatabaseName(System.getenv("ICSI518_DB"));
			ds.setDatabaseName("se_proj");
			ds.setUser("root");
			ds.setPassword("prasad");
			
			con = ds.getConnection();
			
			String sql = "SELECT username,password from reg where username= ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, this.username);
			//st.setString(1, this.password);
			 
			
			ResultSet rs = st.executeQuery();
			String un="";
			String pass="";
			
			while (rs.next()) {
				un=rs.getString("username");
				pass=rs.getString("password");
				
				if(pass.equals(password)&& un.equals(username))
				{
					res="sucess";
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
				}
				else
				{
					res="failure";
				}
				//rs.close();
				
			}
//				System.out.println("First Name is: " + rs.getString("first_name"));
//				this.firstName = rs.getString("first_name");
			
			
		}catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				if(con!=null)
					con.close();
			} catch (SQLException e) {

			}
			return res;
	}
		}
	}



	//}
	public String Regi() throws SQLException {
		//Connection con = DriverManager.getConnection("jdbc:mysql://seproj:3306/se_proj", "root", "prasad");
		Connection con=null;
		String s="";
		if(this.email.contains("@")  && (this.email.contains(".com")||this.email.contains(".edu")))
		{
		try {
			// Setup the DataSource object
			
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(System.getenv("ICSI518_SERVER"));
			ds.setPortNumber(3306);
			//ds.setDatabaseName(System.getenv("ICSI518_DB"));
			ds.setDatabaseName("se_proj");
			ds.setUser("root");
			ds.setPassword("prasad");
			
			
//			ds.setServerName(System.getenv("ICSI518_SERVER"));
//			ds.setPortNumber(3306);
//			ds.setDatabaseName("ICSI518_DB");
			//ds.setUser(System.getenv("ICSI518_USER"));
			//ds.setPassword(System.getenv("ICSI518_PASSWORD"));


			// Get a connection object
			con = ds.getConnection();

			// Get a prepared SQL statement
			//String sql = "INSERT INTO Registration "+"VALUES (4251, 'Zara', 'Ali@gmail.com', 'koneru','vamsikrishna')";
			String sql = "INSERT INTO reg "+"(userid, name, email, password,username) values (?,?,?,?,?)";
			//Statement st = con.createStatement();
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, userid);
			st.setString(2 ,firstname);
			st.setString(3, email);
			st.setString(4, password);
			st.setString(5, username);
			

			st.executeUpdate();
			s="true";
			System.out.println("Inserted");
			

			
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				if(con!=null)
					con.close();
			} catch (SQLException e) {
			}
		}
		
		//return "regsuc";
		
		{
		return "regsuc?faces-redirect=true";
		}
		
	}
		else
			return "failure2";
	

	}
	}
