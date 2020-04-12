package base.network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Auther: Vectory
 * @Date: 2019/10/16
 * @Description: base.network.tcp
 * @version: 1.0
 */
public class ServerThread implements Runnable {

    Socket socket = null;
    BufferedReader br = null;
    PrintStream ps = null;
    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ps = new PrintStream(socket.getOutputStream());
            String line = null;
            while((line = br.readLine()) != null){
                if(line.startsWith(CrazyitProtocol.USER_ROUND) && line.endsWith(CrazyitProtocol.USER_ROUND)){
                    String userName = getRealMsg(line);
                    if(MyServer.clients.map.containsKey(userName)){
                        System.out.println("重复");
                        ps.println(CrazyitProtocol.NAME_REP);
                    }else{
                        System.out.println("成功");
                        ps.println(CrazyitProtocol.LOGIN_SUCCESS);
                        MyServer.clients.put(userName, ps);
                    }
                } else if(line.startsWith(CrazyitProtocol.PRIVATE_ROUND) && line.endsWith(CrazyitProtocol.PRIVATE_ROUND)){
                    String userAndMsg = getRealMsg(line);
                    String user = userAndMsg.split(CrazyitProtocol.SPLIT_SIGN)[0];
                    String msg = userAndMsg.split(CrazyitProtocol.SPLIT_SIGN)[1];
                    MyServer.clients.map.get(user).println(MyServer.clients.getKeyByValue(ps) + "悄悄对你说: " + msg);
                } else{
                    String msg = getRealMsg(line);
                    for(PrintStream clientPs : MyServer.clients.valueSet()){
                        clientPs.println(MyServer.clients.getKeyByValue(ps) + "说: " + msg);
                    }
                }
            }
        } catch (IOException e) {
            MyServer.clients.removeByValue(ps);
            System.out.println(MyServer.clients.map.size());
            try {
                if(br != null)
                    br.close();
                if(ps != null)
                    ps.close();
                if(socket != null)
                    socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private String getRealMsg(String line){
        return line.substring(CrazyitProtocol.PROTOCOL_LEN, line.length() - CrazyitProtocol.PROTOCOL_LEN);
    }
}
