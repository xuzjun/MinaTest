package per.x.minatest.serverhandler;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TimeServerHandler extends IoHandlerAdapter {

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println(session.getRemoteAddress().toString());
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		String msg = message.toString();
		if (msg.trim().equalsIgnoreCase("quit")) {
			session.close(true);
		} else {
			Date date = new Date();
			session.write(date.toString());
			System.out.println("Message writen...");
		}
	}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		
		System.out.println("IDLE" + session.getIdleCount(status));
		session.write("0000");
	}
}
