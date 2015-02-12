package model;
/**
 * This class is a subclass of {@link Shape}. It is used to
 * draw lines to the Netpaint window whenever a line object is
 * called.
 * @author TannerKoch
 * @author JamesDeveuve
 */
import java.awt.geom.Line2D;
import java.io.Serializable;

	public class Line extends Shape implements Serializable {
		/**
		 * This constructor creates a line to be drawn to the
		 * Netpaint screen. Shape's constructor is called here.
		 * @param ox The old X value.
		 * @param oy The old Y value.
		 * @param nx The new X value.
		 * @param ny The new Y value.
		 * @author TannerKoch
		 * @author JamesDeveuve
		 *
		 */
	public Line(int ox, int oy, int nx, int ny){
		super(ox, oy, nx, ny);
	}
	/**
	 * This class creates the line object from the values passed into it.
	 * @return Object Returns an object that is a line.
	 * @param ox The old X value.
	 * @param oy The old Y value.
	 * @param nx The new X value.
	 * @param ny The new Y value.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	@Override
	public Object getShape(int ox, int oy, int nx, int ny) {
		Line2D.Double line = new Line2D.Double(ox, oy, nx, ny);
		return line ;
	}

}
