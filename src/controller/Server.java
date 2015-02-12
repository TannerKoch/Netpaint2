package controller;
import view.Window;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import model.AddObjectCommand;
import model.Command;
import model.DisconnectCommand;
import model.Shape;
import model.UpdateClientCommand;
import model.UpdateTextCommand;
/**
 * This class is the main server that contains the netpaint content for the {@link Client}
 * to connect to. This server accepts clients and dispatches them to a new thread, handling 
 * commands as they are received on that thread. Any shape drawn on the client side is
 * passed to the server's collection of shapes, then updates all of its connected clients.
 * @author TannerKoch
 * @author JamesDeveuve
 */
public class Server {
	
	private ServerSocket socket; // the server socket
	private HashMap<String, ObjectOutputStream> outputs; // map of all connected users' output streams
	private List<Shape> shapes;
	private String clientName;
	static ObjectOutputStream thisStream;
	/**
	 * This class is a private class within {@link Server} that handles the clients
	 * connecting to the server within its own thread. This is the class that deals
	 * with the handling of Commands received from clients.
	 * @author TannerKoch
	 * @author JamesDeveuve
	 *
	 */
	private class ClientHandler implements Runnable{
		private ObjectInputStream input; // the input stream from the client
		private ObjectOutputStream output;
		// added output stream to constructor and set it to an instance variable if 
		// that output draws an object 
		// at the bottom it won't redraw to this output stream.
		/**
		 * This constructor initializes an instance variable to handle this Clients information.
		 * The connectCommand() method is called in order to alert all clients that this new Client
		 * has connected to the Netpaint server.
		 * @param input Used to read the commands from this Client.
		 * @param output Used to make sure the drawing isn't duplicated when updateClients() is called.
		 * 
		 */
		public ClientHandler(ObjectInputStream input, ObjectOutputStream output){
			connectCommand();
			this.input = input;
			this.output = output;
		}
		/**
		 * This is the overridden method from the {@link Thread} class. 
		 * This method is executed by the thread and the thread will wait for
		 * a command to come in from the client, then handle it accordingly.
		 * 
		 */
		public void run() {
			try{
				while(true){
					// read a command from the client, execute on the server
					@SuppressWarnings("unchecked")
					Command<Server> command = (Command<Server>)input.readObject();
					if (command instanceof DisconnectCommand){
						input.close();
						return;
					}
					if (command instanceof AddObjectCommand) {
						thisStream = output;
					}
					command.execute(Server.this);
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}

		
	}
	/**
	 * This private class within {@link Server} handles the incoming connection of 
	 * a new client to the server. This class accepts the client and records it's input and 
	 * output stream for later communication. After this, the {@link ClientHandler} class is 
	 * instantiated and executed under a new {@link Thread}.
	 * @author TannerKoch
	 * @author JamesDeveuve
	 *
	 */
	private class ClientAccepter implements Runnable{
		/**
		 * This is the overridden method from the {@link Thread} class, it accepts and executes
		 * a new clients connection to the server.
		 */
		public void run() {
			try{
				while(true){
					// accept a new client, get output & input streams
					Socket s = socket.accept();
					ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(s.getInputStream());
					// read the client's name
					clientName = (String)input.readObject();
					// map client name to output stream
					outputs.put(clientName, output);
					
					// spawn a thread to handle communication with this client
					new Thread(new ClientHandler(input, output)).start();
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * This is the constructor of the {@link Server} class that creates the 
	 * ServerSocket to be connected to by clients. Also prints out to the console that
	 * the server has successfully been opened once the constructor has been instantiated.
	 * @author TannerKoch
	 * @author JamesDeveuve
	 */
	public Server(){
		this.outputs = new HashMap<String, ObjectOutputStream>(); // setup the map
		this.shapes = new ArrayList<Shape>();
		
		try{
			// start a new server on port 9001
			socket = new ServerSocket(9001);
			System.out.println("NetPaint server started on port 9001");
			
			// spawn a client accepter thread
			new Thread(new ClientAccepter()).start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This method adds the shape passed to it to a list of shapes drawn.
	 * After the list is updated, the updateClients() method is called.
	 * @param shape The shape to be added to the List<Shape> 
	 */
	public void addShape(Shape shape){
		shapes.add(shape);
		
		updateClients();
	}
	/**
	 * This method executes the connect message to all clients on the server.
	 * The message is that the newest client has connected.
	 */
	public void connectCommand() {
		UpdateTextCommand command = new UpdateTextCommand(clientName);
		try {
			for (ObjectOutputStream output: outputs.values())
				output.writeObject(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method writes the update objects command to all the clients. This
	 * in turn updates the most recently drawn shape to the rest of the clients.
	 */
	public void updateClients() {
		UpdateClientCommand update = new UpdateClientCommand(shapes);
		
		try{
			for (ObjectOutputStream out : outputs.values()) {
				if (out != thisStream ) {
				out.writeObject(update);
				}
				System.out.println(thisStream);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This main method instantiates the Server
	 * @param args No arguments are passed to the command line.
	 */
	public static void main(String[] args){
		new Server();
	}
	/**
	 * This method handles the execution of a disconnect from the server by a client.
	 * @param clientName The client's name who just disconnected as a String.
	 */
	public void disconnect(String clientName) {
		try{
			outputs.get(clientName).close(); // close output stream
			outputs.remove(clientName); // remove from map
			
			// add notification message
			//addMessage(clientName + " disconnected");2
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
