package model;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
/**
 * This class is a subclass of {@link Shape}. It is used to
 * draw lines to the Netpaint window whenever a Rectangle 
 * object is called.
 * @author TannerKoch
 * @author JamesDeveuve
 */
public class Rectangle extends Shape implements Serializable {
	/**
	 * This constructor creates a Rectangle to be drawn to the
	 * Netpaint screen. Shape's constructor is called here.
	 * @param ox The old X value.
	 * @param oy The old Y value.
	 * @param nx The new X value.
	 * @param ny The new Y value.
	 * @author TannerKoch
	 * @author JamesDeveuve
	 *
	 */
	public Rectangle(int ox, int oy, int nx, int ny){
		super(ox, oy, nx, ny);
	}
	/**
	 * This class creates the Rectangle object from the values passed into it.
	 * @return Object Returns an object that is a rectangle.
	 * @param ox The old X value.
	 * @param oy The old Y value.
	 * @param nx The new X value.
	 * @param ny The new Y value.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	@Override
	public Object getShape(int ox, int oy, int nx, int ny) {
		int h = getHeight(oy, ny);
		int w = getWidth(ox, nx);
		Rectangle2D.Double rectangle = new Rectangle2D.Double(ox, oy, w, h);
		return rectangle;
	}
	/**
	 * This method returns the difference of Y values,
	 * producing the height of the current Rectangle.
	 * @param old The original Y value.
	 * @param n The most recent Y value.
	 * @return The height of the current Rectangle.
	 */
	public int getHeight(int old, int n){
		int height = 0;
		if(old >= n){
			height = old - n;
		}
		if(n > old){
			height = n - old;
		}
		return height;
		
	}
	/**
	 * This method returns the difference of X values,
	 * producing the width of the current Rectangle.
	 * @param old The original Y value.
	 * @param n The most recent Y value.
	 * @return The width of the current Rectangle.
	 */
	public int getWidth(int old, int n){
		int width = 0;
		if(old >= n){
			width = old - n;
		}
		if(n > old){
			width = n - old;
		}
		return width;
		
	}
	

}
