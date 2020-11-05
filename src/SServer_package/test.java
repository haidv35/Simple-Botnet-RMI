/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SServer_package;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Iterator;
import org.bson.Document;

/**
 *
 * @author ligirk
 */
public class test {
    public static void main(String[] args) {    
        MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
        MongoCredential credential;
	credential = MongoCredential.createCredential("admin", "masked_faceid", "vinai123".toCharArray());
        MongoDatabase database = mongo.getDatabase("masked_faceid");
//        MongoCollection<Document> collection = database.getCollection("api_log");
        
        for (String name : database.listCollectionNames()) { 
           System.out.println(name); 
        } 
    }
}
