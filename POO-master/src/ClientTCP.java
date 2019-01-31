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
	private int i=0;
	private int port_dest;
	private List_user MyList_user;
	private int nouveau_port;
	private String nom;
	
	
	/*
	
	public ClientTCP(List_user list_user, int num_port) {
		super();
		port=num_port;
		MyList_user=list_user;
		
	}
	
	*/
	
	
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
							// affiche session refusé Nouveau panel ?
						//MySessions.windows_session_declined();	// nom user dest en argument ?
					}
					link.close();
	
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/* else if(port==2200) {
				try {
					Socket link = new Socket(addr_dest,port);
					PrintWriter out=new PrintWriter(link.getOutputStream(),true);
					out.println("COUCOU");
				
					BufferedReader in=new BufferedReader(new InputStreamReader(link.getInputStream()));
					input=in.readLine();
					nouveau_port=Integer.parseInt(input);
					link.close();
					
					
					link = new Socket(addr_dest,nouveau_port);
					PrintWriter out2=new PrintWriter(link.getOutputStream(),true);
					out2.println("GET_LIST_USER");
					input=in.readLine();
					while(input!="END#") {
						//envoie de nom / addr comme l'envoie de nom et ancien nom
						if(input.indexOf(" / ")!=-1){
							String[] user_name_addr=input.split(" / ");
							MyList_user.maj_list_user(user_name_addr[0]);
							MyList_user.maj_list_user_addr(InetAddress.getByAddress((Byte.decode(user_name_addr[1]))));	 // transformé string en inetaddresse
							input=in.readLine();
						}
					}
					
					
					
					MyList_user.MyLogin.windows_set_name();
					do {
						while(MyList_user.MyLogin.get_bouttonok()==0);
						//setbouttonok=0
						nom=MyList_user.MyLogin.Set_nameTextField.getText();
						if(MyList_user.check_name(nom)!=true){
							MyList_user.MyLogin.Set_nameLabel.setText("Wrong name");
						}
						else {
							MyList_user.MyLogin.windows_connected();
							MySessions = new Sessions(MyList_user.MyLogin);											// on peut nous contacter pour une session une fois que l'on est connecté
							System.out.println("ok apres creation de session une fois connecte");
							PrintWriter out3=new PrintWriter(link.getOutputStream(),true);
							out3.println(nom);
						
							MyList_user.MyLogin.MyAgent.MainPanel.remove(MyList_user.MyLogin.pane2);
							MyList_user.MyLogin.MyAgent.MainPanel.validate();
						}
					}
					while(MyList_user.check_name(nom)==true);
					
					
					 while(true){
						 
					 }
					 
				}catch (IOException e){
				
				}
			} */
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
