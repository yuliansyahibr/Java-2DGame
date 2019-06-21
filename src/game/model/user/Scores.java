package game.model.user;

import java.sql.Connection;
import java.sql.SQLException;

import game.config.DBconfig;


// class model untuk tabel tpeace
public class Scores {
	
	// get all
	public java.sql.ResultSet getAll(){
		
		java.sql.ResultSet res = null;		
		try {
			String sql = "SELECT * FROM tpeace ORDER BY peace DESC";
			Connection conn=(Connection)DBconfig.openConnection();
			java.sql.Statement stm = conn.createStatement();
			res=stm.executeQuery(sql);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	// insert user baru
	public void insert(String username, int peace) {
		try {
			Connection conn = (Connection)DBconfig.openConnection();			
			String sql = "INSERT INTO tpeace (username, peace) VALUES('";
			sql += username + "', '" + peace + "')";			
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);			
			pst.execute();			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// cari username
	public boolean findUsername(String username) {
		
		boolean result = false;		
		try {			
			Connection conn = (Connection)DBconfig.openConnection();			
			java.sql.Statement stm = conn.createStatement();
			java.sql.ResultSet res;
			res = stm.executeQuery("SELECT COUNT(username) FROM tpeace WHERE username='"+username+"'");
			res.next();			
			int count = Integer.parseInt(res.getString(1));
			if(count == 1) {
				result = true;
			}			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// update skor
	public void update(String username, int peace) {
		try {
			Connection conn = (Connection)DBconfig.openConnection();			
			int skor = getSkor(conn, username);			
			if(skor >= peace) return;			
			String sql = "UPDATE tpeace SET peace="+peace; 
			sql += " WHERE username='"+username+"'";			
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);			
			pst.execute();			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// get skor user
	public int getSkor(Connection conn, String username) {
		
		int skor=0;		
		try {			
			java.sql.Statement stm = conn.createStatement();
			java.sql.ResultSet res;
			res = stm.executeQuery("SELECT peace FROM tpeace WHERE username='"+username+"'");
			res.next();			
			skor = res.getInt(1);			
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		return skor;
	}
	
}





