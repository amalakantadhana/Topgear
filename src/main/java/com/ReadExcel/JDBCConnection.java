package com.ReadExcel;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCConnection {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		@SuppressWarnings("unused")
		JDBCConnection jdbc= new JDBCConnection();
		Connection conn = null;
		String dbName = "MyCHC_New";
		String serverip="10.159.34.103";
		String serverport="1433";
		String url = "jdbc:sqlserver://"+serverip+"\\SQLEXPRESS:"+serverport+";databaseName="+dbName+"";
		java.sql.Statement stmt = null;
		ResultSet result = null;
	
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String databaseUserName = "Appuser";
		String databasePassword = "123";
		
	
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
			stmt = conn.createStatement();
			result = null;
			String City_ID,City_Name,Country_IDN;


			conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: " + dm.getDatabaseProductName());
				System.out.println("Product version: " + dm.getDatabaseProductVersion());
			}

			
		//	stmt.executeUpdate("INSERT INTO dbo.Registration " + "VALUES ('Firstname', 'Majji','test4777@gmail.com','123456','25413697', 'M', 'Hyderabad', '500032', '1111111111','103')");
		//	stmt.executeUpdate("INSERT INTO dbo.Registration (FirstName, LastName, Email,Password,dob,gender,location,zipcode,zipcode,Role_ID) values ("+"'sFirstName'"+", '+sLastName+', '+semail+','123456','45871032', 'M', 'Hyderabad', '500082', '1111111111','+sRole_ID+')");
			
			



result = stmt.executeQuery("select * from dbo.City");
			
			System.out.println("City_IDN	"	+ "City_Name	"+"Country_IDN		");
			while(result.next()){
				City_ID=result.getString("City_IDN");
				City_Name = result.getString("City_Name");
				Country_IDN=result.getString("Country_IDN");
					
				
				System.out.println(City_ID+"		"+City_Name+"		"+Country_IDN+" " );
				
							
				
				
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
