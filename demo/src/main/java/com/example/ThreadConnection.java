package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadConnection extends Thread {
    
    Socket socket;
    
    public ThreadConnection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            List<String> lista = new ArrayList<String>();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String stringaRicevuta;
            do {
                stringaRicevuta = in.readLine();
                switch (stringaRicevuta) {
                    case "!":
                        System.out.println("Termine della comunicazione");
                    break;
                    case "?":
                        for (int i = 0; i < lista.size(); i++) {
                            out.writeBytes(i + ") " +  lista.get(i) + "\n");
                        }
                        out.writeBytes("@" + "\n");
                        out.writeBytes("Si vuole eliminare un' elemento della lista?(s = si, altro = no)" + "\n");
                        String scelta = in.readLine();
                        if (scelta.equals("s")) {
                            out.writeBytes("scegliere il numero: 0-" + lista.size() + "\n");
                            scelta = in.readLine();
                            lista.remove(scelta);
                            out.writeBytes("rimozione eseguita");
                        }
                        out.writeBytes("@" + "\n");
                    break;
                    default:
                        lista.add(stringaRicevuta);
                        out.writeBytes("OK " + "\n");
                    break;
                }
            }while(!stringaRicevuta.equals("!"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
