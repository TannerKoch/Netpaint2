package model;

import controller.Client;
/**
 * This class is called whenever a {@link Client} connects to the server
 * Extends Command<T> so that this command among others can be executed universally.
 * @author TannerKoch
 * @author JamesDeveuve
 *
 */
public class UpdateTextCommand extends Command<Client>{
	String clientName;
	/**
	 * This constructor initializes the variable containing the clients name
	 */
	public UpdateTextCommand(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * The method inherited from Command<T>. Called when all the clients need to be updated
	 * with the message that a new client has connected.
	 */
	public void execute(Client executeOn) {
		executeOn.setText(clientName+" has connected");
		
	}

}
