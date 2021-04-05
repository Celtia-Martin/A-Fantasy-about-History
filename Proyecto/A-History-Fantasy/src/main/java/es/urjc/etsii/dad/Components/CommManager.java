package es.urjc.etsii.dad.Components;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class CommManager{

	private int connectionTimeout= 5;
	public void Comunicacion(String s) {
		try {
			//String host = "92.172.14.237";
			//String host = "139.47.79.207";
			
			String host = "127.0.0.1";
			int port = 9350;
			//SocketFactory socketFactory= SSLSocketFactory.getDefault();
			//SSLSocket socket= (SSLSocket) socketFactory.createSocket(host,port);
			//Socket sock = new Socket();
			//sock.connect(new InetSocketAddress(host, port), connectionTimeout);
			//Socket socket = (SSLSocket)socketFactory.createSocket(host,port);
			Socket socket= new Socket(host,port);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			
			PrintWriter send = new PrintWriter(out, true);
			send.println(s);
			
			out.close();
			in.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			System.err.println("Host desconocido");
		} catch (IOException e) {
			System.err.println("Error I/O");
		}
	}
}
