package node;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;


public class DBwrite {
	
	
	MongoClient mongo = new MongoClient(" localhost", 27017);
	
	@SuppressWarnings("deprecation")
	DB db=mongo.getDB("Adriot");	
	DBCollection collection = db.getCollection("Block3");
	 BasicDBObject document = new BasicDBObject();	  
	 WriteResult db1=collection.insert(document);
	
	 
	}
   
    
	

