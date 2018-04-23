package com.bridgelabz.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PreparedStatementPractice {
	static Scanner scanner = new Scanner(System.in);
	static int choice = 0;

	public static void main(String[] args) {

		do {
			System.out.println("**********************Menu**********************");
			System.out.println("1)Create Table in DataBase");
			System.out.println("2)Insert employee details into table");
			System.out.println("3)Update emplyoee in table");
			System.out.println("4)Delete employee from table");
			System.out.println("5)Display employees Details");
			System.out.println("6)Multiple Insert into table");
			System.out.println("7)Exit");
			System.out.println("*************************************************");
			System.out.println("Enter Your choice:");
			choice = scanner.nextInt();
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
				break;

			default:
				break;
			}

		} while (choice != 9);

		scanner.close();

	}

	private static void display() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String selectQuery = "select * from students";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "root");
			preparedStatement = connection.prepareStatement(selectQuery);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("Id=" + resultSet.getInt(1) + " Name=" + resultSet.getString(2) + " Age="
						+ resultSet.getInt(3) + " Course=" + resultSet.getString(4));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private static void delete() {
		int result = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		System.out.println("Enter id of user to be deleted");
		int id = scanner.nextInt();
		String deleteQuery = "delete from students where id=?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "root");
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println(result + " Employee with id=" + id + " deleted!!");

	}

	private static void update() {
		Connection connection = null;
		int result = 0;
		PreparedStatement preparedStatement = null;
		System.out.println("Enter Name to be replaced");
		String replaceName = scanner.next();
		System.out.println("Enter Name from where to replace");
		String name = scanner.next();
		String updatequery = "update students set name=? where name=?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "root");
			preparedStatement = connection.prepareStatement(updatequery);
			preparedStatement.setString(1, replaceName);
			preparedStatement.setString(2, name);
			result = preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		System.out.println(result + " User Updated!!!");
	}

	private static void multipleInsert() {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "insert into students(name,age,course) values(?,?,?)";
		try {
			System.out.println("Enter no of column");
			int rows = scanner.nextInt();
			for (int i = 0; i < rows; i++) {
				System.out.println("Enter Name:");
				String name = scanner.next();
				System.out.println("Enter Age:");
				int age = scanner.nextInt();
				System.out.println("Enter Course:");
				String course = scanner.next();

				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "root");
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, name);
				preparedStatement.setInt(2, age);
				preparedStatement.setString(3, course);
				int result = preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private static void insert() {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		System.out.println("Enter Name:");
		String name = scanner.next();
		System.out.println("Enter Age:");
		int age = scanner.nextInt();
		System.out.println("Enter Course:");
		String course = scanner.next();
		String sql = "insert into students(name,age,course) values(?,?,?)";
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "root");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, course);
			int result = preparedStatement.executeUpdate();
			System.out.println(result + " row inserted successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private static void createTable() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String createQuery = "create table students(id int(4) auto_increment primary key,name varchar(20),age int(10),course int(4))";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "root");
			preparedStatement = connection.prepareStatement(createQuery);
			preparedStatement.execute();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
