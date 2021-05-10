package es.urjc.etsii.dad.Components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommManager{


	@Value("${ip.server.interno}")
	private String host;
	@Autowired
	private ControlUsuarios controlUsuarios;
	
	private Logger log = LoggerFactory.getLogger(CommManager.class);
	
	public void Comunicacion(String s) {
		
		try {
			int port = 9000;
			
			Socket socket= new Socket(host,port);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			BufferedReader leerServidor =
					 new BufferedReader(new InputStreamReader(socket.getInputStream()));

			PrintWriter send = new PrintWriter(out, true);
			send.println(s);
			
			leerServidor.read();//espera hasta que el server acabe
			
			controlUsuarios.InvalidarCache();
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
