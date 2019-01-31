
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

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


public class login implements ActionListener, ListSelectionListener {					
		
		private String nom;
		private List_user MyList_user;
		private Sessions MySessions;
		private InetAddress MyAddress;	//Mon adresse en inetaddress
		private String Address;			//Mon adresse en String
		private Agent MyAgent;
		private int bouttonok=0;
		private boolean windows_connected_ok;
		
		private JButton Set_nameButton;
		private JTextField Set_nameTextField;
		private JPanel pane2;
		private JLabel Set_nameLabel;
		private	JList list;
		private DefaultListModel listmodel;
		private	JButton RenameButton;
		private	JLabel PrintName;
		private	JPanel pane3;
		private	JButton DisconnectButton;
		
		
		
		private JLabel Set_renameLabel;
		private	JPanel pane4;
		private	JButton Set_renameButton;
		private	JTextField Set_renameTextField;
		
		private		JPanel pane7;
		
		
		public login(Agent MyAgent, int mode_connexion) {
			this.MyAgent=MyAgent;
			if(mode_connexion==1) {
				windows_set_name();
			}
			try {
				MyAddress=InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Address= MyAddress.toString();
			System.out.print( "Mon addresse : " + Address);
			MyList_user= new List_user(this, mode_connexion);
			windows_connected_ok=false;
		}
	
	

	
    public String get_name() {
    	return nom;
    }
    
    public String get_addr() {
    	return Address;
    }
    
    public InetAddress get_addr2() {
    	return MyAddress;
    }
    
    public String[] get_user_list() {
    	return MyList_user.get_list_user();
    }
    
    public InetAddress get_user_list_addr(int i) {
    	return MyList_user.get_list_user_addr(i);
    }
    
    public int get_bouttonok() {
    	return bouttonok;
    }
    
    public InetAddress get_addr(String user_dest) {
    	return MyList_user.get_addr(user_dest);
    }
    
    public JPanel get_pane7() {
    	return this.pane7;
    }
    
    public JList get_list() {
    	return this.list;
    }
	
    public String get_user(int i) {
    	return MyList_user.get_user(i);
    }
    
    
    
    //gestion de la connexion 
    public boolean windows_connected_ok() {
    	return windows_connected_ok;
    }
    
    
    public void windows_connected() {
    	windows_connected_ok=true;
    	MyAgent.MainPanel.setLayout(new GridLayout(0, 2));
    	
    	listmodel=new DefaultListModel();
    	
    	String[] list_user=MyList_user.get_list_user();
    	for(int i=0;i<list_user.length;i++) {
    		if(list_user[i]!=null) {
    			listmodel.addElement(list_user[i]);
    		}
    	}
    	
    	
    	list = new JList(listmodel); 
    	list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    	list.setLayoutOrientation(JList.VERTICAL_WRAP);
    	list.setVisibleRowCount(-1);
    	JScrollPane listScroller = new JScrollPane(list);
    	listScroller.setPreferredSize(new Dimension(150, 40));
    	list.addListSelectionListener(this);
		 
    	
    	RenameButton= new JButton("Rename");
    	RenameButton.setMnemonic(KeyEvent.VK_I);
		RenameButton.addActionListener(this);
		
		DisconnectButton= new JButton("Disconnect");
    	DisconnectButton.setMnemonic(KeyEvent.VK_I);
		DisconnectButton.addActionListener(this);
		
		
		PrintName = new JLabel();
		PrintName.setText("nom : " + this.nom );
    	
    	
    	
		pane3 = new JPanel(new GridLayout(0, 1));
	    pane3.add(list);
	    pane3.add(RenameButton);
	    pane3.add(DisconnectButton);
	    pane3.add(PrintName);
		/*pane3.setBorder(BorderFactory.createEmptyBorder(
	                10, //top
	                10, //left
	                10, //bottom
	                300)); //right*/
	    
	    pane3.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createTitledBorder("Liste des utilisateurs"), 
	            BorderFactory.createEmptyBorder(15,5,15,5)));

		
		pane7 = new JPanel(new GridLayout(0, 1));
		
