package model;

import controller.Client;
import controller.Server;
/**
 * This class is called whenever a {@link Client} draws a new object to the screen.
 * Extends Command<T> so that this command among others can be executed universally.
 * @author TannerKoch
 * @author JamesDeveuve
 *
 */
public class AddObjectCommand extends Command<Server> {
	private static final long serialVersionUID = 5225331992455441015L;
	private Shape theObject;
	/**
	 * This constructor initializes the shape instance variable with the shape passed to the 
	 * constructor.
	 * @param o The shape to be added to the server.
	 */
	public AddObjectCommand(Shape o){
		this.theObject = o;
		//System.out.println(theObject.getColor());
	}
	/**
	 * The method inherited from Command<T>. Called when the server needs to be updated
	 * with the most recent shape.
	 */
	@Override
	public void execute(Server server) {
		server.addShape(theObject);
		
	}
}
