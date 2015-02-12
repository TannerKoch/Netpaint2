package controller;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import model.Command;
import model.DisconnectCommand;
import model.Shape;
import model.UpdateTextCommand;
import view.Window;
/**
 * This class is the Client that handles the communication between the NetPaint window and 
 * the Server connecting all clients.
 * @author JamesDeveuve
 * @author TannerKoch
 */
public class Client extends JFrame {
	private String clientName;
	private Window window; 
	private JFrame frame;
	private Socket server; // connection to server
	private ObjectOutputStream out; // output stream
	private ObjectInputStream in; // input stream
	/**
	 * This private class within {@link Client} handles the connection to the Server.
	 * @author TannerKoch
	 * @author JamesDeveuve
	 *
	 */
	private class ServerHandler implements Runnable{
		/**
		 * This is the overridden method from the {@link Thread} class.
		 * This executes commands from the server, predominately the command
		 * to update the screen.
		 */
		public void run(){
			try{
				while(true){
					// read a command from server and execute it
					Command<Client> c = (Command<Client>)in.readObject();
					c.execute(Client.this);
				}
			}
			catch(SocketException e){
				return; // "gracefully" terminate after disconnect
			}
			catch (EOFException e) {
				return; // "gracefully" terminate
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * This method establishes the host address and port, as well as the current
	 * Clients user name. If all valid fields are passed, a connection to the {@link Server}
	 * will be established and this Clients name will be written to the Server.
	 */
	public Client() {
		// ask the user for a host, port, and user name
		String host = JOptionPane.showInputDialog("Host address:");
		String port = JOptionPane.showInputDialog("Host port:");
		clientName = JOptionPane.showInputDialog("User name:");
		
		if (host == null || port == null || clientName == null)
			return;
		
		try{
			// Open a connection to the server
			server = new Socket(host, Integer.parseInt(port));
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
			
			// write out the name of this client
			out.writeObject(clientName);
			
			// add a listener that sends a disconnect command to when closing
			this.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent arg0) {
					try {
						out.writeObject(new DisconnectCommand(clientName));
						out.close();
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
			setupGUI();
			
			// start a thread for handling server events
			new Thread(new ServerHandler()).start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This method instantiates the NetPaint {@link Window} class and initializes
	 * the visible JFrame.
	 */
	private void setupGUI() {
		//this.setSize(800, 800);
		// create chatPanel 
		//add the output stream later (new Window(out))
		window = new Window(out);
		/////////////////////////////////////
		// add a Chat Panel
		frame = window;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.setVisible(true);
	}
	/**
	 * This method instantiates the client will called.
	 * @param args
	 */
	public static void main (String args[]){
		new Client();
		
	}
	/**
	 * This method passes a list of shapes to the Window so that it can correctly update the
	 * screen.
	 * @param shapes The list of drawn shapes to be updated.
	 */
	public void update(List<Shape> shapes) {
		window.update(shapes);
		// how can we get our list from server to all of our clients to update the screen
		//
	}
	/**
	 * This method sets the text under the NetPaint window. Only called when a client
	 * is connected so their client name is broadcasted to the other Clients.
	 * @param t The client name.
	 */
	public void setText(String t) {
		Window.setText(t);
	}
}
