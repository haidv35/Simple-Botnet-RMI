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
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
        MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
        MongoCredential credential;
	credential = MongoCredential.createCredential("admin", "Botnet", "vinai123".toCharArray());
        MongoDatabase database = mongo.getDatabase("Botnet");
        Integer id = 1;
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String ip = reader.readLine();
                String bot_name = "bot" + id.toString();
                MongoCollection<Document> collection = database.getCollection("bot_ip");
                Document doc = new Document(bot_name, ip);
                collection.insertOne(doc);
                id++;
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
