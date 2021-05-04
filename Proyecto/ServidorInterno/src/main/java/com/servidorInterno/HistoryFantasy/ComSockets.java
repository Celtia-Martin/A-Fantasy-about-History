package com.servidorInterno.HistoryFantasy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComSockets {

	@Autowired
	private EjecutarBatallaService batallaService;
	
	@Autowired
	private RefrescarMercadoService mercadoService;
	
	public void Comunicacion() {
		try {
			
			int port = 9000;
			ServerSocket serverSocket = new ServerSocket(port);
			
			while (true) {
				Socket socket = serverSocket.accept();
				/*
				Thread t= new Thread(new ProcesadorSocket(socket,batallaService,mercadoService));
				t.start();
				*/
				InputStream is;
				try {
					is = socket.getInputStream();
				
					OutputStream os = socket.getOutputStream();
					
					BufferedReader leerServidor =
							 new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
					String orden = leerServidor.readLine();
					
					if(orden != null) {
						switch(orden) {
							case "Refresco":
								mercadoService.RefrescarMercado();
								break;
							case "Batalla":
								batallaService.RealizarBatalla();
								break;
						}
					}
					
					is.close();
					os.close();
					socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		} catch (IOException e) {
			System.err.println("Error I/O");
		}
	}
	
}
