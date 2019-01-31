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
	private	Sessions MySessions;
	private	Session MySession;
	private	int session_num;
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
				System.out.println("EROOROROROR1");
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
					System.out.println("il court il court le furet");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				message = new String(inPacket.getData(), 0, inPacket.getLength());
				System.out.println(" MESSAGE RECU : " + message);
				
				/* on compare le resultat de message : il peut contenir soit :
				 * 
				 * PORT 1500
				 * -1 : un nom, il faut mettre a jours notre tableau list_user 	et list_user_addr
				 * -2	: deux nom, remplacer lancien nom par le nouveau dans list_user
				 * -3 : une demande de nom, il faut renvoyer notre nom et adresse
				 */
					if(port==1500) {	// pas besoin de ce if
						if(message.equals("ASK_FOR_NAME")) {
							InetAddress clientAddress= inPacket.getAddress();
							int clientPort = inPacket.getPort();
							if(MyList_user.get_name()!=null) {
								response=MyList_user.get_name();
								DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(),clientAddress, 1500);
								try {
									dgramSocket_server.send(outPacket);
								} 
								catch (IOException e) {
									System.out.println("Il repassera par là");
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						else {	// si 1 nouveau nom / 1 ancien nom
								if(message.indexOf(" / ")!=-1){
									System.out.println("le rename est présent quoi ");
									String[] name_oldname=message.split(" / ");
									System.out.println(" son ancien nom : " + name_oldname[1]);
									System.out.println(" son nvx nom : " + name_oldname[0]);
									if(name_oldname[0].equals(MyList_user.get_name())) {
										System.out.println("on maj list pas nouuus ?");
									}
									else {
										System.out.println("on maj list pas nouuus ? 2");
										MyList_user.maj_list_user(name_oldname[1],name_oldname[0]);
									}
								}
								else if(message.indexOf(" # ")!=-1){
									System.out.println("la deconnexion de l'utilisateur est présent quoi ");
									String[] name_disconnect=message.split(" # ");
									System.out.println(" son nom  : " + name_disconnect[0]);

									MyList_user.maj_list_user_disconnection(name_disconnect[0]);
								}
								else {
									System.out.println(message);
									MyList_user.maj_list_user_addr(inPacket.getAddress());
									System.out.println("addresse stock� : " + inPacket.getAddress());
									MyList_user.maj_list_user(message); 	
								}
						}
					}
			}
					
	}
	
	public void close_socket() {
		System.out.println("on y est !!");
		ok_boucle=false;
		dgramSocket_server.close();
	}
	
	
}
