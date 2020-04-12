package base.network.tcp;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @Auther: Vectory
 * @Date: 2019/10/16
 * @Description: base.network.tcp
 * @version: 1.0
 */
public class MyClient {

    private static final int SERVER_PORT = 30000;
    private Socket socket;
    private PrintStream ps;
    private BufferedReader brServer;
    private BufferedReader keyIn;

    public void init() throws IOException {
        try {
            keyIn = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket("127.0.0.1", 30000);
            ps = new PrintStream(socket.getOutputStream());
            brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String tip = "";
            while(true){
                String userName = JOptionPane.showInputDialog(tip + "输入用户名");
                ps.println(CrazyitProtocol.USER_ROUND + userName + CrazyitProtocol.USER_ROUND);
                String result = brServer.readLine();
                if(result.equals(CrazyitProtocol.NAME_REP)){
                    tip = "用户名重复！请重填";
                    continue;
                }
                if(result.equals(CrazyitProtocol.LOGIN_SUCCESS)){
                    break;
                }
            }
        } catch (IOException e) {
            closeRs();
            e.printStackTrace();
        }
        new Thread(new ClientThread(brServer)).start();
    }

    private void readAndSend(){
        try {
            String line = null;
            while((line = keyIn.readLine()) != null){
                if(line.indexOf(":") > 0 && line.startsWith("@")){
                    line = line.substring(2);
                    ps.println(CrazyitProtocol.PRIVATE_ROUND +
                            line.split(":")[0] + CrazyitProtocol.SPLIT_SIGN
                            + line.split(":")[1] + CrazyitProtocol.PRIVATE_ROUND);
                }else{
                    ps.println(CrazyitProtocol.MSG_ROUND + line
                            + CrazyitProtocol.MSG_ROUND);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeRs(){
        try {
            if(keyIn != null)
                keyIn.close();
            if(brServer != null)
                brServer.close();
            if(ps != null)
                ps.close();
            if(socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        MyClient client = new MyClient();
        client.init();
        client.readAndSend();
    }
}
