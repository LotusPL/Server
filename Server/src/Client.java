import java.io.IOException;
import java.lang.String;import java.lang.System;import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by Lotus on 2015-04-20.
 */
public class Client {
    String nick, password;
    SocketChannel ch;
    Server serv;
    Scanner scan;
    Database fdb;

    void disconnect() {
        try {
            System.out.print((nick==null ? "" : nick) + " disconnected ... ");
            serv.clients.remove(this);
            ch.close();

        } catch (IOException e) {

        }

    }

    void receive(String message){
        try {
            message = message+"\n";
            ch.write(ByteBuffer.wrap(message.getBytes()));
            ch.socket().getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    void read(){

        try {
            String line = scan.nextLine();
            System.out.println(line);
            String[] parts = line.split("\\s");
            if (parts[0].equals("msg")) {
                String receiver;
                receiver = parts[1];
                String msg = line.substring(5 + parts[1].length());
                for (Client cls : serv.clients) {
                    if (receiver.equals(cls.nick)) {
                        cls.receive("msg " + this.nick + " " + msg);
                        return;
                    }
                }
                serv.DB.saveMessage(new Message(this.nick, receiver, msg));
            }
            if (parts[0].equals("login")) {
                nick = parts[1];
                password = parts[2];
                if (fdb.isCorrect(nick, password) == true) {
                    serv.ans.queue.add(nick + " ok");
                    for(String friend : serv.DB.getFriends(nick)){

                    }
                    for(String spect : serv.DB.getSpectators(nick)) {

                    }
                } else {
                    serv.ans.queue.add(nick + " failed");
                }
            }
            if (parts[0].equals("register")) {
                nick = parts[1];
                password = parts[2];
                if (fdb.register(nick, password) == true) {
                    serv.ans.queue.add(nick + " ok");
                } else {
                    serv.ans.queue.add(nick + " failed");
                }
            }
            if (parts[0].equals("FRIEND")) {
                // 0 - usunac
                // 1 - dodac
                if(parts[2].equals("0")) {
                    serv.DB.removeFriend(this.nick, parts[1]);
                }
                else if(parts[2].equals("1")) {
                    serv.DB.addFriend(this.nick, parts[1]);
                    this.receive("FRIEND " + parts[1] + " 1" );
                }
            }
        } catch (Exception e) {
            disconnect();
        }
    }

}
