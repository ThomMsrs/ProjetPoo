import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientTCP extends Thread {
	
	private int port;
	private InetAddress addr_dest;
	private String input;
	private String msg;
	private Sessions MySessions;
	private Session MySession;
	private String[] tab;

	
	
	public ClientTCP(Sessions sessions, int num_port, String message, InetAddress address_dest) {
		super();
		port=num_port;
		addr_dest=address_dest;
		msg=message;
		MySessions=sessions;
		tab=new String[2];
	}
	
	public ClientTCP(Session session, int num_port, String message, InetAddress address_dest) {
		super();
		port=num_port;
		addr_dest=address_dest;
		msg=message;
		MySession=session;
	}
	
	
	public void run(){
			if (port==1700) { 
				try {
					Socket link = new Socket(addr_dest,port);
					PrintWriter out=new PrintWriter(link.getOutputStream(),true);
					out.println(msg);
					
					
					
					BufferedReader in=new BufferedReader(new InputStreamReader(link.getInputStream()));
					input=in.readLine();
			
					if(input.equals("OK")){
						PrintWriter out2=new PrintWriter(link.getOutputStream(),true);					//Envoie du port utilisé pour la session
						out2.println(MySessions.get_last_port());
						input=in.readLine();															//attente de la reception du port
						tab[0]=input;
						
						System.out.println("tab 0 client  : " + tab[0]);
						
						PrintWriter out3=new PrintWriter(link.getOutputStream(),true);					//Envoie du nom utilisé pour la session
						out3.println(MySessions.get_name());
						input=in.readLine();															//attente de la reception du nom
						tab[1]=input;
						
						System.out.println("tab 1 client  : " + tab[1]);
						MySessions.new_session(tab[1],tab[0]);
					}
					if(input.equals("NOT_OK")) {
						//TODO
					}
					link.close();
	
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				Socket link;
				try {
					link = new Socket(addr_dest,port);
					PrintWriter out=new PrintWriter(link.getOutputStream(),true);
					out.println(msg);
					link.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
}
