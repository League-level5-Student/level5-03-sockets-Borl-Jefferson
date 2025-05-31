package _02_Chat_Application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringJoiner;
import java.util.zip.Deflater;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
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
		
		
		msgs.setLayout(new BoxLayout(msgs, BoxLayout.Y_AXIS));
		scrollPane = new JScrollPane(msgs);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setVisible(true);
		add(scrollPane, BorderLayout.EAST);
		this.add(msgs);
		
		this.setPreferredSize(new Dimension(400, 600));
		
		
		int response = JOptionPane.showConfirmDialog(null, "Would you like to host a lobby?", "Buttons!", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION){
			System.out.println("test 1");
			server = new Server(8080, this);
			System.out.println("test 2");
			setTitle("SERVER");
			
			jetf.addKeyListener(this);
			jells.add(new JLabel());
			msgs.add(jells.get(0));
			jells.get(0).setSize(400, 15);
			//jells.get(0).setText("Server started at: " + server.getIPAddress() + " Port: " + server.getPort());
			
			jells.get(0).setText("Password: " + encodeIp(server.getIPAddress()));
			
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
			String ipStr=JOptionPane.showInputDialog("Enter lobby password");
			if(ipStr.equals(" ")) {
				ipStr="1hge9ba";
			}
			/*try {
				ipStr = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			int port = 8080;
			connectmsg = ipStr+" succesful";
			client = new Client(decodeIp(ipStr), port, this);
			
			jetf.addKeyListener(this);
			jells.add(new JLabel());
			msgs.add(jells.get(0), 0);
			
			jells.add(new JLabel());
			msgs.add(jells.get(jells.size()-1), jells.size()-1);
			//jells.get(jells.size()-1).setSize(400, 15);
			jells.get(jells.size()-1).setText("Attempting: "+ipStr+" . . .");
			
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
	 public static String encodeIp(String ipAddress) {
	        if (ipAddress == null || ipAddress.isEmpty()) {
	            throw new IllegalArgumentException("IP address cannot be null or empty.");
	        }

	        String[] octets = ipAddress.split("\\.");
	        if (octets.length != 4) {
	            throw new IllegalArgumentException("Invalid IP address format. Expected 4 octets.");
	        }

	        long numericIp = 0;
	        try {
	            for (int i = 0; i < 4; i++) {
	                int octet = Integer.parseInt(octets[i]);
	                if (octet < 0 || octet > 255) {
	                    throw new IllegalArgumentException("Invalid octet value: " + octet + ". Octet must be between 0 and 255.");
	                }
	                numericIp |= ((long) octet << (24 - (8 * i)));
	            }
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("Invalid number in IP address octet.", e);
	        }

	        // Convert the long to a Base36 string.
	        // 0L will be "0". 4294967295L (255.255.255.255) will be "zik0zj".
	        return Long.toString(numericIp, 36);
	    }
	 public static String decodeIp(String encodedIp) {
	        if (encodedIp == null || encodedIp.isEmpty()) {
	            throw new IllegalArgumentException("Encoded IP string cannot be null or empty.");
	        }

	        long numericIp;
	        try {
	            // Parse the Base36 string back to a long.
	            numericIp = Long.parseLong(encodedIp, 36);
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("Invalid encoded IP string format for Base36.", e);
	        }

	        if (numericIp < 0 || numericIp > 0xFFFFFFFFL) { // 0xFFFFFFFFL is 2^32 - 1
	            throw new IllegalArgumentException("Decoded numeric IP is out of the valid 32-bit range.");
	        }

	        // Extract octets
	        // Using StringJoiner for cleaner concatenation
	        StringJoiner ipJoiner = new StringJoiner(".");
	        ipJoiner.add(String.valueOf((numericIp >> 24) & 0xFF));
	        ipJoiner.add(String.valueOf((numericIp >> 16) & 0xFF));
	        ipJoiner.add(String.valueOf((numericIp >> 8) & 0xFF));
	        ipJoiner.add(String.valueOf(numericIp & 0xFF));

	        return ipJoiner.toString();
	    }
}
