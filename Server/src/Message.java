import java.lang.String; /**
 * Created by Lotus on 2015-04-20.
 */
public class Message {
    String from, to_who, content;

    public Message(String from, String to_who, String content) {
        this.from = from;
        this.to_who = to_who;
        this.content = content;
    }
}
