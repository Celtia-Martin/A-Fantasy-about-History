package com.servidorInterno.HistoryFantasy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComSockets {

	@Autowired
	public ServicioInternoController cont;
	
	public void Comunicacion() {
		try {
			int port = 9115;
			ServerSocket serverSocket = new ServerSocket(port);
			
			while (true) {
				Socket socket = serverSocket.accept();
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				BufferedReader leerServidor =
						 new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String orden = leerServidor.readLine();
				
				if(orden != null) {
					switch(orden) {
						case "Refresco":
							cont.RefrescarMercado();
							break;
						case "Batalla":
							cont.RealizarBatalla();
							break;
					}
				}
				
				is.close();
				os.close();
				socket.close();
			}
		} catch (IOException e) {
			System.err.println("Error I/O");
		}
	}
}
