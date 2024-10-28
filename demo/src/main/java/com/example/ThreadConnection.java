package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadConnection extends Thread {
    
    Socket socket;
    
    public ThreadConnection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String stringaRicevuta;
            do {
                stringaRicevuta = in.readLine();
                System.out.println("La stringa ricevuta: " + stringaRicevuta);

                String stringMaiuscola = stringaRicevuta.toUpperCase();
                out.writeBytes(stringMaiuscola + "\n");
            }while(!stringaRicevuta.equals("!"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Chiusura della connessione a seguito di invio di ! o di un'eccezzione");

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
