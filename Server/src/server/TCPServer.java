/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import DBUtils.MongoConnect;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ann52
 */

public class TCPServer extends Thread {
    private final int port;
    
    public TCPServer(int port){
        this.port = port;
    }

    @Override
    public void run() {
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            MongoConnect client = new MongoConnect();
            client.Connect();
            
            Integer id = client.Read("bot_ip").size() + 1;
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String ip = reader.readLine();
                client.Write(ip, id);
                id++;
                
                reader.close();
                input.close();
                socket.close();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
        }
    }
}


