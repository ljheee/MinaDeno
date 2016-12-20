package com.ljheee.mina.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.ljheee.mina.entity.Message;
/**
 * 客户端
 * @author ljheee
 *
 */
public class SimpleClient { 
	
    private static final String HOSTNAME = "localhost"; 
    private static final int PORT = 9595; 
    private static final int CONNECT_TIMEOUT = 30;  

    public static void main( String[] args ) throws Throwable { 
    	
    	// 创建一个非阻塞的客户端socket
        SocketConnector connector = new NioSocketConnector();   
        
        // 设置链接超时时间
        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
        
        //消息过滤器  
        connector.getFilterChain().addLast("codec",new ProtocolCodecFilter( new ObjectSerializationCodecFactory()));  
        connector.getFilterChain().addLast("logger",new LoggingFilter());
        
        
        Message msg = new Message(0,1,"hello"); 
        connector.setHandler(new ClientSessionHandler(msg));//客户端事务逻辑  
        
        //连接服务器
        ConnectFuture future = connector.connect(new InetSocketAddress( HOSTNAME, PORT ));
        
    } 
} 
