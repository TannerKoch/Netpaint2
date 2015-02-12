package model;

import java.awt.Color;
import java.io.Serializable;

/**
 * This abstract class is the parent class to all shapes drawn in the 
 * Netpaint program. Provides the base methods needed to instantiate 
 * the various shape objects.
 * 
 * @author TannerKoch
 * @author JamesDeveuve
 *
 */
public abstract class Shape implements Serializable {
	private int oldX, oldY, newX, newY;
	private Color color;
	/**
	 * This constructor creates a Shape object, this
	 * object is never drawn but is meant to hold the 
	 * X and Y values of the objects to be drawn.
	 * @param ox The old X value.
	 * @param oy The old Y value.
	 * @param nx The new X value.
	 * @param ny The new Y value.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	public Shape(int ox, int oy, int nx, int ny) {
		this.oldX = ox;
		this.oldY = oy;
		this.newX = nx;
		this.newY = ny;
	}
	/**
	 * This method is used to draw the various shapes that
	 * extend this base class.
	 * @param ox The old X value.
	 * @param oy The old Y value.
	 * @param nx The new X value.
	 * @param ny The new Y value.
	 * @return Object Returns the object to be drawn.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	abstract public Object getShape(int ox, int oy, int nx, int ny);
	/**
	 * This method is used to call the last recorded X value.
	 * 
	 * @return oldX The old X value.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	public int getOldX() {
		return oldX;
	}
	/**
	 * This method is used to set the last recorded X value.
	 * 
	 * @param oldX The old X value.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	public void setOldX(int oldX) {
		this.oldX = oldX;
	}
	/**
	 * This method is used to call the last recorded Y value.
	 * 
	 * @return oldY The old Y value.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	public int getOldY() {
		return oldY;
	}
	/**
	 * This method is used to set the last recorded Y value.
	 * 
	 * @param oldY The old Y value.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	public void setOldY(int oldY) {
		this.oldY = oldY;
	}
	/**
	 * This method is used to call the latest recorded X value.
	 * 
	 * @return newX The new X value.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	public int getNewX() {
		return newX;
	}
	/**
	 * This method is used to set the latest recorded X value.
	 * 
	 * @param newX The new X value.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	public void setNewX(int newX) {
		this.newX = newX;
	}
	/**
	 * This method is used to call the latest recorded Y value.
	 * 
	 * @return newY The new Y value.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	public int getNewY() {
		return newY;
	}
	/**
	 * This method is used to set the latest recorded Y value.
	 * 
	 * @param newY The new Y value.
	 * @author JamesDeveuve
	 * @author TannerKoch
	 */
	public void setNewY(int newY) {
		this.newY = newY;
	}
	public void setColor(Color c) {
	this.color = c;	
	}
	public Color getColor() {
		return color;
	}
}
