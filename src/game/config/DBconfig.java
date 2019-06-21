package game.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Kelas konfigurasi database
public class DBconfig {
	
	private static Connection mysqlconfig;
	// open koneksi ke database
	public static Connection openConnection() throws SQLException{
		try {
			String url="jdbc:mysql://localhost:3306/tmd_pbo2019";
			String user="root";
			String pass="";
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			mysqlconfig=DriverManager.getConnection(url, user, pass);
		}catch(Exception e) {
			System.err.println("koneksi gagal "+e.getMessage());
		}
		return mysqlconfig;
	}
}
