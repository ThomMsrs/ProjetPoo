
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientUDP extends Thread{
	private DatagramSocket dgramSocket;
	private int port;
	private String message;

	
	
	public ClientUDP(int num_port, String msg_to_send) {		//	UTILISé pour broadcast
		super();
		port=num_port;
		message=msg_to_send;
	}
	
	
	
	public void run() {
		
		try {
			dgramSocket = new DatagramSocket(port);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			dgramSocket.setBroadcast(true);
			DatagramPacket outPacket= new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName("255.255.255.255"), 1500 );
			dgramSocket.send(outPacket);
			dgramSocket.close();
		} catch (Exception e1) {
		}
	}
	
	
}