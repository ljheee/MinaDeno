package com.ljheee.mina.server;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
/**
 * 服务器
 * @author ljheee
 *
 */
public class MinaServer { 
	
    private static final int SERVER_PORT = 9595; //定义监听端口  
    
    public static void main( String[] args ) throws Throwable { 
    	//创建一个非阻塞的Server端socket，基于NIO 
        IoAcceptor acceptor = new NioSocketAcceptor(); 
        
        //获得接受数据的过滤器，并设定这个过滤器将以对象为单位读取数据 
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new ObjectSerializationCodecFactory()));
        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        
        
        //指定业务逻辑处理器
        acceptor.setHandler(new ServerSessionHandler( ) );
        acceptor.bind( new InetSocketAddress( SERVER_PORT )); //启动监听  
        
        System.out.println( "Mina server Listening on port " + SERVER_PORT ); 
    } 
} 