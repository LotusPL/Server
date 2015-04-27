import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;import java.lang.System;import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by Lotus on 2015-04-20.
 */
public class Client {
    String nick;
    SocketChannel ch;
    Server serv;
    Scanner scan;
    void Receive(String message){
        try {
            message = message+"\n";
            ch.write(ByteBuffer.wrap(message.getBytes()));
            ch.socket().getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    void Read(){
        String line = scan.nextLine();
        System.out.println(line);
        String[] parts = line.split("\\s");
        if (parts[0].equals("msg")){
            line.substring(5+parts[1].length());
        }
        if (parts[0].equals("login")){
            nick = parts[1];
            serv.ans.queue.add(nick + " ok");
        }
    }

}
