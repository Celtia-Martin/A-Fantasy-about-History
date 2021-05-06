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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class CommManager{

	private int connectionTimeout= 5;
	@Value("${ip.server.interno}")
	private String host;
	
	private Logger log = LoggerFactory.getLogger(CommManager.class);
	
	public void Comunicacion(String s) {
		log.warn("LO HA TIRAO AL AIRE: " + host);
		
		try {
			int port = 9000;
			
			Socket socket= new Socket(host,port);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			
			log.warn("LO VA A ENVIAR: " + host);
			PrintWriter send = new PrintWriter(out, true);
			send.println(s);
			log.warn("LO HA ENVIADO");
			
			out.close();
			in.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			System.err.println("Host desconocido");
			log.warn("Host desconocido: " + host);
		} catch (IOException e) {
			System.err.println("Error I/O");
			log.warn("Error I/O: " + host);
		}
	}
}
