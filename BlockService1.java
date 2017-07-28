package node;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import node.CryptoUtil1;
import node.Block1;

public class BlockService1 {

		private static List<Block1> block;
		
		public static List<Block1> getBlockChain()
		{
			block = new ArrayList<Block1>();
			
			return block;
		}
	
		public static Block1 getFirstBlock() {
			
			/* Logic to calulate the first hash has to be in place
			 * SHould not be hardcoded
			 */
			String nextHash = calculateHash(1, "0", System.currentTimeMillis(), "hello");
		  
		  
	        return new Block1(1, "0", System.currentTimeMillis(), "hello", nextHash);
	    
	    }
		
		
		public static Block1 generateNextBlock(String blockData) {
	        Block1 previousBlock = getLatestBlock();
	        
	        /* Next index needs to be calculated using the DB count 
	         * 
	         */
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
		
			/* The DBCount has to be used to fetch the latest record from the DB 
			 * 
			 */
	        return block.get(block.size() - 1);
	    }
	
//	public static void main(String args[]){
//		
//	}	
}
