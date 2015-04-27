import java.lang.AutoCloseable;import java.lang.Class;import java.lang.ClassNotFoundException;import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


    public class DatabaseConnector implements AutoCloseable {
    public Connection connect = null;
    private boolean isOpen = false;

    public DatabaseConnector() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        open();
    }

    // TODO: constants and error handling
    public void open() throws SQLException {
        if (!isOpen) {
            try {
                connect = DriverManager.getConnection("jdbc:mysql://localhost/webcom" + "user=root");
                isOpen = true;
            } catch (SQLException ex) {
                ex.printStackTrace();
                isOpen = false;
                throw ex;
            }
        }
    }

    public void close() {
        try {
            if (isOpen) {
                connect.close();
                isOpen = false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            isOpen = true;
        }
    }
}
