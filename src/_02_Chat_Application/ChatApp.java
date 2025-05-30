package _02_Chat_Application;

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
import javax.swing.ScrollPaneConstants;

import _00_Click_Chat.networking.Client;
import _00_Click_Chat.networking.Server;

public class ChatApp extends JFrame implements KeyListener {
	JTextField jetf = new JTextField("Replace with message");
	JLabel jell = new JLabel("");
	ArrayList<JLabel> jells = new ArrayList<JLabel>();
	boolean good = false;
	Server server;
	Client client;
	String connectmsg = "not set";
	boolean isserver=true;
	JPanel msgs = new JPanel();
	JScrollPane scrollPane;
	int count = 0;
	public static void main(String[] args) {
		new ChatApp();
	}
	
	public ChatApp(){
		jetf.setSize(400, 30);
		this.add(jetf, BorderLayout.NORTH);
		
		this.add(msgs);
		msgs.setLayout(new BoxLayout(msgs, BoxLayout.Y_AXIS));
		scrollPane = new JScrollPane(msgs);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setVisible(true);
		add(scrollPane, BorderLayout.EAST);
		
		this.setPreferredSize(new Dimension(400, 600));
		
		
		int response = JOptionPane.showConfirmDialog(null, "Would you like to host a connection?", "Buttons!", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION){
			System.out.println("test 1");
			server = new Server(8080, this);
			System.out.println("test 2");
			setTitle("SERVER");
			
			jetf.addKeyListener(this);
			jells.add(new JLabel());
			msgs.add(jells.get(0));
			jells.get(0).setSize(400, 15);
			jells.get(0).setText("Server started at: " + server.getIPAddress() + " Port: " + server.getPort());
			this.pack();
			setVisible(true);
			setSize(400, 600);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			msgs.setPreferredSize(new Dimension(this.getWidth()-35, msgs.getPreferredSize().height));
			server.start();
			good=true;
			
		}else{
			setTitle("CLIENT");
			isserver=false;
			String ipStr=JOptionPane.showInputDialog("Enter IP you're connecting to");
			/*try {
				ipStr = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			int port = 8080;
			connectmsg = "Connected to: " +ipStr + " Port: " + port;
			client = new Client(ipStr, port, this);
			
			jetf.addKeyListener(this);
			jells.add(new JLabel());
			msgs.add(jells.get(0), 0);
			
			jells.add(new JLabel());
			msgs.add(jells.get(jells.size()-1), jells.size()-1);
			//jells.get(jells.size()-1).setSize(400, 15);
			jells.get(jells.size()-1).setText("Attempting  "+ipStr+" . . .");
			
			jells.get(0).setSize(400, 15);
			jetf.setSize(400, 30);
			this.pack();
			setVisible(true);
			setSize(400, 600);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			msgs.setPreferredSize(new Dimension(this.getWidth()-35, msgs.getPreferredSize().height));
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
		msg="Client: "+jetf.getText();
		}else {
			msg="Server: "+jetf.getText();
		
		}
		
		
			
			jells.add(new JLabel());
			msgs.add(jells.get(jells.size()-1), jells.size()-1);
			jells.get(jells.size()-1).setSize(400, 15);
		jells.get(jells.size()-1).setText(count+") "+msg);
	
		count++;
		jetf.setText("");
		msgs.setPreferredSize(new Dimension(this.getWidth()-35, msgs.getPreferredSize().height));
		this.setPreferredSize(this.getSize());
		this.pack();
		
		scrollPane.getViewport().scrollRectToVisible(jells.get(jells.size()-1).getBounds());
	}
	
	
	public void setMessage(String msg) {
		
		jells.add(new JLabel());
		jells.get(jells.size()-1).setSize(400, 15);
	jells.get(jells.size()-1).setText(msg);
	msgs.add(jells.get(jells.size()-1));
	jetf.setText("");
	count++;
	scrollPane.getViewport().scrollRectToVisible(jells.get(jells.size()-1).getBounds());
	this.revalidate();
	this.repaint();
	if(!jells.get(jells.size()-2).getVisibleRect().isEmpty()) {
	scrollPane.getViewport().scrollRectToVisible(jells.get(jells.size()-1).getBounds());
	}
	msgs.setPreferredSize(new Dimension(this.getWidth()-35, msgs.getPreferredSize().height));
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
