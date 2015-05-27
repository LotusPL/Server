import java.lang.InterruptedException;import java.lang.Runnable;import java.lang.String;import java.lang.System;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Lotus on 2015-04-20.
 */
public class Answer implements Runnable {
    BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
    Server serv;
    public void run() {
        while(true)
        {
            String text = null;
            try {
                text = queue.take();
                String[] part = text.split("\\s");
                for(Client s : serv.clients) {
                    if(part[0].equals(s.nick)) {
                        s.receive(text.substring(part[0].length() + 1));
                    }
                }
            }catch (InterruptedException e) {
                //e.printStackTrace();
            }
            System.out.println("> " + text);
        }
    }
}
