package _00_Click_Chat.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import _00_Click_Chat.gui.ButtonClicker;
import _02_Chat_Application.ChatApp;

public class Client {
	private String ip;
	private int port;
	ChatApp bc;
	Socket connection;

	ObjectOutputStream os;
	ObjectInputStream is;

	public Client(String ip, int port, ChatApp bc) {
		this.ip = ip;
		this.port = port;
		this.bc = bc;
	}

	public void start(){
		try {

			connection = new Socket(ip, port);
			bc.setMessage(bc.getconnectmsg());

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

			os.flush();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (connection.isConnected()) {
			try {
				bc.setMessage(is.readObject().toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sendClick() {
		try {
			if (os != null) {
				bc.sendMessage(false);
				os.writeObject(bc.getjell());
				
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
