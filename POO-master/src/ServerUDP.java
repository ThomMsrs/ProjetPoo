import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServerUDP extends Thread{
	private byte[] buffer;
	private DatagramSocket dgramSocket_server;
	private int port;
	private String message;
	private String response;
	private	List_user MyList_user;
	private	boolean ok_boucle;

	
	
	public ServerUDP(List_user list_user, int num_port) {		//TODO : 3 constructeurs, 1 pour list_user, 1 pour Sessions, et 1 pour Session
		super();
		MyList_user=list_user;	
		port=num_port;	//toujours 1500
		ok_boucle=true;
	}


	public void run() {
			try {
				dgramSocket_server = new DatagramSocket(port);
			} 
			catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(ok_boucle){	
				buffer = new byte[256];
				DatagramPacket inPacket = new DatagramPacket(buffer,buffer.length);
				try {
					dgramSocket_server.receive(inPacket);
				} 
				catch (IOException e) {
					System.out.println(" ERREUR SERVER UDP ");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				message = new String(inPacket.getData(), 0, inPacket.getLength());
				System.out.println(" MESSAGE RECU : " + message);
				

					if(port==1500) {
						if(message.equals("ASK_FOR_NAME")) { 	//un autre utilisateur se connecte, et nous demande notre nom
							InetAddress clientAddress= inPacket.getAddress();
							int clientPort = inPacket.getPort();
							if(MyList_user.get_name()!=null) {
								response=MyList_user.get_name();
								DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(),clientAddress, 1500);
								try {
									dgramSocket_server.send(outPacket);
								} 
								catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						else {	
								if(message.indexOf(" / ")!=-1){						//un autre utilisateur change de nom, reception 1 nouveau nom / 1 ancien nom
									String[] name_oldname=message.split(" / ");
									System.out.println(" son ancien nom : " + name_oldname[1]);
									System.out.println(" son nvx nom : " + name_oldname[0]);
									if(name_oldname[0].equals(MyList_user.get_name())) {
									}
									else {
										MyList_user.maj_list_user(name_oldname[1],name_oldname[0]);
									}
								}
								else if(message.indexOf(" # ")!=-1){				//un autre utilisateur se deconnecte, reception 1 nouveau nom # Disconnect
									String[] name_disconnect=message.split(" # ");
									System.out.println(" deconnection : son nom  : " + name_disconnect[0]);

									MyList_user.maj_list_user_disconnection(name_disconnect[0]);
								}
								else {												//un autre utilisateur se connecte et nous envoie son nom
									MyList_user.maj_list_user_addr(inPacket.getAddress());
									System.out.println("addresse destinataire : " + inPacket.getAddress());
									MyList_user.maj_list_user(message); 	
								}
						}
					}
			}		
	}
	public void close_socket() {
		ok_boucle=false;
		dgramSocket_server.close();
	}
	
	
}
