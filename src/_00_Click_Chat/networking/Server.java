package _00_Click_Chat.networking;
/*
 * have jetf on both sides and a jlabel
 * have message include previous messages and show everything on the same panel
*/
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import _00_Click_Chat.gui.ButtonClicker;
 
public class Server {
	private int port;
	ButtonClicker bc;
	private ServerSocket server;
	private Socket connection;

	ObjectOutputStream os;
	ObjectInputStream is;

	public Server(int port, ButtonClicker bc) {
		this.port = port;
		this.bc = bc;
	}

	public void start(){
		try {
			
			server = new ServerSocket(port, 100);

			connection = server.accept();

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

			os.flush();

			while (connection.isConnected()) {
				try {
					//JOptionPane.showMessageDialog(null, is.readObject());
					bc.setMessage(is.readObject().toString());
					//System.out.println(is.readObject());
				}catch(EOFException e) {
					JOptionPane.showMessageDialog(null, "Client disconnected");
					System.exit(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "ERROR!!!!!";
		}
	}

	public int getPort() {
		return port;
	}

	public void sendClick() {
		try {
			if (os != null) {
				bc.sendMessage(true);
				os.writeObject(bc.getjell());
				
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
