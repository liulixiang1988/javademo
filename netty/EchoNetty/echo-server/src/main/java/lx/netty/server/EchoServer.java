package lx.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by lxliu@iflytek.com on 2016/8/8.
 */
public class EchoServer {
    private static final Logger logger = LoggerFactory.getLogger(EchoServer.class);
    public final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            logger.error("Usage: {} <port>", EchoServer.class.getSimpleName());
            return;
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    public void start()throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        //1. 创建事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //2. 创建ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();

            b.group(group)
                    //3. 指定使用NIO传输Channel
                    .channel(NioServerSocketChannel.class)
                    //4.指定socket端口
                    .localAddress(new InetSocketAddress(port))
                    //5. 添加EchoServerHandler到ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //因为EchoServerHandler是@Sharable的,所以我们一直可以使用同一个
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            //6. 异步绑定,使用sync()等待绑定完成
            ChannelFuture f = b.bind().sync();
            //7. 获取Chaneel的CloseFuture,阻塞当前线程,知道其完成
            f.channel().closeFuture().sync();
        } finally {
            //8. 关闭EventLoopGroup,释放所有资源
            group.shutdownGracefully().sync();
        }

    }
}
