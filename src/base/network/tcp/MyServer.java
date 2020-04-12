package base.network.tcp;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Auther: Vectory
 * @Date: 2019/10/16
 * @Description: base.network.tcp
 * @version: 1.0
 */
public class MyServer {

    private static final int SERVER_PORT = 30000;
    public static CrazyitMap<String, PrintStream> clients = new CrazyitMap<>();

    public void init(){
        try (ServerSocket ss = new ServerSocket(SERVER_PORT)){
            while(true){
                Socket socket = ss.accept();
                new Thread(new ServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyServer myServer = new MyServer();
        myServer.init();
    }
}
