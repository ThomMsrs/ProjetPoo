import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
	
	
	private	JButton session_sendButton;
	private	JButton session_closeButton;
	private	JTextField session_messageTextField;
	private	JPanel pane6;
	private	JLabel session_nameLabel;
	private	JList list_conv;
	private	DefaultListModel listmodel;
	
	private	JTextArea Chatarea;
	
	private   JFrame frame;
	private   Connection con2;

	public Session(Sessions sessions,String destinataire,InetAddress addresse_destinataire, int num_port,int port_dest, Connection con) {		// nouvelle session
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
		
		/*
		try {
			Statement statement=getCon2().createStatement();
			ResultSet rs=statement.executeQuery("SELECT message FROM Historique WHERE user_dest =" + this.user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
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
		ClientTCP MyClientTCP= new ClientTCP(this, port_dest,"CLOSE_SESSION1",addr_dest);
		MyClientTCP.start();
	}
	
	public int get_port_dest() {
		return this.port_dest;
	}
	
	public JList get_list() {
		return this.list_conv;
	}
	
	public DefaultListModel get_list_model() {
		return this.listmodel;
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
		session_messageTextField.addActionListener( action );
		
		
		listmodel=new DefaultListModel();
		
		list_conv = new JList(listmodel); 
    	list_conv.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    	list_conv.setLayoutOrientation(JList.VERTICAL_WRAP);
    	list_conv.setVisibleRowCount(-1);
    	JScrollPane listScroller = new JScrollPane();
    	listScroller.setPreferredSize(new Dimension(150, 40));
    	listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	//listScroller.setViewportView(list_conv);
    	listScroller.add(list_conv);
    	list_conv.addListSelectionListener(this);
		
		
		//Chatarea= new JTextArea();
		
		
		 pane6 = new JPanel(new GridLayout(0, 1));
		 
		/* pane6.add(Chatarea);*/
	     
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

	     JFrame.setDefaultLookAndFeelDecorated(true);
	        
	        //Create and set up the window.
	        frame = new JFrame("Agent");
	        
	        frame.setPreferredSize(new Dimension(400,800));

	        
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setContentPane(pane6);

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	     
	     pane6.setVisible(true);
   }
	
	
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("CLOSE")){
			fermer_session();
			remove_panel_session();
		}
		if (event.getActionCommand().equals("SEND")){
			
			
			System.out.println("msg send "  );
			
			
			SimpleDateFormat date=new SimpleDateFormat("h:mm a");
			
			
			listmodel.addElement(MySessions.get_name() + " : " + session_messageTextField.getText() + "                              " + date.format(new Date()));
			list_conv.setModel(listmodel);

			/*
			Chatarea.append(MySessions.MyLogin.get_name());
			Chatarea.append(" : " + session_messageTextField.getText()+ " ");
			Chatarea.append(date.format(new Date()) + "\n");
			*/
			
			envoie_de_message(session_messageTextField.getText());
			session_messageTextField.setText("");
			
			/*
			try {
				Statement statement=getCon2().createStatement();
				ResultSet rs=statement.executeQuery("INSERT INTO Historique VALUES (" + user + "," + MySessions.get_name() + " : " + session_messageTextField.getText() + "                         " + date.format(new Date()) + ")" );
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
		}	
	}
	
	public void remove_panel_session() {
		frame.dispose();
		/*
		MySessions.MyLogin.pane7.remove(pane6);
		MySessions.MyLogin.pane7.validate();*/
	}
	
	
	/* touche entrer pour envoyer */ 
	Action action = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e)
	    {
	    	System.out.println("msg send "  );
	    	SimpleDateFormat date=new SimpleDateFormat("h:mm a");
	    	
	    	listmodel.addElement(MySessions.get_name() + " : " + session_messageTextField.getText() + "                         " + date.format(new Date()));
			list_conv.setModel(listmodel);
			
			/*
			Chatarea.append(MySessions.MyLogin.get_name());
			Chatarea.append(" : " + session_messageTextField.getText()+ " ");
			Chatarea.append(date.format(new Date()) + "\n");
			*/
			
			envoie_de_message(session_messageTextField.getText());
			session_messageTextField.setText("");
	
			/*
			
			try {
				Statement statement=getCon2().createStatement();
				ResultSet rs=statement.executeQuery("INSERT INTO Historique VALUES (" + user + "," + MySessions.get_name() + " : " + session_messageTextField.getText() + "                         " + date.format(new Date()) + ")" );
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
	    }
	};


	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public Connection getCon2() {
		return con2;
	}

}
