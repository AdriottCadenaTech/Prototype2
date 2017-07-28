package bck;

import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;
import java.util.List;

import bck.CryptoUtil1;

public class BlockService1 {
	
	private static List<Block1> block;
	
	
	
	  private static Block1 getFristBlock() {
		 /* String previousBlock="0";
		  int nextIndex=1;
		  long nextTimestamp = System.currentTimeMillis();
		  String blockData="hello";*/
		  String nextHash = calculateHash(1, "0", System.currentTimeMillis(), "hello");
		  
		  
	        return new Block1(1, "0", System.currentTimeMillis(), "hello", nextHash);
	    }
	  /*private static String calculateHash1(int nextIndex, String previousBlock, long nextTimestamp, String blockData) {
		StringBuilder sb=new StringBuilder(nextIndex);
		sb.append(previousBlock).append(nextTimestamp).append(blockData);
        return CryptoUtil1.getSHA256(sb.toString());
	}*/
	public static Block1 generateNextBlock(String blockData) {
	        Block1 previousBlock = getLatestBlock();
	        int nextIndex = previousBlock.getIndex() + 1;
	        long nextTimestamp = System.currentTimeMillis();
	        String nextHash = calculateHash(nextIndex, previousBlock.getHash(), nextTimestamp, blockData);
	        return new Block1(nextIndex, previousBlock.getHash(), nextTimestamp, blockData, nextHash);
	    }
	  private static String calculateHash(int index, String previousHash, long timestamp, String data) {
	        StringBuilder builder = new StringBuilder(index);
	        builder.append(previousHash).append(timestamp).append(data);
	        return CryptoUtil1.getSHA256(builder.toString());
	    }
	public static Block1 getLatestBlock() {
	        return block.get(block.size() - 1);
	    }
	public static void main(String args[]){
		
		block=new ArrayList<Block1>();
		block.add(getFristBlock());
		block.add(generateNextBlock("abc"));
		block.add(generateNextBlock("def"));
		for(Block1 b:block){
			/*System.out.println( b.getIndex());
			System.out.println(b.getPreviousHash());
			System.out.println(b.getTimestamp());
			System.out.println(b.getData());
			System.out.println(b.getHash());
			System.out.println("\n");*/
			
			 ObjectMapper mapper = new ObjectMapper();
		        try {
		            String json = mapper.writeValueAsString(b);
		            System.out.println("\n" + json);
		        } catch (JsonProcessingException e) {
		            e.printStackTrace();
		        }
		}
		
		
			
			
			
	}
	
}
