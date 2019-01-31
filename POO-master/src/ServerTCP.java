import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerTCP extends Thread{
	private int port;
	private Sessions MySessionsUsed;
	private Session MySessionUsed;
	private String input;
	private int last_num_port;
	private String[] tab;
	int i = 0;
	int flag=1;
	
	
	
	
	public ServerTCP(Sessions sessions,int num_port) {
		super();
		System.out.println("ok Serveur TCP");
		tab=new String[2];
		port=num_port;
		MySessionsUsed=sessions;
	}
	
	
	public ServerTCP(Session session,int num_port) {
		super();
		port=num_port;
		MySessionUsed=session;
	}
	
	public void run() {
		ServerSocket servSocket;
		try {
			servSocket = new ServerSocket(port);
			while(flag==1){
				Socket link=servSocket.accept();
				
				//on attent la reponse avant de retourner en accept  + attente de reception de toute les reponses ( 3 en tout )for( int k=0;k<3;k++){
				if(port==1700) { 
						BufferedReader in=new BufferedReader(new InputStreamReader(link.getInputStream()));
						input=in.readLine();
	
						if(input.equals("ASK_FOR_NEW_SESSION")) {
							PrintWriter out=new PrintWriter(link.getOutputStream(),true);
							out.println("OK");
							
							/*PrintWriter out=new PrintWriter(link.getOutputStream(),true);
							out.println("NOT_OK");*/
							
							input=in.readLine();
							tab[0] = input;																			// envoie du port
							PrintWriter out2=new PrintWriter(link.getOutputStream(),true);
							out2.println(MySessionsUsed.get_last_port());
							
							System.out.println("tab 0: " + tab[0]);
							
							input=in.readLine();
							tab[1] = input;																			// envoie du nom
							PrintWriter out3=new PrintWriter(link.getOutputStream(),true);
							out3.println(MySessionsUsed.get_name());
							
							System.out.println("tab 1: " + tab[1]);
							
							
							MySessionsUsed.new_session(tab[1],tab[0]);

							
						}
						link.close();
				}
				else {	// si on est sur un aute port (mode message session)
					BufferedReader in=new BufferedReader(new InputStreamReader(link.getInputStream()));
					input=in.readLine();
					if(input.equals("CLOSE_SESSION1")) {
						//afficher la session a ete fermée par votre correspondant 
						System.out.println("pq tu fermes mon gar t fou");
						ClientTCP MyClientTCP= new ClientTCP(MySessionUsed, MySessionUsed.get_port_dest(),"CLOSE_SESSION2",MySessionUsed.get_addr_dest());
						MyClientTCP.start();
						MySessionUsed.remove_panel_session();
						link.close();
						flag=0;
					}
					if(input.equals("CLOSE_SESSION2")) {
						System.out.println("pq tu fermes mon gar t fou 333333");
						link.close();
						flag=0;
					}
					System.out.println("pq tu fermes mon gar t fou 22222");
					SimpleDateFormat date=new SimpleDateFormat("h:mm a");
					//afficher le message sur la bonne session
					MySessionUsed.listmodel.addElement(MySessionUsed.get_user_dest() +  " : " + input + "  	                "  + date.format(new Date()) );
					MySessionUsed.list_conv.setModel(MySessionUsed.listmodel);
					
					
					try {
						Statement statement=MySessionUsed.con2.createStatement();
						ResultSet rs=statement.executeQuery("INSERT INTO Historique VALUES (" + MySessionUsed.get_user_dest() + "," + MySessionUsed.get_user_dest() + " : " + input  + "                         " + date.format(new Date()) + ")" );
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}



	
	


