import java.awt.Frame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import button.*;


public class StickTogether extends Frame implements MouseListener {
	
	/**
	 * To hold our Sticks
	 */
	private Comparable<Stick>[] sticks;
	
	/**
	 * Holds number of sticks in array
	 */
	private int numSticks;
	
	/**
	 * Holds index of selected Stick, if there is one
	 */
	private int selected = -1;
	
	/**
	 * For the sake of searching
	 */
	private Stick randomStick;
	
	/**
	 * To distinguish between the stick that is selected and the rest
	 */
	private Color normal, highlighted;
	
	/**
	 * Our buttons
	 */
	private AButton[] buttons = new AButton[8];
	
	/**
	 * Space between buttons and sticks
	 */
	private static final int SPACE = 10;
	
	/**
	 * So we can close our window
	 */
	private UneFenetre window;
	
	
	public StickTogether(){
		
		final int 	BUTTON_WIDTH = 100,
		BUTTON_HEIGHT = 40,//to define the size of our buttons
		LABEL_HEIGHT = 50;// to make sure we don't try to paint over the title bar

		setTitle("Stick With It");//name, location and size of our frame
		setLocation(150,150);	

		int windowWidth = 900;
		int windowHeight = 800;
		setSize(windowWidth,windowHeight);

		

		/*
		 * Set up our eight buttons in a row
		 */
		buttons[0] = new TriangleButton("Add", Color.red, SPACE, LABEL_HEIGHT + SPACE, 
				BUTTON_WIDTH, BUTTON_HEIGHT);


		buttons[1] = new OvalButton("Smallest", Color.green, SPACE * 2 + BUTTON_WIDTH, 
				LABEL_HEIGHT + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);



		buttons[2] = new RegularButton("Largest", Color.cyan, SPACE * 3 + BUTTON_WIDTH * 2, 
				LABEL_HEIGHT + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);

		buttons[3] = new TransparentButton("Remove", Color.blue, SPACE * 4 + BUTTON_WIDTH * 3, 
				LABEL_HEIGHT + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		buttons[4] = new TriangleButton("Sort", Color.yellow, SPACE * 5 + BUTTON_WIDTH * 4,
				LABEL_HEIGHT + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		buttons[5] = new TransparentButton("Clear", Color.pink, SPACE * 6 + BUTTON_WIDTH * 5,
				LABEL_HEIGHT + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		buttons[6] = new OvalButton("Random", Color.orange, SPACE * 7 + BUTTON_WIDTH * 6,
				LABEL_HEIGHT + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		buttons[7] = new RegularButton("Search", Color.magenta, SPACE * 8 + BUTTON_WIDTH * 7,
				LABEL_HEIGHT + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);


		window = new UneFenetre();//to allow for window closing
		addWindowListener(window);
		
		addMouseListener(this);//to allow buttons to work
		
		highlighted = Color.red;
		normal = highlighted.darker();
		
		sticks = new Stick[25];//maximum number of Sticks
		
		numSticks = 0;//we don't have any yet!
		
		setVisible(true);
		
	}
	
	/**
	 * paints the buttons and the sticks
	 */
	public void paint(Graphics pane){
		
		for(AButton b : buttons){
			b.paint(pane);
		}
		
		for(int i = 0; i < numSticks; i++){//paint all of our sticks
			((Stick)sticks[i]).paint(pane);
		}
		
		if(randomStick != null)
			randomStick.paint(pane);
	}
	

	/**
	 * adds one stick, up to limit
	 */
	private void add(){
		if(numSticks != sticks.length){
			final int WINDOW_HEIGHT = 800,
			BUTTON_PANEL_HEIGHT = 100;
			int x, y = 800-SPACE, width, height;//the bottom of the stick doesn't change

			if(numSticks == 0){//start on the left
				x = SPACE*2+30;	//enough room for random stick		
			}else{ //or start after the last stick
				x = ((Stick)sticks[numSticks - 1]).getX() + 
				((Stick)sticks[numSticks - 1]).getWidth() + SPACE;
			}

			width = (int)(Math.random() * 21 + SPACE);//Between 10 and 30
			height = (int)(Math.random() * 
					(WINDOW_HEIGHT - BUTTON_PANEL_HEIGHT - SPACE*3) + SPACE);//10-680
			sticks[numSticks] = new Stick();
			((Stick)sticks[numSticks]).setLocation(x, y);
			((Stick)sticks[numSticks]).setSize(width, height);
			((Stick)sticks[numSticks++]).setColor(normal);
		}
	}
	
	/**
	 * Selects and highlights smallest stick
	 */
	private void findSmallest(){
		if(selected > -1)
			((Stick)sticks[selected]).setColor(normal);//make sure none are highlighted
		int smallest = 0;	//initialize smallest to first Stick in array
		for(int i = 1; i<numSticks; i++){	//find smallest
			if(sticks[smallest].compareTo((Stick)sticks[i]) > 0)
				smallest = i;
		}
		if(numSticks>0){
			selected = smallest;	//set selected so it can be removed
			((Stick)sticks[selected]).setColor(highlighted); //and highlight it
			repaint();
		}		
		
	}
	
	/**
	 * Selects and highlights largest stick
	 */
	private void findLargest(){
		if(selected > -1)
			((Stick)sticks[selected]).setColor(normal);//make sure none are highlighted
		int largest = 0;	//initialize largest to first Stick in array
		for(int i = 1; i<numSticks; i++){	//find largest
			if(sticks[largest].compareTo((Stick)sticks[i]) < 0)
				largest = i;
		}
		if(numSticks>0){
			selected = largest;	//set selected so it can be removed
			((Stick)sticks[selected]).setColor(highlighted); //and highlight it
			repaint();
		}		
	}
	
	/**
	 * Removes the selected stick if there is one.
	 */
	private void remove(){
		if(selected > -1){//doesn't do anything if there isn't any selected
			int x = ((Stick)sticks[selected]).getX();//get coordinates to
			int y = ((Stick)sticks[selected]).getY();//reset locations of sticks
			
			for(int i = selected + 1; i < numSticks; i++){
				sticks[i-1] = sticks[i];//move it in array
				((Stick)sticks[i-1]).setLocation(x, y);//and reset its location
				x += ((Stick)sticks[i-1]).getWidth() + SPACE;
			}
			
			sticks[--numSticks] = null;//no reference to last stick
			selected = -1;//none are selected
		}
	}
	
	/**
	 * Resets collection to 0 sticks
	 */
	private void clear(){
		numSticks = 0;
		selected = -1;
		randomStick = null;
	}
	
	/**
	 * Generates a stick that has a 50% chance of having the same
	 * height and width as one of the sticks in the collection.
	 * Deselects any previously selected stick.
	 */
	private void generateRandom(){
		if(selected > -1){
			((Stick)sticks[selected]).setColor(normal);
			selected = -1;
		}
		boolean inCollection = false;
		double random = Math.random();
		if(random < .5)
			inCollection = true;
		final int WINDOW_HEIGHT = 800,
		BUTTON_PANEL_HEIGHT = 100;
		int x = SPACE, y = 800-SPACE;//the bottom of the stick doesn't change
		int width, height;
		if(inCollection){
			int chosen = (int)(Math.random()*numSticks);
			Stick target = (Stick)(sticks[chosen]);
			width = target.getWidth();
			height = target.getHeight();
		}else{
			width = (int)(Math.random() * 21 + SPACE);//Between 10 and 30
			height = (int)(Math.random() * 
					(WINDOW_HEIGHT - BUTTON_PANEL_HEIGHT - SPACE*3) + SPACE);//10-680
		}
		randomStick = new Stick();
		randomStick.setLocation(x, y);
		randomStick.setSize(width, height);
		randomStick.setColor(highlighted);
	}
	
	/**
	 * Sorts the collection
	 */
	private void sort(){
		insertionSort();
	}
	
	/**
	 * Sorts the collection using bubble sort.
	 */
	private void bubbleSort(){
		
	}
	
	/**
	 * Sorts the collection using selection sort.
	 */
	private void selectionSort(){
		
	}
	
	/**
	 * Sorts the collection using insertion sort.
	 */
	private void insertionSort(){
		for(int i=1; i<numSticks;i++)
		{
			for (int j=i; j> 0; j--)
			{
				if (sticks[j].compareTo((Stick)sticks[j-1]) == -1)
				{
					Stick tempStick = (Stick)sticks[j];
					sticks[j] = sticks[j-1];
					sticks[j-1] = tempStick;
				}	
			}
		}
		
	}
	
	/**
	 * If randomStick is the same size as one in the collection, will
	 * set the one in the collection as selected
	 * @return true if it's in the collection
	 */
	private boolean search(){
		return linearSearch();
	}
	
	/**
	 * If randomStick is the same size as one in the collection, will
	 * set the one in the collection as selected and return true.
	 */
	private boolean linearSearch(){
		return false;//stub until method is implemented
	}
	
	/**
	 * If randomStick is the same size as one in the collection, will
	 * set the one in the collection as selected
	 * @return true if it's in the collection
	 */
	private boolean binarySearch(){
		return false;//stub until method is implemented
	}
	
	/**
	 * Assigns sticks their location based on their order in the array
	 */
	private void positionSticks(){
		int x = SPACE*2 + 30;	//leftmost stick
		int y = 800 - SPACE;	//bottom position never changes
		for(int i = 0; i<numSticks; i++){
			((Stick)sticks[i]).setLocation(x, y);
			x+=SPACE + ((Stick)sticks[i]).getWidth(); //move over one stick
		}
	}
	
	

	/**
	 * You don't have to change anything here, just implement/adjust the methods
	 * that are invoked.
	 */
	public void mouseClicked(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		
		if(buttons[0].isInside(x, y))
			add();
		else if(buttons[1].isInside(x, y)){
			findSmallest();
		}
			
		else if(buttons[2].isInside(x, y)){
			findLargest();
		}
			
		else if(buttons[3].isInside(x, y)){
			remove();
		}
		
		else if(buttons[4].isInside(x,y)){
			sort();
			positionSticks();//update their positions to reflect sorted order
		}
		
		else if(buttons[5].isInside(x, y)){
			clear();
		}
		
		else if(buttons[6].isInside(x, y)){
			if(numSticks > 0)
				generateRandom();
		}

		else if(buttons[7].isInside(x, y)){
			if(randomStick!=null){
				search();
			}
		}
			
		repaint();

	}

	/**
	 * This just flips any button that is pressed.
	 */
	public void mousePressed(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		
		for(AButton b : buttons){
			if(b.isInside(x,y)){
				b.flip();
				repaint();
			}
		}
		

	}

	/**
	 * This flips it back.
	 */
	public void mouseReleased(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		
		for(AButton b : buttons){
			if(b.isInside(x,y)){
				b.flip();
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args){
		new StickTogether();
	}

}
