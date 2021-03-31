package es.urjc.etsii.dad.Components;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommManager{

	public void Comunicacion(String s) {
		try {
			//String host = "92.172.14.237";
			//String host = "139.47.79.207";
			String host = "127.0.0.1";
			int port = 9350;
			
			Socket socket = new Socket(host,port);
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
