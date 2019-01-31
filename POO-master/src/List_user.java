import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class List_user {
	
	private String[] list_user;
	private InetAddress[] list_user_addr;
	private String[] list_user_state;
	int nbr_user=0;
	ClientUDP MyClientUDP;
	ServerUDP MyServerUDP;
	ServerTCP MyServerTCP;
	ClientTCP MyClientTCP;
	login MyLogin;
	private int mode;
	
	public List_user(login Login, int mode_connexion) {
		mode=mode_connexion;
		System.out.println("ok dans List user");
		list_user=new  String[100000];
		list_user_addr=new InetAddress[100000];
		this.MyLogin=Login;
		if(mode==1) {
			MyServerUDP=new ServerUDP(this,1500);
			MyServerUDP.start();
		}
		/*if(mode==0) {
			MyClientTCP=new ClientTCP(this,2200);
			MyClientTCP.start();
			list_user_state= new String[100000];
		}
		*/

		//creation d'un ClientUDP extends threads qui va envoyer une demande de noms en broadcast
		//et stocker les resultats dans la list_user
	}
	
	public void ask_list_user() {
		MyClientUDP= new ClientUDP(1600,"ASK_FOR_NAME");		
		MyClientUDP.start();

		// demande en broadcast a toutes les machines du réseau, si leur agent est connecté,
		//leur nom et les stocks dans une list_user
		
	}
	
	public void maj_list_user(String old_name, String new_name) {		// on recoit un rename
			int i=0;
			while(list_user[i].equals(old_name)!=true) {
				i++;
			}
			list_user[i]=new_name;
			MyLogin.maj_display_list();
	}
	
	public void send_disconnection() {
		MyClientUDP= new ClientUDP(1601, MyLogin.get_name() + " # " + "Disconnect");		
		MyClientUDP.start();
		MyServerUDP.close_socket();
	}
	
	public void maj_list_user_disconnection(String name) {
		nbr_user--;
		int i=0;
		while(list_user[i].equals(name)!=true) {
			i++;
		}
		int j=i;
		int k=j+1;
		while(list_user[k]!=null) {
			list_user[j]=list_user[k];
			j++;
			k++;
		}
		MyLogin.maj_display_list();
	}
	
	
	
	// peut etre ne faire qu'une seul methode pour laddr et le nom
	public void maj_list_user(String name) {							// on recoit une nouvelle connexion
		list_user[nbr_user]=name;
		nbr_user++;
		if(name.equals(MyLogin.get_name())==false){
			MyLogin.add_element_to_list(name);
		}
	}
	
	public void maj_list_user_addr(InetAddress addr) {							// on recoit une nouvelle connexion
		list_user_addr[nbr_user]=addr;
	}
	
	
	public void send_new_name(String new_name, String old_name) {
		MyClientUDP= new ClientUDP(1601, new_name + " / " + old_name);			//envoie son blaze et ancien a tt le monde
		MyClientUDP.start();
	}
	
	public void send_new_name(String new_name) 
	{
		MyClientUDP= new ClientUDP(1602,new_name);			//envoie son blaze et son addresse a tt le monde
		MyClientUDP.start();															
	}
	
	public String[] get_list_user() {
		return this.list_user;		
	}
	
	public int get_length_list_user() {
		return list_user.length ;
	}
	
	public String get_name() {
		return MyLogin.get_name();
	}
	
	public String get_addr() {
		return MyLogin.get_addr();
	}
	
	public InetAddress get_list_user_addr(int i) {
		return list_user_addr[i];
	}

	public String get_user(int i) {
		return list_user[i];
	}
	
	public void print_list() {
		int i=0;
		while(i<list_user.length) {
			if(list_user[i]!=null) {
				System.out.println("utilisateur numero :" + i );
				System.out.println(list_user[i]);
			}
			i++;
		}
	}
	
	public InetAddress get_addr(String user_name) {
		int i=0;
		while(list_user[i].contentEquals(user_name)!=true) {
			i++;
		}
		return list_user_addr[i];
	}
	
	public boolean check_name(String nom){
		ask_list_user();
		for(int i=0 ; i<list_user.length ; i++) {
			if (list_user[i]!=nom){
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	
	
}
