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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.glass.events.KeyEvent;

public class Session implements ActionListener, ListSelectionListener{
	private String user;
	private int port;
	private Sessions MySessions;
	private int port_dest;
	private InetAddress addr_dest;
	
	
	JButton session_sendButton;
	JButton session_closeButton;
	JTextField session_messageTextField;
	JPanel pane6;
	JLabel session_nameLabel;
	JList list_conv;
	DefaultListModel listmodel;

	public Session(Sessions sessions,String destinataire,InetAddress addresse_destinataire, int num_port,int port_dest) {		// nouvelle session
		MySessions=sessions;
		this.user = destinataire;
		this.port=num_port;
		this.port_dest=port_dest;
		this.addr_dest=addresse_destinataire;
		System.out.println(" ok ouverture de la session 1");
		message_recu(port);
		System.out.println(" ok ouverture de la session 2");
		windows_session(user);
		System.out.println(" ok ouverture de la session 3");
	}
	
/*
	public Session(String destinataire, String[] historique_conv) {		// session deja ouverte auparavant donc historique conv
		this.user = destinataire;
		windows_session(user);
		//afficher_fenetre();
		//afficher fenetre avec l'historique
		message_recu(port);
	}
	
*/
	
	public void envoie_de_message(String msg) {
		// afficher le message sur la fenetre + envoie message + add_to_historique
		ClientTCP MyClientTCP= new ClientTCP(this, port_dest,msg,addr_dest);
		MyClientTCP.start();
		// + afficher le message
		// + ajouter a lhisto_conversation
		//MySessions.add_to_historique(MySessions.MyLogin.get_name(), MySessions.MyLogin.get_name(), msg);
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
	
	public int get_port_dest() {
		return this.port_dest;
	}

	public InetAddress get_addr_dest() {
		return this.addr_dest;
	}
	
	public void windows_session(String name) {
   	 session_sendButton = new JButton("SEND");
		 session_sendButton.setMnemonic(KeyEvent.VK_I);
		 session_sendButton.addActionListener(this);
   	
		 session_closeButton = new JButton("CLOSE");
		 session_closeButton.setMnemonic(KeyEvent.VK_I);
		 session_closeButton.addActionListener(this);
		 
		session_messageTextField= new JTextField();
		
		/* LABEL POUR LHISTORIQUE ??? */
		/* si historique afficher un listmodel */
		//session_nameLabel = new JLabel(name);
		
		listmodel=new DefaultListModel();
		
		list_conv = new JList(listmodel); 
    	list_conv.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    	list_conv.setLayoutOrientation(JList.VERTICAL_WRAP);
    	list_conv.setVisibleRowCount(-1);
    	JScrollPane listScroller = new JScrollPane(list_conv);
    	listScroller.setPreferredSize(new Dimension(150, 40));
    	list_conv.addListSelectionListener(this);
		
		 pane6 = new JPanel(new GridLayout(0, 1));
	     
	     //pane6.add(session_nameLabel);
		 pane6.add(list_conv);
	     pane6.add(session_messageTextField);
	     pane6.add(session_sendButton);
	     pane6.add(session_closeButton);
	     pane6.setBorder(BorderFactory.createEmptyBorder(
	                15, //top
	                15, //left
	                5, //bottom
	                15)); //right
	     pane6.setBorder(BorderFactory.createCompoundBorder(
		            BorderFactory.createTitledBorder("Session with " + name), 
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
			listmodel.addElement(MySessions.MyLogin.get_name() + " : " + session_messageTextField.getText());
			list_conv.setModel(listmodel);
			envoie_de_message(session_messageTextField.getText());
			session_messageTextField.setText("");
		}
		
	}


	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
