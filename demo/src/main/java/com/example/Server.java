package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException{
        System.out.println("Hello Word!");
        ServerSocket ss = new ServerSocket(3000);
        do
        {
        Socket mySocket = ss.accept();
        Thread thread = new ThreadConnection(mySocket);
        thread.start();
        System.out.println("qualcuno si e' collegato");
        }while(true);
    }
}