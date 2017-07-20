package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;

	/**
	 * to load the database base driver
	 * 
	 * @return a database connection
	 */
	private static Connection loadDriver() {
		try {
			Class.forName(Configuration.DB_DRIVER);
			connection = DriverManager.getConnection(Configuration.DB_URL,
					Configuration.DB_USER, Configuration.DB_PASSWORD);
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			closeConnection();
		}
		System.out.println("Opened database successfully");
		return connection;
	}

	/**
	 * to get a result set of a query
	 * 
	 * @param query
	 *            custom query
	 * @return a result set of custom query
	 */
	public static ResultSet getResultSet(String query) {
		connection = loadDriver();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			closeConnection();
		}

		System.out.println("Run query " + query);

		return resultSet;
	}

	/**
	 * to run an update query such as update, delete
	 * 
	 * @param query
	 *            custom query
	 */
	public static int runQuery(String query) {
		connection = loadDriver();
		int rowAffected = 0;
		try {
			statement = connection.createStatement();
			rowAffected = statement.executeUpdate(query);
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		System.out.println("Run query " + query);
		return rowAffected;
	}

	public static int[] runQueries(String... queries) {
		connection = loadDriver();
		int[] rowAffected = null;
		try {
			for (String query : queries) {
				statement = connection.createStatement();
				statement.addBatch(query);
			}
			statement.executeBatch();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		System.out.println("Run queries");
		return rowAffected;
	}

	public static void closeConnection() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Closed database successfully");
	}
}
