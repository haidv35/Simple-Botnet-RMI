/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author ann52
 */

public class MongoConnect {
    private MongoDatabase database;
    public void Connect(){
        MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
        MongoCredential credential;
	credential = MongoCredential.createCredential("admin", "Botnet", "Haiclover99".toCharArray());
        this.database = mongo.getDatabase("Botnet");
        try{
            this.database.createCollection("bot_ip"); 
        } catch (MongoCommandException e){
        }
        
        
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
    }
    
    public void Write(String ip, Integer id){
        String bot_name = "bot" + id.toString();
        MongoCollection<Document> collection = this.database.getCollection("bot_ip");
        Document doc = new Document(bot_name, ip);
        ArrayList<String> list = this.Read("bot_ip");
        for (String i : list){
            if (ip.equals(i)){
                return ;
            }
        }
        collection.insertOne(doc);
    }
    
    public ArrayList<String> Read(String collection_name){
        MongoCollection<Document> collection = database.getCollection(collection_name);
        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int id = 1;
        // Getting the iterator
        ArrayList<String> ip_list = new ArrayList<>();
        for (Document doc : iterDoc) {
            String ip = (String) doc.get("bot" + id);
            ip_list.add(ip);
            id++;
        }
        return ip_list;
    }
    
}
