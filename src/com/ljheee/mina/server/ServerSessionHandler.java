package com.ljheee.mina.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.ljheee.mina.entity.Message;

public class ServerSessionHandler extends IoHandlerAdapter {
	
	/**
	 * 当一个客户端连接进入时 
	 */
	@Override
	public void sessionOpened(IoSession session) {
		// session.setIdleTime( IdleStatus.BOTH_IDLE, 60 );
//		session.getRemoteAddress();
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);
		session.setAttribute("times", new Integer(0));
	}

	/**
	 * 接收到客户端消息时
	 */
	@Override
	public void messageReceived(IoSession session, Object message) {
		System.out.println("in messageReceived");
		int times = ((Integer) (session.getAttribute("times"))).intValue();
		System.out.println("tiems = " + times);

		// communicate 30 times,then close the session.
		if (times < 30) {
			times++;
			session.setAttribute("times", new Integer(times));
			Message msg;
			msg = (Message) message;
			msg.setMsgBody("in server side: " + msg.getMsgBody());
			System.out.println("begin send msg: " + msg.getMsgBody());
			session.write(msg);
		} else {
			session.close();
		}
	}

	/**
	 * 客户端连接关闭时
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		// SessionLog.info( session, "Disconnecting the idle." );
		// disconnect an idle client
		session.close();
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		// close the connection on exceptional situation
		session.close();
	}
}
