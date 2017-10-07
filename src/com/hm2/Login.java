package com.hm2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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
			HttpSession hs= Util.getSession();
			
			// Setup the DataSource object
			System.out.println("Inside database");
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(System.getenv("ICSI518_SERVER"));
			ds.setPortNumber(Integer.parseInt(System.getenv("ICSI518_PORT")));
			ds.setDatabaseName(System.getenv("ICSI518_DB").toString());
			ds.setUser(System.getenv("ICSI518_USER").toString());
			ds.setPassword(System.getenv("ICSI518_PASSWORD").toString());
			
			
			con = ds.getConnection();
			
			String sql = "SELECT name,username,password from reg where username= ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, this.username);
			 
			
			ResultSet rs = st.executeQuery();
			String un="";
			String pass="";
			
			while (rs.next()) {
				un=rs.getString("username");
				pass=rs.getString("password");
				
				if(pass.equals(password)&& un.equals(username))
				{
					res="sucess";
					hs.setAttribute("username", username);
					
				}
				else
				{
					FacesMessage fm= new FacesMessage("Login error", "ERROR MESSAGE");
					fm.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage(null	, fm);
					//res="failure";
				}
				
				
			}
			
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
		
		Connection con=null;
		String s="";
		if(this.email.contains("@")  && (this.email.contains(".com")||this.email.contains(".edu")))
		{
		try {
			// Setup the DataSource object
			
			
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(System.getenv("ICSI518_SERVER"));
			ds.setPortNumber(Integer.parseInt(System.getenv("ICSI518_PORT")));
			ds.setDatabaseName(System.getenv("ICSI518_DB").toString());
			ds.setUser(System.getenv("ICSI518_USER").toString());
			ds.setPassword(System.getenv("ICSI518_PASSWORD").toString());
			
			
			con = ds.getConnection();

			
			String sql = "INSERT INTO reg "+"(userid, name, email, password,username) values (?,?,?,?,?)";
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
		
		
		
		
		return "login?faces-redirect=true";
		
		
	}
		else
			return "failure2";
	

	}
	
	public String logout() {
		HttpSession hs=Util.getSession();
		hs.invalidate();
		return "login";
	}
	}
