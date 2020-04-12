package base.network.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: Vectory
 * @Date: 2019/10/18
 * @Description: base.network.aio
 * @version: 1.0
 */
public class AIOServer {

    static final int PORT = 30000;
    final static String UTF_8 = "UTF-8";
    static List<AsynchronousSocketChannel> channelList = new ArrayList<>();

    public void startListen() throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(20);
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
        AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel
                .open(channelGroup)
                .bind(new InetSocketAddress("127.0.0.1", PORT));
        serverChannel.accept(null, new AcceptHandler(serverChannel));
    }

    public static void main(String[] args) throws Exception {
        AIOServer server = new AIOServer();
        server.startListen();
    }
}

class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object>{

    private AsynchronousServerSocketChannel serverChannel;
    ByteBuffer buffer = ByteBuffer.allocate(1024);

    public AcceptHandler(AsynchronousServerSocketChannel serverChannel){
        this.serverChannel = serverChannel;
    }

    @Override
    public void completed(AsynchronousSocketChannel sc, Object attachment) {
        AIOServer.channelList.add(sc);
        serverChannel.accept(null, this);
        sc.read(buffer, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                buffer.flip();
                String content = StandardCharsets.UTF_8.decode(buffer).toString();
                for(AsynchronousSocketChannel c : AIOServer.channelList){
                    try{
                        c.write(ByteBuffer.wrap(content.getBytes(AIOServer.UTF_8))).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                buffer.clear();
                sc.read(buffer, null, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("读取数据失败：" + exc);
                AIOServer.channelList.remove(sc);
            }
        });
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        System.out.println("连接失败：" + exc);
    }
}