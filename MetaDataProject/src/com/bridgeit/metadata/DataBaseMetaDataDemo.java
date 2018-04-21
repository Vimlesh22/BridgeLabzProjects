package com.bridgeit.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseMetaDataDemo {

	public static void main(String[] args) 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false","root","root"); 

			DatabaseMetaData databaseMetaData=connection.getMetaData(); 
			System.out.println("Driver Name: "+databaseMetaData.getDriverName());  
			System.out.println("Driver Version: "+databaseMetaData.getDriverVersion());  
			System.out.println("UserName: "+databaseMetaData.getUserName());  
			System.out.println("Database Product Name: "+databaseMetaData.getDatabaseProductName());  
			System.out.println("Database Product Version: "+databaseMetaData.getDatabaseProductVersion()); 
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}  

	}
}

