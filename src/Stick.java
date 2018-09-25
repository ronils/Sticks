/*
	File:	Stick.java

	Definition (and visualiztion) of a Stick.
*/

import java.awt.*;						//	AWT = "Abstract Window Toolkit"

public class Stick implements Comparable<Stick>
{
	private int x, y;					//	Bottom left corner of the stick
	private int width, height;					//	Size of the stick
	
	private Color color;

	//
	//	The default constructor
	//
	public Stick()
	{
	}

	//
	//	To set (or reset) the location of the stick.
	//
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	//
	//	To set (or reset) the size of the stick.
	//
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	/**
	 * To change the color of the stick
	 * @param someColor
	 */
	public void setColor(Color someColor){
		color = someColor;
	}

	//
	//	To get the size of the stick.
	//
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * 
	 * @return height of stick
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * 
	 * @return x coordinate of lower left corner of stick
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * 
	 * @return y coordinate of lower left corner of stick
	 */
	public int getY(){
		return y;
	}

	//
	//	The only "graphical" method of the class is the paint method.
	//	The method takes care of painting the stick ... just right.
	//
	public void paint(Graphics pane)
	{

		pane.setColor(color);		//	Note it draws from the top though
		pane.fillRect(x, y - height, width , height);//our y is at the bottom
		
		pane.setColor(Color.black);		//	Black for the border of the stick
		pane.drawRect(x, y - height, width , height);
	}
	
	/**
	 * Compares sticks on basis of area.
	 */
	public int compareTo(Stick stick2) {
		int result = 0;
		int area = width * height;
		int stick2Area = stick2.width * stick2.height;
		if(area < stick2Area)
			result = -1;
		else if(area > stick2Area)
			result = 1;
		return result;
	}
		
		
}	// end Stick