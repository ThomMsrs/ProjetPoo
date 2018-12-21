
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientUDP extends Thread{
	byte[] buffer;
	DatagramSocket dgramSocket;
	int port;
	String message;
	int addr;	//0= pas denvoie daddr, 1= envoie daddr
	InetAddress address;
	InetAddress Myaddress;
	String addresse;
	
	
	public ClientUDP(int num_port, String msg_to_send) {		//	UTILISé pour broadcast
		super();
		port=num_port;
		message=msg_to_send;
	}
	
	
	
	public void run() {
		
		try {
			
			System.out.println("testClientUDP1");
			dgramSocket = new DatagramSocket(port);
			System.out.println("testClientUDP2");
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			dgramSocket.setBroadcast(true);
			System.out.println("testClientUDP3");
			//DatagramPacket outPacket= new DatagramPacket(message.getBytes(), message.length()); 	// port 1500 numero de port ???????????
			DatagramPacket outPacket= new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName("255.255.255.255"), 1500 );
			dgramSocket.send(outPacket);
			System.out.println("testClientUDP4");
			dgramSocket.close();
			System.out.println("testClientUDP5");
		} catch (Exception e1) {
		}
	}
	
	
}