package com.servidorInterno.HistoryFantasy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.stereotype.Service;

@Service
public class ComSockets {

	@Autowired
	private EjecutarBatallaService batallaService;
	
	@Autowired
	private RefrescarMercadoService mercadoService;
	
	private Logger log = LoggerFactory.getLogger(ComSockets.class);
	
	public void Comunicacion() {
		try {
			
			int port = 9000;
			ServerSocket serverSocket = new ServerSocket(port);
			
			while (true) {
				log.warn("LO HA TIRAO AL AIRE");
				
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
					PrintWriter send = new PrintWriter(os, true);
					log.warn("LO HA COGIO AR VUELO");
					
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
					send.print(true);
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
