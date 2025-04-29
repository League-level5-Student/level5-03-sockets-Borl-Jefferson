package _00_Click_Chat.gui;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import _00_Click_Chat.networking.Client;
import _00_Click_Chat.networking.Server;

public class ButtonClicker extends JFrame {
	JButton button = new JButton("CLICK");
	JTextField jetf = new JTextField("message|");
	JLabel jell = new JLabel("");
	boolean good = false;
	Server server;
	Client client;
	
	
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
			JOptionPane.showMessageDialog(null, "Server started at: " + server.getIPAddress() + "\nPort: " + server.getPort());
			button.addActionListener((e)->{
				server.sendClick();
			});
			
			add(jetf);
			add(jell);
			add(button);
			jell.setSize(400, 150);
			jell.setLocation(0, 180);
			jetf.setSize(400, 30);
			button.setSize(50, 50);
			this.pack();
			setVisible(true);
			setSize(400, 300);
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
			client = new Client(ipStr, port, this);
			button.addActionListener((e)->{
				client.sendClick();
			});
			add(jetf);
			add(jell);
			add(button);
			jell.setSize(400, 150);
			jell.setLocation(0, 180);
			jetf.setSize(400, 30);
			button.setSize(50, 50);
			this.pack();
			setVisible(true);
			setSize(400, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.start();
			good=true;
		}
		if(!good) {
			System.out.println("not good");
		}
	} 
	public void sendMessage() {
		jell.setText(jell.getText()+"\n"+"Client: "+jetf.getText());
	}
	public void setMessage(String msg) {
		jell.setText(msg);
	}
	public String getjell() {
		return jell.getText();
	}
}
