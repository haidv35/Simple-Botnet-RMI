/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Iterator;
import org.bson.Document;

/**
 *
 * @author ligirk
 */
public class MongoConnect {
    private MongoDatabase database;
    public void Connect(){
        MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
        MongoCredential credential;
	credential = MongoCredential.createCredential("admin", "Botnet", "vinai123".toCharArray());
        this.database = mongo.getDatabase("Botnet");
    }
    
    public void Write(String ip, Integer id){
        String bot_name = "bot" + id.toString();
        MongoCollection<Document> collection = this.database.getCollection("bot_ip");
        Document doc = new Document(bot_name, ip);
        collection.insertOne(doc);
    }
    
    public ArrayList<String> Read(String collection_name){
        MongoCollection<Document> collection = database.getCollection(collection_name);
        ArrayList<Document> list = new ArrayList<Document>();
        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int id = 1;
        // Getting the iterator
        ArrayList<String> ip_list = new ArrayList<>();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document doc = (Document) it.next();
            String ip = (String) doc.get("bot" + id);
            ip_list.add(ip);
            id++;
        }
        return ip_list;
    }
    
}
