package com.bridgelabz.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StatementPractice {
	
	static Scanner scanner=new Scanner(System.in);
	
	public static void main(String[] args) {
		int choice=0;
	
		
		do{
			System.out.println("**********************Menu**********************");
			System.out.println("1)Create Table in DataBase");
			System.out.println("2)Insert employee details into table");
			System.out.println("3)Update emplyoee in table");
			System.out.println("4)Delete employee from table");
			System.out.println("5)Display employees Details");
			System.out.println("6)Multiple insert");
			System.out.println("9)Exit");
			System.out.println("*************************************************");
			System.out.println("Enter Your choice:");
			choice=scanner.nextInt();
			switch (choice) {
			case 1:
				createTable();
				break;
				
			case 2:
				insert();
				break;
				
			case 3:
				update();
				break;
				
			case 4:
				delete();
				break;
				
			case 5:
				display();
				break;
				
			case 6:
				multipleInsert();

			default:
				break;
			}
		
			
		}while(choice!=9);
	
		scanner.close();
	}
	
	private static void display() {
		Connection connection=null;
		Statement statement=null;
		String selectQuery="select * from employees";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","root");
			statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(selectQuery);
			while(resultSet.next())
			{
				System.out.println("Id="+resultSet.getInt(1)+
				" Name="+resultSet.getString(2)+
				" Salary="+resultSet.getInt(3)+
				" Department Id="+resultSet.getInt(4));
			}
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(connection!=null)
			{
				try 
				{
					connection.close();
				} 
				catch (SQLException e) 
				{	
					e.printStackTrace();
				}
			}
			if(statement!=null)
			{
				try 
				{
					statement.close();
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		
	}

	public static void createTable()
	{
		Connection connection=null;
		Statement statement=null;
		String createQuery="create table employees(id int(4) auto_increment primary key,name varchar(20),salary int(10),department_id int(4))";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","root");
			statement=connection.createStatement();
			statement.execute(createQuery);	
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(connection!=null)
			{
				try 
				{
					connection.close();
				} 
				catch (SQLException e) 
				{	
					e.printStackTrace();
				}
			}
			if(statement!=null)
			{
				try 
				{
					statement.close();
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void insert()
	{
		Connection connection=null;
		Statement statement=null;
		System.out.println("Enter Name:");
		String name=scanner.next();
		System.out.println("Enter Salary:");
		int salary=scanner.nextInt();
		System.out.println("Enter Department Id:");
		int department_id=scanner.nextInt();
		String sql="insert into employees(name,salary,department_id) values"+"('"+name+"',"+salary+","+department_id+")";
		try 
		{
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","root");
			statement=connection.createStatement();
			int result=statement.executeUpdate(sql);
			System.out.println(result+" row inserted successfully");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(connection!=null)
			{
				try 
				{
					connection.close();
				} 
				catch (SQLException e) 
				{
					
					e.printStackTrace();
				}
			}
			if(statement!=null)
			{
				try 
				{
					statement.close();
				} 
				catch (SQLException e) 
				{		
					e.printStackTrace();
				}
			}
		}
	}
	public static void multipleInsert()
	{
		Connection connection=null;
		Statement statement=null;
		
		
		try 
		{
			System.out.println("Enter no of rows to be inserted");
			int rows=scanner.nextInt();
			
			for(int i=0;i<rows;i++)
			{
				System.out.println("Enter Name:");
				String name=scanner.next();
				System.out.println("Enter Salary:");
				int salary=scanner.nextInt();
				System.out.println("Enter Department Id:");
				int department_id=scanner.nextInt();
				String sql="insert into employees(name,salary,department_id) values"+"('"+name+"',"+salary+","+department_id+")";
			
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","root");
			statement=connection.createStatement();
			int result=statement.executeUpdate(sql);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(connection!=null)
			{
				try 
				{
					connection.close();
				} 
				catch (SQLException e) 
				{
					
					e.printStackTrace();
				}
			}
			if(statement!=null)
			{
				try 
				{
					statement.close();
				} 
				catch (SQLException e) 
				{		
					e.printStackTrace();
				}
			}
		}
		
	}
	public static void delete()
	{
		int result=0;
		Connection connection = null;
		Statement statement = null;
		System.out.println("Enter id of user to be deleted");
		int id=scanner.nextInt();
		String deleteQuery="delete from employees where id="+id;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","root");
			statement=connection.createStatement();
			result=statement.executeUpdate(deleteQuery);
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if(connection!=null)
			{
				try 
				{
					connection.close();
				} 
				catch (SQLException e) 
				{
					
					e.printStackTrace();
				}
			}
			if(statement!=null)
			{
				try 
				{
					statement.close();
				} 
				catch (SQLException e) 
				{		
					e.printStackTrace();
				}
			}
				scanner.close();
		}
		
		System.out.println(result+" Employee with id="+id+" deleted!!");
	}
	
	
	public static void update()
	{
		Connection connection=null;
		int result=0;
		Statement statement=null;
		System.out.println("Enter Name to be replaced");
		String replaceName=scanner.next();
		System.out.println("Enter Name from where to replace");
		String name=scanner.next();
		String updatequery="update employees set name='"+replaceName+"' where name='"+name+"'";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","root");
			statement=connection.createStatement();
			result=statement.executeUpdate(updatequery);	
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(connection!=null)
			{
					
				try 
				{
					connection.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				
			}
			if(statement!=null)
			{
				try 
				{
					statement.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
		System.out.println(result+" User Updated!!!");
	}

}
