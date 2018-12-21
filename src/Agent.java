import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.glass.events.KeyEvent;



public class Agent implements ActionListener, ListSelectionListener{
	public String state;
	public String staterename;
	public login MyLogin;
	
	
	
	final static String LOOKANDFEEL = null ;
	static JFrame frame;
	JPanel MainPanel;
	
	JButton MainButton;
	JButton Set_nameButton;
	JTextField Set_nameTextField;
	JPanel pane1;
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
	
	public JLabel ask_sessionLabel;
	JPanel pane5;
	JButton yesButton;
	JButton noButton;
	int flag_ask_session=0;
	
	
	JButton session_sendButton;
	JButton session_closeButton;
	JTextField session_messageTextField;
	JPanel pane6;
	JLabel session_nameLabel;
	
	JPanel pane7;
	
	
	
	

		private static void initLookAndFeel() {
		        
		        // Swing allows you to specify which look and feel your program uses--Java,
		        // GTK+, Windows, and so on as shown below.
		        String lookAndFeel = null;
		        
		        if (LOOKANDFEEL != null) {
		            if (LOOKANDFEEL.equals("Metal")) {
		                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
		            } else if (LOOKANDFEEL.equals("System")) {
		                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		            } else if (LOOKANDFEEL.equals("Motif")) {
		                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		            } else if (LOOKANDFEEL.equals("GTK+")) { //new in 1.4.2
		                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
		            } else {
		                System.err.println("Unexpected value of LOOKANDFEEL specified: "
		                        + LOOKANDFEEL);
		                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
		            }
		            
		            try {
		                UIManager.setLookAndFeel(lookAndFeel);
		            } catch (ClassNotFoundException e) {
		                System.err.println("Couldn't find class for specified look and feel:"
		                        + lookAndFeel);
		                System.err.println("Did you include the L&F library in the class path?");
		                System.err.println("Using the default look and feel.");
		            } catch (UnsupportedLookAndFeelException e) {
		                System.err.println("Can't use the specified look and feel ("
		                        + lookAndFeel
		                        + ") on this platform.");
		                System.err.println("Using the default look and feel.");
		            } catch (Exception e) {
		                System.err.println("Couldn't get specified look and feel ("
		                        + lookAndFeel
		                        + "), for some reason.");
		                System.err.println("Using the default look and feel.");
		                e.printStackTrace();
		            }
		        }
		 }
		
		public Agent() {
			
			MainPanel= new JPanel(new GridLayout(0, 1));
			MainPanel.setBorder(BorderFactory.createEmptyBorder(
	                30, //top
	                30, //left
	                10, //bottom
	                30) //right
	                );
	   
			 MainButton = new JButton("Login");
			 MainButton.setMnemonic(KeyEvent.VK_I);
			 MainButton.addActionListener(this);
			 
			 //MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.PAGE_AXIS));
		     MainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		     

			 
			 pane1 = new JPanel(new GridLayout(0, 1));
		     pane1.add(MainButton);
		     pane1.setBorder(BorderFactory.createEmptyBorder(
		                150, //top
		                150, //left
		                50, //bottom
		                150) //right
		                );
		     MainPanel.add(pane1);
	    }

		
	    public void actionPerformed(ActionEvent event) {
	    	if (event.getActionCommand().equals("Login")){
				MyLogin = new login(this);
				//windows_set_name();
				//pane1.setVisible(false);
				
			}	
			if (event.getActionCommand().equals("CLOSE")){
				//MyLogin.MySessions.get_session(       ).fermer_session();
				pane7.remove(pane6);;
				pane7.validate();
			}
			if (event.getActionCommand().equals("SEND")){
				System.out.println("msg send "  );
				//MyLogin.MySessions.get_session(       ).envoie_de_message(session_messageTextField.getText());
				
			}
	    }
		
	    
		
	    private static void createAndShowGUI() {
	    	initLookAndFeel();
	    	Agent MyAgent = new Agent();
	    	
	        //Make sure we have nice window decorations.
	        JFrame.setDefaultLookAndFeelDecorated(true);
	        
	        //Create and set up the window.
	        frame = new JFrame("Agent");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setContentPane(MyAgent.MainPanel);

	   
	        
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }
		
		
	   
	    public void windows_ask_session() {	
	    	flag_ask_session=1;
	    	 yesButton = new JButton("YES");
			 yesButton.setMnemonic(KeyEvent.VK_I);
			 yesButton.addActionListener(this);
			 
			 noButton = new JButton("NO");
			 noButton.setMnemonic(KeyEvent.VK_I);
			 noButton.addActionListener(this);
			 
			 ask_sessionLabel = new JLabel("demarrer session avec " + list.getSelectedValue());
			 
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
          
	    	pane7.add(pane5);
	    	pane7.validate();

	    	pane5.setVisible(true);
	    }
	    
	    
	    
	   
	    
		public static void main(String[] args) {
			      //Schedule a job for the event-dispatching thread:
			      //creating and showing this application's GUI.
			      javax.swing.SwingUtilities.invokeLater(new Runnable() {
			    	  public void run() {
			    		  createAndShowGUI();
			    	  }
			       });
		}


		public void valueChanged(ListSelectionEvent e) {
			if(flag_ask_session==0) {
				windows_ask_session();
			}
					
			//list.clearSelection();
		}
}
