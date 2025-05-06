package _00_Click_Chat.gui;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import _00_Click_Chat.networking.Client;
import _00_Click_Chat.networking.Server;

public class ButtonClicker extends JFrame {
	JButton button = new JButton("Send message");
	JTextField jetf = new JTextField("Replace with message");
	JLabel jell = new JLabel("");
	ArrayList<JLabel> jells = new ArrayList<JLabel>();
	boolean good = false;
	Server server;
	Client client;
	String connectmsg = "not set";
	
	
	public static void main(String[] args) {
		new ButtonClicker();
	}
	
	public ButtonClicker(){
		
		int response = JOptionPane.showConfirmDialog(null, "Would you like to host a connection?", "Buttons!", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION){
			System.out.println("test 1");
			server = new Server(8080, this);
			System.out.println("test 2");
			setTitle("SERVER");
			//JOptionPane.showMessageDialog(null, "Server started at: " + server.getIPAddress() + "\nPort: " + server.getPort());
			button.addActionListener((e)->{
				server.sendClick();
			});
			 
			add(jetf);
			jells.add(new JLabel());
			add(jells.get(0));
			add(button);
			jells.get(0).setSize(400, 150);
			jells.get(0).setLocation(10, 480);
			jells.get(0).setText("Server started at: " + server.getIPAddress() + "\nPort: " + server.getPort());
			//jell.setLocation(0, 180);
			jetf.setSize(400, 30);
			button.setSize(50, 50);
			this.pack();
			setVisible(true);
			setSize(400, 600);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			server.start();
			good=true;
			
		}else{
			setTitle("CLIENT");
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
			button.addActionListener((e)->{
				client.sendClick();
			});
			add(jetf);
			jells.add(new JLabel());
			add(jells.get(0));
			add(button);
			jells.get(0).setSize(400, 150);
			jells.get(0).setLocation(10, 480);
			//jells.get(0).setText("debug");
			jetf.setSize(400, 30);
			button.setSize(50, 50);
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
		String msg = "MESSAGE NOT SET";
		if(!isserver) {
		//msg=jell.getText()+"Client: "+jetf.getText();
		msg="Client: "+jetf.getText();
		}else {
			
			//msg=jell.getText()+"Server: "+jetf.getText();
			msg="Server: "+jetf.getText();
		}
		
		for (int i = 0; i < jells.size(); i++) {
			jells.get(i).setLocation(10, 480-((jells.size()-i)*20));
		}
		jells.add(new JLabel());
		add(jells.get(jells.size()-1));
		jells.get(jells.size()-1).setSize(400, 150);
		jells.get(jells.size()-1).setLocation(10, 480);
		jells.get(jells.size()-1).setText(jells.size()-2+") "+msg);
		add(button);
		//button.setVisible(false);
		
	}
	
	
	public void setMessage(String msg) {
		for (int i = 0; i < jells.size(); i++) {
			jells.get(i).setLocation(10, 480-((jells.size()-i)*20));
		}
		jells.add(new JLabel());
		add(jells.get(jells.size()-1));
		jells.get(jells.size()-1).setSize(400, 150);
		jells.get(jells.size()-1).setLocation(10, 480);
		jells.get(jells.size()-1).setText(msg);
		add(button);
	}
	public String getjell() {
		System.out.println(jells.get(jells.size()-1).getText());
		return jells.get(jells.size()-1).getText();
	}
	public String getconnectmsg() {
		return connectmsg;
	}
}
