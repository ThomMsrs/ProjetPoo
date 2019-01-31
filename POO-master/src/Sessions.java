import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.glass.events.KeyEvent;

public class Sessions implements ActionListener{
	
	
	private	int nbrsession=0;
	private Session[] Tab_Session;
	private	ServerTCP MyServerTCP ;
	private	ClientTCP MyClientTCP;
	private	login MyLogin;
	private	int last_port;
	
	
	private JLabel ask_sessionLabel;
	private	JPanel pane5;
	private JButton yesButton;
	private JButton noButton;

	private int flag_ask_session=0;
	
	private Connection con=null;

	
	
	
	public Sessions (login Login) {
		System.out.println("ok sessions");
		MyLogin=Login;
		last_port=2000;
		Tab_Session=new Session[50];
		//MyHistorique= new historique();
		MyServerTCP=new ServerTCP(this,1700);			// permet de savoir si on veut nous contacter pour demarrer une session
		MyServerTCP.start();
		
		
		
		try {

		    Class.forName( "com.mysql.jdbc.Driver" );

		} catch ( ClassNotFoundException e ) {

		    /* Gérer les éventuelles erreurs ici. */

		}
		//con = DriverManager.getConnection( /* URL DE LA BASE DE DONNEE SQL */ );
		
	}
	
	public void close_socket() {
		MyServerTCP.close_socketserver();
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

		
		
		System.out.println(" ouverture de la session ");
		Tab_Session[nbrsession]= new Session(this,user_dest,MyLogin.get_addr(user_dest),last_port,Integer.parseInt(port_dest),con);	
		System.out.println("  session ouverte");
		
		
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
		
		
		ask_sessionLabel = new JLabel("demarrer session avec " + MyLogin.get_list().getSelectedValue());
		 
		pane5 = new JPanel(new GridLayout(0, 1));
		pane5.add(getAsk_sessionLabel());
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
      
    	MyLogin.get_pane7().add(pane5);
    	MyLogin.get_pane7().validate();

    	pane5.setVisible(true);
    }



	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("YES")) {
    		int i=MyLogin.get_list().getSelectedIndex();
			demande_new_session(MyLogin.get_user(i));
			MyLogin.get_list().clearSelection();
			flag_ask_session=0;
			MyLogin.get_pane7().remove(pane5);
	    	MyLogin.get_pane7().validate();
		}
		if (event.getActionCommand().equals("NO")) {
			MyLogin.get_list().clearSelection();
			flag_ask_session=0;
			MyLogin.get_pane7().remove(pane5);
	    	MyLogin.get_pane7().validate();
	  
			System.out.println("ok nobutton");
		}
		
	}

	public JLabel getAsk_sessionLabel() {
		return ask_sessionLabel;
	}

	

}
