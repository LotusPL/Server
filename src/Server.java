import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Created by Lotus on 2015-04-20.
 */
public class Server implements Runnable{
    List<Client> clients = new LinkedList<Client>();
    public static void main(String[] args){
        new Server().run();
    }
    Answer ans;
    FakeDatabase FDB;
    public void run(){
        ans = new Answer();
        ans.serv = this;
        new Thread(ans).start();

        try {
            Selector selector = Selector.open();

            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(33060));
            channel.register(selector, SelectionKey.OP_ACCEPT);


            while(true)
            {
                if(selector.select()>0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> i = keys.iterator();
                    while(i.hasNext()) {
                        SelectionKey key = i.next();
                        i.remove();
                        if (key.isAcceptable()) {
                            SocketChannel socketchannel = channel.accept();
                            Client client = new Client();
                            client.ch = socketchannel;
                            client.serv = this;
                            client.scan = new Scanner(socketchannel);

                            socketchannel.configureBlocking(false);
                            socketchannel.register(selector,SelectionKey.OP_READ,client);
                            clients.add(client);

                        }
                        if (key.isReadable()){
                            Client client = (Client)key.attachment();
                            client.Read();
                        }
                    }


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
