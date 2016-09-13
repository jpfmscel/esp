
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoDB {

	private static String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_CONNECTION;
	private static String DB_USER;
	private static String DB_PASSWORD;

	public static void insertRecordIntoDbUserTable(String status) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;

		String insertTableSQL = "INSERT INTO ACAO" + "(device_ID, hora, status) " + "VALUES" + "(1," + "NOW(),'" + status + "')";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(insertTableSQL);

			// execute insert SQL stetement
			statement.executeUpdate(insertTableSQL);
			System.out.println("Record is inserted into ACAO table!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}

	public static String getLatest(String device_id) throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String selectTableSQL = "SELECT status from ACAO where device_id = " + device_id + " ORDER BY id DESC LIMIT 1";
		String status = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);

			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {
				status = rs.getString("status");
				break;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return status;
	}

	private static Connection getDBConnection() {

		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("mac") >= 0) {
			DB_CONNECTION = "jdbc:mysql://localhost:3306/esp";
			DB_USER = "root";
			DB_PASSWORD = "root";
		} else {
			DB_CONNECTION = "jdbc:mysql://vetz.caubzvqegnle.us-west-2.rds.amazonaws.com:3306/esp";
			DB_USER = "vetz";
			DB_PASSWORD = "rootroot";
		}

		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return dbConnection;
	}

}
