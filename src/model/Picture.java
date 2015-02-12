package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;


public class Picture extends Shape implements Serializable{
	private String fileName;
	File theFile;
	private int h;
	private int w;
	
	public Picture(int ox, int oy, int nx, int ny){
		super(ox, oy, nx, ny);
		this.fileName = "doge.jpeg";
		theFile = new File(fileName);
		this.h = getHeight(oy, ny);
		this.w = getWidth(ox, nx);
	}
	@Override
	public Object getShape(int ox, int oy, int nx, int ny) {
		return null;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
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
	 * producing the width of the current oval.
	 * @param old The original Y value.
	 * @param n The most recent Y value.
	 * @return The width of the current oval.
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
