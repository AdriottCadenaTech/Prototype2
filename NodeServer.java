package node;
 
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import com.mongodb.DBCursor;
 
/**
 * @author Rashida
 *
 */
 
public class NodeServer {
 
	public static void main(String[] args) throws IOException {
 
		// Selector: multiplexor of SelectableChannel objects
		Selector selector = Selector.open(); // selector is open here
 
		// ServerSocketChannel: selectable channel for stream-oriented listening sockets
		ServerSocketChannel nodeSocket = ServerSocketChannel.open();
		InetSocketAddress nodeAddr = new InetSocketAddress("localhost", 1111);
 
		// Binds the channel's socket to a local address and configures the socket to listen for connections
		nodeSocket.bind(nodeAddr);
 
		// Adjusts this channel's blocking mode.
		nodeSocket.configureBlocking(false);
 
		int ops = nodeSocket.validOps();
		SelectionKey selectKy = nodeSocket.register(selector, ops, null);
 
		// Infinite loop..
		// Keep server running
		while (true) {
 
			log("i'm a server and i'm waiting for new connection and buffer select...");
			// Selects a set of keys whose corresponding channels are ready for I/O operations
			selector.select();
 
			// token representing the registration of a SelectableChannel with a Selector
			Set<SelectionKey> nodeSelectKeys = selector.selectedKeys();
			Iterator<SelectionKey> selectIterator = nodeSelectKeys.iterator();
 
			while (selectIterator.hasNext()) {
				SelectionKey myKey = selectIterator.next();
 
				// Tests whether this key's channel is ready to accept a new socket connection
				if (myKey.isAcceptable()) {
					SocketChannel nClient = nodeSocket.accept();
 
					// Adjusts this channel's blocking mode to false
					nClient.configureBlocking(false);
 
					// Operation-set bit for read operations
					nClient.register(selector, SelectionKey.OP_READ);
					log("Connection Accepted: " + nClient.getLocalAddress() + "\n");
 
					// Tests whether this key's channel is ready for reading
				} else if (myKey.isReadable()) {
					
					SocketChannel nClient = (SocketChannel) myKey.channel();
					ByteBuffer buffer = ByteBuffer.allocate(256);
					nClient.read(buffer);
					String result = new String(buffer.array()).trim();
					 DBwrite dw=new DBwrite();
					 DBCursor cursorDocJSON =  dw.collection.find();
			         while (cursorDocJSON.hasNext()) {
			          System.out.println( cursorDocJSON.next());
			         }
 
					log("Message received: " + result);
 
					if (result.equals("")) {
						nClient.close();
						log("\nIt's time to close connection as we got the empty message the first time'");
						log("\nServer will keep running. Try running client again to establish new connection\n");
					}
				}
				selectIterator.remove();
			}
		}
	}
 
	private static void log(String str) {
		System.out.println(str);
	}
}