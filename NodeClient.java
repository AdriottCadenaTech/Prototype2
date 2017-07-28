package node;
 
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;


import node.Block1;
import node.BlockService1;
import node.CryptoUtil1;
 
public class NodeClient {
	
	private static List<Block1> block;
 
	public static void main(String[] args) throws IOException, InterruptedException {
 
		//InetSocketAddress clientAddr = new InetSocketAddress("10.0.0.20", 1111);
		InetSocketAddress clientAddr = new InetSocketAddress("localhost", 1111);
		SocketChannel client = SocketChannel.open(clientAddr);
 
		log("Connecting to Server on port 1111...");
 
		ArrayList<String> staticStrings = new ArrayList<String>();
 
		// create a ArrayList with myStaticString list
		/*staticStrings.add("Guess Me");
		staticStrings.add("Who am I");
		staticStrings.add("Hello Out there!!!");
		staticStrings.add("Am I Guessing.....????????");
		staticStrings.add("Testing Done!");
		*/
		
		block = BlockService1.getBlockChain();
		//block=new ArrayList<Block1>();
		
		block.add(BlockService1.getFirstBlock());
		
		block.add(BlockService1.generateNextBlock("Hello Out There!!!"));
		
		
		
		
		
		for(Block1 b:block) {
			System.out.println( b.getIndex());
			System.out.println(b.getPreviousHash());
			System.out.println(b.getTimestamp());
			System.out.println(b.getData());
			System.out.println(b.getHash());
			System.out.println("\n");	
	
		
		
		 ObjectMapper mapper = new ObjectMapper();
	        try {
	            String json = mapper.writeValueAsString(b);
		 DBwrite dw=new DBwrite();
         DBObject dbObject = (DBObject)JSON.parse(json);
        dw.collection.insert(dbObject);
         
         //  collection.insert(dbObject);
         
        
        DBCursor cursorDocJSON =  dw.collection.find();
         while (cursorDocJSON.hasNext()) {
          System.out.println( cursorDocJSON.next());
         }
 
		/*//for (String myStaticString : staticStrings) {
		for(Block1 b:block)
		{
			byte[] message = new String(b.getIndex() + b.getPreviousHash() + b.getTimestamp() + b.getData() + b.getHash()).getBytes();
			ByteBuffer buffer = ByteBuffer.wrap(message);
			client.write(buffer);
 
			log("sending: " + message.toString()
			);
			buffer.clear();
 
			// wait for 2 seconds before sending next message
			Thread.sleep(2000);*/
		
	   } catch (JsonProcessingException e) {
           e.printStackTrace();
       }}
		client.close();
	}
 
	private static void log(String str) {
		System.out.println(str);
	}
}