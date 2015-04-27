import java.lang.Boolean;import java.lang.String;import java.util.*;import java.util.HashMap;import java.util.Iterator;import java.util.LinkedList;import java.util.List;import java.util.Map;

/**
 * Created by Lotus on 2015-04-20.
 */
public class FakeDatabase {
    private Map<String, String> passwords = new HashMap<String, String>();
    private Map<String, List<String>> friends = new HashMap<String, List<String>>();
    private List<Message> wiad = new LinkedList<Message>();
    Boolean IsCorrect(String login, String password){
        String haslo = passwords.get(login);
        if( haslo != null)
        {
            if(haslo.equals(password))
            {
                return true;
            }
        }
        return false;
    }
    Boolean Register(String login, String password){

        if(passwords.containsKey(login) == true){
            return false;
        }
        else {
            passwords.put(login, password);
            friends.put(login,new LinkedList<String>());
            return true;
        }
    }
    void SaveMessage(Message msg){
        wiad.add(msg);
    }
    List<Message> GetMessage(String nick){
        Iterator<Message> i = wiad.iterator();
        List<Message> wynik = new LinkedList<Message>();
        while(i.hasNext()){
            Message mes = i.next();
            if(mes.to_who.equals(nick)){
                i.remove();
                wynik.add(mes);

            }

        }
        return wynik;
    }
    List<String> getFriends(String login){
        return friends.get(login);
    }
    void addFriend(String login, String login2){
        friends.get(login).add(login2);

    }
    void removeFriend(String login, String login2){
        friends.get(login).remove(login2);
    }
}
