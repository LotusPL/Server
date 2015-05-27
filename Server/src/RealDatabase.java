import java.lang.AutoCloseable;import java.lang.Class;import java.lang.ClassNotFoundException;
import java.sql.*;
import java.util.List;


public class RealDatabase implements Database {
    public Connection connect = null;
    private boolean isOpen = false;

    public RealDatabase()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            open();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open()  {
        if (!isOpen) {
            try {
                connect = DriverManager.getConnection("jdbc:mysql://localhost/webcom?" + "user=root");
                isOpen = true;
            } catch (SQLException ex) {
                ex.printStackTrace();
                isOpen = false;

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

    @Override
    public Boolean isCorrect(String login, String password) {
        try {
            PreparedStatement ps = connect.prepareStatement("SELECT COUNT(*) AS n FROM users WHERE nick =  ? AND pass = ?");
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int n = rs.getInt("n");
            if(n > 0)
                return true;
            else
                return false;
        }
        catch (SQLException e) {
            return false;
        }
    }
//rs.next() oblozyc if'em
    @Override
    public Boolean register(String login, String password) {
        try {
            PreparedStatement ps = connect.prepareStatement("SELECT COUNT(*) AS n FROM users WHERE nick = ?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int m = rs.getInt("n");
            if(m>0)
                return false;
            ps = connect.prepareStatement("INSERT INTO users(nick, pass) VALUES (?,?)");
            ps.setString(1, login);
            ps.setString(2, password);


            int ok = ps.executeUpdate();
            if(ok == 1)
                return true;
            else
                return false;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void saveMessage(Message msg) {
        try {
            PreparedStatement ps = connect.prepareStatement("INSERT INTO messages");
        }
        catch (SQLException e) {

        }
    }

    @Override
    public List<Message> getMessage(String nick) {
       try {
           PreparedStatement ps1 = connect.prepareStatement("SELECT sender AS nadawca, receiver AS odbiorca, text AS wiad FROM messages");
           ResultSet rs = ps1.executeQuery();
           rs.next();
           String msg = rs.getString("wiad");


       }catch (SQLException e) {
            e.printStackTrace();
       }
        return List<Message>;
    }

    @Override
    public List<String> getFriends(String login) {
        //PreparedStatement ps = connect.prepareStatement("SELECT ");
        return null;
    }

    @Override
    public List<String> getSpectators(String login) {
        return null;
    }

    @Override
    public void addFriend(String login, String login2) {

    }

    @Override
    public void removeFriend(String login, String login2) {

    }
}
