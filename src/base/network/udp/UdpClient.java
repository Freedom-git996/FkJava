package base.network.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @Auther: Vectory
 * @Date: 2019/10/16
 * @Description: base.network.udp
 * @version: 1.0
 */
public class UdpClient {

    public static final int DEST_PORT = 30000;
    public static final java.lang.String DEST_IP = "127.0.0.1";
    private static final int DATA_LEN = 4096;
    byte[] inBuff = new byte[DATA_LEN];

    private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
    private DatagramPacket outPacket = null;

    public void init(){
        try(DatagramSocket socket = new DatagramSocket()) {
            outPacket = new DatagramPacket(new byte[0], 0, new InetSocketAddress(DEST_IP, DEST_PORT));
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()){
                byte[] buff = scanner.nextLine().getBytes();
                outPacket.setData(buff);
                socket.send(outPacket);
                socket.receive(inPacket);
                System.out.println(new java.lang.String(inBuff, 0, inPacket.getLength()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UdpClient().init();
    }
}
