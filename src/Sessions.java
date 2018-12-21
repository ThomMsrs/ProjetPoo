import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.glass.events.KeyEvent;

public class Sessions implements ActionListener{
	
	
	int nbrsession=0;
	Session[] Tab_Session;
	historique MyHistorique;
	ServerTCP MyServerTCP ;
	ClientTCP MyClientTCP;
	login MyLogin;
	int last_port;
	
	
	public JLabel ask_sessionLabel;
	JPanel pane5;
	JButton yesButton;
	JButton noButton;

	int flag_ask_session=0;
	
	
	
	
	
	public Sessions (login Login) {
		System.out.println("ok sessions");
		MyLogin=Login;
		last_port=2000;
		Tab_Session=new Session[50];
		//MyHistorique= new historique();
		MyServerTCP=new ServerTCP(this,1700);			// permet de savoir si on veut nous contacter pour demarrer une session
		MyServerTCP.start();
	}
	
	
	
	public void demande_new_session(String user_dest) { 
		int i=0;
		String[] list_user=MyLogin.get_user_list();		// faire une methode public get_user(string user)
		while(list_user[i]!=user_dest) {
			i++;
		}
		MyClientTCP = new ClientTCP(this,1700,"ASK_FOR_NEW_SESSION", MyLogin.get_user_list_addr(i));
		MyClientTCP.start();
	}
	

	
	public void new_session(String user_dest, String port_dest) {				//creer une session avec un utilisateur
		
		
		
		
		//int num_port_dest=Integer.parseInt(port_dest);
		int i=0;
		String[] list_user=MyLogin.get_user_list();		// faire une methode get_user(string user)
		while(list_user[i]!=user_dest) {
			i++;
		}
		Tab_Session[nbrsession]= new Session(this,user_dest,MyLogin.get_user_list_addr(i),last_port,Integer.parseInt(port_dest));			//envoyer le numero de port !!!	
		
		
		
		// test
		
		//Tab_Session[nbrsession]= new Session(this, user_dest, MyLogin.get_addr2(),last_port, Integer.parseInt(port_dest));
		
		// fin test
		
		last_port++;
		nbrsession++;
		
		
	}
	
	
	public int get_last_port() {
		return last_port;
	}
	
	
	public void close_session(String user_dest) {
		
	}
	
	public String get_name() {
		return MyLogin.get_name();
	}
	
	public void add_to_historique(String user1, String user2, String conv/*date?*/) {
		MyHistorique.add_to_historique(user1, user2, conv);
	}
	
	public Session get_session(String user_dest) {
		int i=0;
		while(Tab_Session[i].get_user_dest()!=user_dest) {
			i++;
		}
		return Tab_Session[i];
	}
	
	public int get_flag_ask_session() {
		return flag_ask_session;
	}
 
	
	
	public void windows_ask_session() {	
		flag_ask_session=1;
    	yesButton = new JButton("YES");
		yesButton.setMnemonic(KeyEvent.VK_I);
		yesButton.addActionListener(this);
		
		noButton = new JButton("NO");
		noButton.setMnemonic(KeyEvent.VK_I);
		noButton.addActionListener(this);
		
		ask_sessionLabel = new JLabel();
		//ask_sessionLabel = new JLabel("demarrer session avec " + MyLogin.list.getSelectedValue());
		 
		pane5 = new JPanel(new GridLayout(0, 1));
		pane5.add(ask_sessionLabel);
	    pane5.add(yesButton);
	    pane5.add(noButton);
	    
	    
	    pane5.setBorder(BorderFactory.createEmptyBorder(
	                15, //top
	                15, //left
	                5, //bottom
	                15)); //right
	     
	    pane5.setBorder(BorderFactory.createCompoundBorder(
		            BorderFactory.createTitledBorder("confirmer demande de session"), 
		            BorderFactory.createEmptyBorder(5,5,5,5)));
	     
		System.out.println("ok windows ask session");
      
    	MyLogin.pane7.add(pane5);
    	MyLogin.pane7.validate();

    	pane5.setVisible(true);
    }



	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("YES")) {
    		int i=MyLogin.list.getSelectedIndex();
			demande_new_session(MyLogin.MyList_user.get_user(i));
    		//new_session("choukou","1930");
			MyLogin.list.clearSelection();
			flag_ask_session=0;
			MyLogin.pane7.remove(pane5);
	    	MyLogin.pane7.validate();
		}
		if (event.getActionCommand().equals("NO")) {
			MyLogin.list.clearSelection();
			flag_ask_session=0;
			MyLogin.pane7.remove(pane5);
	    	MyLogin.pane7.validate();
	  
			System.out.println("ok nobutton");
		}
		
	}
	

}
