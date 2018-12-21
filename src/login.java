
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
		private String nom2;
		List_user MyList_user;
		private boolean test_noms;
		Sessions MySessions;
		private InetAddress MyAddress;	//Mon adresse en inetaddress
		private String Address;			//Mon adresse en String
		Agent MyAgent;
		
		JButton Set_nameButton;
		JTextField Set_nameTextField;
		JPanel pane2;
		public JLabel Set_nameLabel;
		JList list;
		DefaultListModel listmodel;
		JButton RenameButton;
		JLabel PrintName;
		JPanel pane3;
		JButton DisconnectButton;
		
		
		public JLabel Set_renameLabel;
		JPanel pane4;
		JButton Set_renameButton;
		JTextField Set_renameTextField;
		
		JPanel pane7;
		
		
		public login(Agent MyAgent) {
			this.MyAgent=MyAgent;
			windows_set_name();
			try {
				MyAddress=InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Address= MyAddress.toString();
			System.out.print(Address);
			System.out.println(" # ");
			MyList_user= new List_user(this);
		}
	/*
	public login(Agent MyAgent, String nom) throws IOException {
		this.MyAgent=MyAgent;
		System.out.println("ok apres appel de l'agent dans login");
		MyList_user= new List_user(this);
		System.out.println("ok apres ajout dans la liste user");
		try {
			MyAddress=InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.nom=nom;
		if ( nom.equals("")) {
			MyAgent.Set_nameLabel.setText("Wrong name");
		}
		else {
			System.out.println(nom);
			if ( check_name(nom)!=true) {
				MyAgent.Set_nameLabel.setText("Wrong name");
				//MyAgent.state="not_connected";
			}
			else {
				System.out.println("ok test avant enlevage panel");
				MyAgent.pane2.setVisible(false);//enlever le jpanel, setvisible(false) ???
				MyAgent.state="connected";
				System.out.println("ok test apres state connected");
				MySessions = new Sessions(this);	// on peut nous contacter pour une session une fois que l'on est connecté
				System.out.println("ok apres creation de session une fois connecte");
				Address=MyAddress.toString();
				MyList_user.send_new_name(nom);
			}
		}
	}
	*/
	
	
	private boolean check_name(String nom){
		MyList_user.ask_list_user();
		String[] list_user= MyList_user.get_list_user();
		for(int i=0;i< (MyList_user.get_length_list_user());i++) {
			if (list_user[i]!=nom){
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	
	
	public void rename(String nom) {
			if(check_name(nom)!=true) {
				Set_renameLabel.setText("Wrong name");
			}
			else {
				pane7.remove(pane4);
		    	pane7.validate();
				PrintName.setText("nom : " + nom);
				MyList_user.send_new_name(nom, this.nom);
				this.nom=nom;	
			}
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
    
    
    //gestion de la connexion 
    
    public void windows_connected() {
    	MyAgent.MainPanel.setLayout(new GridLayout(0, 2));
    	
    	listmodel=new DefaultListModel();
    	
    	String[] list_user=MyList_user.get_list_user();
    	for(int i=0;i<list_user.length;i++) {
    		listmodel.addElement(list_user[i]);
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
	    
	    
		System.out.println("ok windows list user");
		
		pane7 = new JPanel(new GridLayout(0, 1));
		
		pane7.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createTitledBorder("Sessions"), 
	            BorderFactory.createEmptyBorder(15,5,15,5)));
		
		
		
		
		
		
		
		
       MyAgent.MainPanel.add(pane3);
       MyAgent.MainPanel.add(pane7);
       MyAgent.MainPanel.remove(pane2);
       MyAgent.MainPanel.validate();
       
    	//frame.getContentPane().add(pane3, BorderLayout.CENTER);
    	pane3.setVisible(true);
    }
    
    //nommage et renommage 
    
    public void windows_set_rename() {
    	
    	System.out.println("ok set_rename 1 ");
    	 
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
			System.out.println("ok windows set rename");
        
		pane7.add(pane4);
			//frame.getContentPane().add(pane4, BorderLayout.CENTER);
    	pane4.setVisible(true);
    	
    	
    	System.out.println("ok set_rename 2 ");
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
		System.out.println("ok windows set name");
       
		
		MyAgent.MainPanel.add(pane2);
    	//frame.getContentPane().add(pane2, BorderLayout.CENTER);
    	pane2.setVisible(true);
    	MyAgent.MainPanel.remove(MyAgent.pane1);
    	MyAgent.MainPanel.validate();
    }



	
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("OK")) {
			nom=Set_nameTextField.getText();
			if(check_name(nom)!=true){
				Set_nameLabel.setText("Wrong name");
			}
			else {
				windows_connected();
				MySessions = new Sessions(this);											// on peut nous contacter pour une session une fois que l'on est connecté
				System.out.println("ok apres creation de session une fois connecte");
				MyList_user.send_new_name(nom);
				MyAgent.MainPanel.remove(pane2);
				MyAgent.MainPanel.validate();
			}
		}
		
		if(event.getActionCommand().equals("Rename")) {
			windows_set_rename();
			//pane3.setVisible(false);
			
		}

		
		if (event.getActionCommand().equals("OK RENAME")) {
			rename(Set_renameTextField.getText());
			}
  			//pane3.setVisible(true);
		}


		public void valueChanged(ListSelectionEvent e) {
			if(MySessions.get_flag_ask_session()==0) {
				MySessions.windows_ask_session();
				MySessions.ask_sessionLabel.setText("demarrer session avec " + list.getSelectedValue());
			}	
		}
		
    
    
	
	
}




