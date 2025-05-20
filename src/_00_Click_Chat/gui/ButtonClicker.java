package _00_Click_Chat.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import _00_Click_Chat.networking.Client;
import _00_Click_Chat.networking.Server;

public class ButtonClicker extends JFrame implements KeyListener {
	//JButton button = new JButton("Send message");
	JTextField jetf = new JTextField("Replace with message");
	JLabel jell = new JLabel("");
	ArrayList<JLabel> jells = new ArrayList<JLabel>();
	boolean good = false;
	Server server;
	Client client;
	String connectmsg = "not set";
	boolean isserver=true;
	JPanel msgs = new JPanel();
	JScrollPane listScroller = new JScrollPane();
	public static void main(String[] args) {
		new ButtonClicker();
	}
	
	public ButtonClicker(){
		//BorderLayout bl = new BorderLayout();
		jetf.setSize(400, 30);
		this.add(jetf, BorderLayout.NORTH);
		listScroller.setAlignmentX(LEFT_ALIGNMENT);
		msgs.add(listScroller);
		
		this.add(msgs);
		msgs.setLayout(new BoxLayout(msgs, BoxLayout.Y_AXIS));
		
		
		this.setPreferredSize(new Dimension(400, 600));
		
		// pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		int response = JOptionPane.showConfirmDialog(null, "Would you like to host a connection?", "Buttons!", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION){
			System.out.println("test 1");
			server = new Server(8080, this);
			System.out.println("test 2");
			setTitle("SERVER");
			//JOptionPane.showMessageDialog(null, "Server started at: " + server.getIPAddress() + "\nPort: " + server.getPort());
			/*button.addActionListener((e)->{
				server.sendClick();
			});*/
			
			jetf.addKeyListener(this);
			//this.getContentPane().add(jetf);
			//this.getcontepadd(jetf);
			jells.add(new JLabel());
			msgs.add(jells.get(0));
			//add(button);
			jells.get(0).setSize(400, 15);
		//	jells.get(0).setLocation(10, 480);
			jells.get(0).setText("Server started at: " + server.getIPAddress() + "\nPort: " + server.getPort());
			//jell.setLocation(0, 180);
			//button.setSize(50, 50);
			this.pack();
			setVisible(true);
			setSize(400, 600);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			server.start();
			good=true;
			
		}else{
			setTitle("CLIENT");
			isserver=false;
			String ipStr="";
			try {
				ipStr = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//JOptionPane.showInputDialog("Enter the IP Address");
			String prtStr = "8080";//JOptionPane.showInputDialog("Enter the port number");
			int port = Integer.parseInt(prtStr);
			connectmsg = "Connected to: " +ipStr + " Port: " + prtStr;
			client = new Client(ipStr, port, this);
			/*button.addActionListener((e)->{
				client.sendClick();
			});*/
		//	this.setPreferredSize(new Dimension(400, 600));
			jetf.addKeyListener(this);
			//this.getContentPane().add(jetf);
			jells.add(new JLabel());
			msgs.add(jells.get(0), 0);
			//add(button);
			jells.get(0).setSize(400, 15);
			//jells.get(0).setLocation(10, 480);
			//jells.get(0).setText("debug");
			jetf.setSize(400, 30);
			//button.setSize(50, 50);
			this.pack();
			setVisible(true);
			setSize(400, 600);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.start();
			good=true;
		}
		if(!good) {
			System.out.println("not good");
		}
	} 
	public void sendMessage(boolean isserver) {
		System.out.println("SEND MESSAGE");
		String msg = "MESSAGE NOT SET";
		
		if(!isserver) {
		//msg=jell.getText()+"Client: "+jetf.getText();
		msg="Client: "+jetf.getText();
		}else {
		//	jells.get(0).setText("");
			//msg=jell.getText()+"Server: "+jetf.getText();
			msg="Server: "+jetf.getText();
		
		}
		
		for (int i = 0; i < jells.size(); i++) {
			//jells.get(i).setBounds(10, this.getSize().height-50-((jells.size()-i)*20), 400, 20);
			System.out.println(jells.get(i).getText()+" at "+(this.getSize().height-30-((jells.size()-i)*20))+" at "+i);
		}
		
		
			
			jells.add(new JLabel());
			msgs.add(jells.get(jells.size()-1), jells.size()-1);
			jells.get(jells.size()-1).setSize(400, 15);
		jells.get(jells.size()-1).setText(jells.size()-2+") "+msg);
	
	//	jells.get(jells.size()-1).setBounds(10, this.getSize().height-50, 400, 20);
		
		System.out.println(jells.get(jells.size()-1).getText()+" size-1 at "+(this.getSize().height-50));
		this.setPreferredSize(this.getSize());
		this.pack();
	}
	
	
	public void setMessage(String msg) {
		if(isserver) {
		//	jells.get(0).setText("");
		}
		for (int i = 0; i < jells.size(); i++) {
			//jells.get(i).setBounds(10, this.getSize().height-50-((jells.size()-i)*20), 400, 20);
		}
		jells.add(new JLabel());
		jells.get(jells.size()-1).setSize(400, 15);
	jells.get(jells.size()-1).setText(msg);
	this.msgs.add(jells.get(jells.size()-1));
//	jells.get(jells.size()-1).setBounds(10, this.getSize().height-50, 400, 20);
	this.setPreferredSize(this.getSize());
	this.pack();
	}
	
	
	
	public String getjell() {
		System.out.println(jells.get(jells.size()-1).getText());
		return jells.get(jells.size()-1).getText();
	}
	public String getconnectmsg() {
		return connectmsg;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(isserver) {
				server.sendClick();
			}else {
				client.sendClick();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
