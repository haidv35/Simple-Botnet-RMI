/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SServer_package;

/**
 *
 * @author ligirk
 */
import DBUtils.MongoConnect;
import java.io.*;
import java.net.*;
import org.bson.Document;

/**
 * This program demonstrates a simple TCP/IP socket server.
 *
 * @author www.codejava.net
 */
public class SServer extends Thread {
    private int port;
    public SServer(int port){
        this.port = port;
    }

    public void run() {
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            MongoConnect client = new MongoConnect();
            client.Connect();
            Integer id = client.Read("bot_ip").size() + 1;
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
//                System.out.println("New client connected");
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String ip = reader.readLine();
                client.Write(ip, id);
                id++;
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
