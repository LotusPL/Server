import java.lang.Boolean;import java.lang.String;import java.util.*;import java.util.HashMap;import java.util.Iterator;import java.util.LinkedList;import java.util.List;import java.util.Map;

/**
 * Created by Lotus on 2015-04-20.
 */
public interface Database {
    void open();
    Boolean isCorrect(String login, String password);
    Boolean register(String login, String password);
    void saveMessage(Message msg);
    List<Message> getMessage(String nick);
    List<String> getFriends(String login);
    List<String> getSpectators(String login);
    void addFriend(String login, String login2);
    void removeFriend(String login, String login2);
}
