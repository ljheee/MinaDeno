package com.ljheee.mina.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.ljheee.mina.entity.Message;

public class ClientSessionHandler extends IoHandlerAdapter {
	
	private Object msg;

	public ClientSessionHandler(Object msg) {
		this.msg = msg;
	}

	@Override
	public void sessionOpened(IoSession session) {
		session.write(this.msg);
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		System.out.println("in messageReceived!");
		Message rm = (Message) message;

		// SessionLog.debug(session, rm.getMsgBody());
		System.out.println("message is: " + rm.getMsgBody());
		session.write(rm);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		session.close();
	}
}
