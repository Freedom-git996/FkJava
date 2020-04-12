package base.network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @Auther: Vectory
 * @Date: 2019/10/16
 * @Description: base.network.tcp
 * @version: 1.0
 */
public class ClientThread implements Runnable {

    BufferedReader br = null;

    public ClientThread(BufferedReader br) throws IOException {
        this.br = br;
    }

    @Override
    public void run() {
        try {
            String line = null;
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
