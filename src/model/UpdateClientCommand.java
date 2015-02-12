package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import controller.Client;
/**
 * This class is called whenever a {@link Client} draws a new object to the screen.
 * Extends Command<T> so that this command among others can be executed universally.
 * @author TannerKoch
 * @author JamesDeveuve
 *
 */
public class UpdateClientCommand extends Command<Client> {
	private static final long serialVersionUID = 3972663424844946574L;
	private List<Shape> shapes;
	/**
	 * This constructor initializes the list of shapes with the list passed to the 
	 * constructor.
	 * @param shapes The list of shapes passed to the constructor.
	 */
	public UpdateClientCommand(List<Shape> shapes) {
		this.shapes = new LinkedList<Shape>(shapes);
	}
	/**
	 * The method inherited from Command<T>. Called when all the clients need to be updated
	 * with the most recent version of the shape list.
	 */
	@Override
	public void execute(Client client) {
		client.update(this.shapes);
		
	}
}
