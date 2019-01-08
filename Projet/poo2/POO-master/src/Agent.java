import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

import com.sun.glass.events.KeyEvent;



public class Agent implements ActionListener{
	public String state;
	public String staterename;
	public login MyLogin;
	
	
	
	final static String LOOKANDFEEL = null ;
	static JFrame frame;
	JPanel MainPanel;
	
	JButton MainButton;
	JPanel pane1;
	JPanel pane2;
	
	
	

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
		
	    
		public static void main(String[] args) {
			      //Schedule a job for the event-dispatching thread:
			      //creating and showing this application's GUI.
			      javax.swing.SwingUtilities.invokeLater(new Runnable() {
			    	  public void run() {
			    		  createAndShowGUI();
			    	  }
			       });
		}
}