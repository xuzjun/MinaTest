package per.x.minatest.clienthandler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaTimeClient {
	
	private static final String IP = "127.0.0.1";
	private static final int PORT = 9123;

	public static void main(String[] args) {

		NioSocketConnector connector = new NioSocketConnector();
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		connector.setConnectTimeoutMillis(30000);
		connector.setHandler(new TimeClientHandler());
		ConnectFuture cf = connector.connect(new InetSocketAddress(IP, PORT));
		cf.awaitUninterruptibly();
		cf.getSession().write("hello");
		
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}

}
