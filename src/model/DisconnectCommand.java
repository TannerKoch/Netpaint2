package model;

import controller.Client;
import controller.Server;

/**
 * This class is called whenever a {@link Client} disconnects from the Server.
 * Extends Command<T> so that this command among others can be executed universally.
 * @author TannerKoch
 * @author JamesDeveuve
 *
 */
public class DisconnectCommand extends Command<Server>{
	private static final long serialVersionUID = -8557424886231888586L;
	private String clientName; // client who is disconnecting
	/**
	 * This constructor initializes a string variable to the string passed by the constructor.
	 * This string is broadcast to the clients so they know when another client has disconnected.
	 * @param name The name of the client disconnected.
	 */
	public DisconnectCommand(String name){
		clientName = name;
	}
	/**
	 * The method inherited from Command<T>. Called when all the clients need to be updated
	 * with the message that a client disconnected.
	 */
	@Override
	public void execute(Server executeOn) {
		// disconnect client
		executeOn.disconnect(clientName);
	}

}