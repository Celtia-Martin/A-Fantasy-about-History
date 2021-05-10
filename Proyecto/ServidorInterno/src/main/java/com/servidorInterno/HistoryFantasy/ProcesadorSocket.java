package com.servidorInterno.HistoryFantasy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ProcesadorSocket implements Runnable {

	Socket socket;
	EjecutarBatallaService batallaService;
	RefrescarMercadoService mercadoService;
	public ProcesadorSocket(Socket socket,EjecutarBatallaService batallaService, RefrescarMercadoService mercadoService) {
	
		this.socket= socket;
		this.batallaService= batallaService;
		this.mercadoService= mercadoService;
	}
	@Override
	public void run() {
	
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

				e.printStackTrace();
			}
	}
}