		pane7.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createTitledBorder("Sessions"), 
	            BorderFactory.createEmptyBorder(15,5,15,5)));
		
       MyAgent.MainPanel.add(pane3);
       MyAgent.MainPanel.add(pane7);
       MyAgent.MainPanel.remove(pane2);
       MyAgent.MainPanel.validate();
       
    	pane3.setVisible(true);
    }
    
    //nommage et renommage 
    
    public void windows_set_rename() {
    	 
    	 Set_renameTextField= new JTextField();
    	
    	 
    	 Set_renameButton = new JButton("OK RENAME");
		 Set_renameButton.setMnemonic(KeyEvent.VK_I);
		 Set_renameButton.addActionListener(this);
		 
		 Set_renameLabel = new JLabel("");
		 
		 pane4 = new JPanel(new GridLayout(0, 1));
	     pane4.add(Set_renameButton);
	     pane4.add(Set_renameTextField);
	     pane4.add(Set_renameLabel);
	     pane4.setBorder(BorderFactory.createEmptyBorder(
	                15, //top
	                15, //left
	                5, //bottom
	                15)); //right
        
		pane7.add(pane4);
			//frame.getContentPane().add(pane4, BorderLayout.CENTER);
    	pane4.setVisible(true);
    	pane7.validate();
    }
    
    public void windows_set_name() {
    	 
    	 Set_nameTextField= new JTextField();
    	
    	 
    	 Set_nameButton = new JButton("OK");
		 Set_nameButton.setMnemonic(KeyEvent.VK_I);
		 Set_nameButton.addActionListener(this);
		 
		 Set_nameLabel = new JLabel("");
		 
		 pane2 = new JPanel(new GridLayout(0, 1));
	     pane2.add(Set_nameButton);
	     pane2.add(Set_nameTextField);
	     pane2.add(Set_nameLabel);
	     pane2.setBorder(BorderFactory.createEmptyBorder(
	                15, //top
	                15, //left
	                5, //bottom
	                15)); //right
	     
		MyAgent.MainPanel.add(pane2);
    	//frame.getContentPane().add(pane2, BorderLayout.CENTER);
    	pane2.setVisible(true);
    	MyAgent.MainPanel.remove(MyAgent.get_pane1());
    	MyAgent.MainPanel.validate();
    }
    
	public void rename(String nom) {
		if(MyList_user.check_name(nom)!=true) {
			Set_renameLabel.setText("Wrong name");
		}
		else {
			pane4.setVisible(false);
			pane7.remove(pane4);
	    	pane7.validate();
			PrintName.setText("nom : " + nom);
			
			
			System.out.println("mon ancien nom : " + this.nom);
			System.out.println("mon nouveau nom : " + nom);
			
			/*
			listmodel.set(listmodel.indexOf(this.nom),nom);
			list.setModel(listmodel);*/
			
			MyList_user.send_new_name(nom, this.nom);
			this.nom=nom;	
		}
		
	}



	
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("OK")) {
			bouttonok=1;
			nom=Set_nameTextField.getText();
			if(MyList_user.check_name(nom)!=true){
				Set_nameLabel.setText("Wrong name");
			}
			else {
				windows_connected();
				MySessions = new Sessions(this);											// on peut nous contacter pour une session une fois que l'on est connecte
				MyList_user.send_new_name(nom);
				MyAgent.MainPanel.remove(pane2);
				MyAgent.MainPanel.validate();
			}
		}
		if(event.getActionCommand().equals("Rename")) {
			windows_set_rename();
		}
		if (event.getActionCommand().equals("OK RENAME")) {
			rename(Set_renameTextField.getText());
		}
		if (event.getActionCommand().equals("Disconnect")) {
			MyList_user.send_disconnection();
			MyAgent.get_frame().dispose();
		}
	}


		public void valueChanged(ListSelectionEvent e) {
			if(MySessions.get_flag_ask_session()==0) {
				MySessions.windows_ask_session();
				MySessions.getAsk_sessionLabel().setText("demarrer session avec " + list.getSelectedValue());		
			}	
		}
		
		public void add_element_to_list(String new_name) {
			listmodel.addElement(new_name);
			list.setModel(listmodel);
		}
		
		public void maj_display_list() {
			listmodel.clear();
			list.setModel(listmodel);
			String[] list_user=MyList_user.get_list_user();
	    	for(int i=0;i<list_user.length;i++) {
	    		if(list_user[i]!=null) {
	    			listmodel.addElement(list_user[i]);
	    		}
	    	}
	    	list.setModel(listmodel);
		}
    

	
}




