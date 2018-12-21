import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.sun.glass.events.KeyEvent;

public class Session implements ActionListener{
	private String user;
	private int num_session;
	private int port;
	private Sessions MySessions;
	private int port_dest;
	private InetAddress addr_dest;
	
	
	JButton session_sendButton;
	JButton session_closeButton;
	JTextField session_messageTextField;
	JPanel pane6;
	JLabel session_nameLabel;
	
	
	
	

	public Session(Sessions sessions,String destinataire,InetAddress addresse_destinataire, int num_port,int port_dest) {		// nouvelle session
		MySessions=sessions;
		this.user = destinataire;
		this.port=num_port;
		this.port_dest=port_dest;
		this.addr_dest=addresse_destinataire;
		//afficher_fenetre();
		message_recu(port);
		windows_session(user);
	}
	

	public Session(String destinataire, String[] historique_conv) {		// session deja ouverte auparavant donc historique conv
		this.user = destinataire;
		windows_session(user);
		//afficher_fenetre();
		//afficher fenetre avec l'historique
		message_recu(port);
	}
	
	
	public void envoie_de_message(String msg) {
		// afficher le message sur la fenetre + envoie message + add_to_historique
		ClientTCP MyClientTCP= new ClientTCP(this, port_dest,msg,addr_dest);
		MyClientTCP.start();
		// + afficher le message
		// + ajouter a lhisto_conversation
		MySessions.add_to_historique(MySessions.MyLogin.get_name(), MySessions.MyLogin.get_name(), msg);
	}
	
	public void message_recu(int num_port) {
		ServerTCP MyServeurTCP= new ServerTCP(this,port);
    	MyServeurTCP.start(); 
	}
	
	public String get_user_dest() {
		return this.user;
	}
	
	public void fermer_session() {
		ClientTCP MyClientTCP= new ClientTCP(this, port_dest,"CLOSE_SESSION",addr_dest);
		MyClientTCP.start();
	}

	
	public void windows_session(Object  object /*String name*/) {
   	 session_sendButton = new JButton("SEND");
		 session_sendButton.setMnemonic(KeyEvent.VK_I);
		 session_sendButton.addActionListener(this);
   	
		 session_closeButton = new JButton("CLOSE");
		 session_closeButton.setMnemonic(KeyEvent.VK_I);
		 session_closeButton.addActionListener(this);
   	
		 
		 
		session_messageTextField= new JTextField();
		
		/* LABEL POUR LHISTORIQUE ??? */
		//session_nameLabel = new JLabel(name);
		session_nameLabel = new JLabel(object.toString());
		
		 pane6 = new JPanel(new GridLayout(0, 1));
	     
	     pane6.add(session_nameLabel);
	     pane6.add(session_messageTextField);
	     pane6.add(session_sendButton);
	     pane6.add(session_closeButton);
	     pane6.setBorder(BorderFactory.createEmptyBorder(
	                15, //top
	                15, //left
	                5, //bottom
	                15)); //right
	     pane6.setBorder(BorderFactory.createCompoundBorder(
		            BorderFactory.createTitledBorder("Session with " + object.toString()), 
		            BorderFactory.createEmptyBorder(5,5,5,5)));
	     
	     MySessions.MyLogin.pane7.add(pane6);
	     pane6.setVisible(true);
   }
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("CLOSE")){
			fermer_session();
			MySessions.MyLogin.pane7.remove(pane6);
			MySessions.MyLogin.pane7.validate();
		}
		if (event.getActionCommand().equals("SEND")){
			System.out.println("msg send "  );
			envoie_de_message(session_messageTextField.getText());
		}
		
	}
	
}
