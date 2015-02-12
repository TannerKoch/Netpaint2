
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Client;
import model.AddObjectCommand;
import model.Line;
import model.Oval;
import model.Picture;
import model.Rectangle;
import model.Shape;

/**
 * Constructs the visual aspect of the NetPaint program. Handles the various JPanels 
 * and associates the components they hold to their necessary action listeners.
 * Also handles the drawing of the elements by calling the determined {@link Line},
 * {@link Oval}, {@link Rectangle}, or {@link Picture}.
 * 
 * 
 * @author Tanner Koch
 * @author James Deveuve
 * 
 */

public class Window extends JFrame {
	DrawPanel drawPanel;
	JPanel centerPanel;
	JPanel buttonPanel;
	JPanel bottomPanel;
	JScrollPane drawPane;
	JRadioButton line;
	JRadioButton rectangle;
	JRadioButton oval;
	JRadioButton image;
	ButtonGroup group = new ButtonGroup();
	JTabbedPane tabs;
	JPanel swatches;
	JPanel HSV;
	JPanel HSL;
	JPanel RGB;
	JPanel CMYK;
	static JTextArea text;
	JLabel preview;
	JColorChooser jcc;
	int shape = 0;
	Color thisColor;
    private ArrayList<Shape> shapes;
    private ObjectOutputStream output; // output stream to server
    public Shape theShape;
    static Graphics2D g2;
/**
 * This constructor's only purpose is to create and layout the components in the gui, 
 * it accomplishes this by calling {@link layoutTheGui()}. There are no parameters 
 * passed to this constructor.
 */
public Window(ObjectOutputStream output) {
	this.output = output;
	layoutTheGui();
}
/**
 * This method is only called within the constructor of {@link Window}. It lays out 
 * the visual components to be used by the NetPaint program. 
 */
private void layoutTheGui() {
	text = new JTextArea();
	text.setEditable(false);
	text.setSize(new Dimension(200, 80));
	tabs = new JTabbedPane();
	preview = new JLabel("Preview");
	swatches = new JPanel();
	HSV = new JPanel();
	HSL = new JPanel();
	RGB = new JPanel();
	CMYK = new JPanel();
	tabs.add(swatches, "Swatches");
	tabs.add(HSV, "HSV");
	tabs.add(HSL, "HSL");
	tabs.add(RGB, "RGB");
	tabs.add(CMYK, "CMYK");
	this.setTitle("Netpaint");
	this.setSize(800, 800);
	this.setLayout(new BorderLayout());
	line = new JRadioButton("Line");
	line.addActionListener(new ButtonListener());
	line.setSelected(true);
	rectangle = new JRadioButton("Rectangle");
	rectangle.addActionListener(new ButtonListener());
	rectangle.setSelected(false);
	oval = new JRadioButton("Oval");
	oval.addActionListener(new ButtonListener());
	oval.setSelected(false);
	image = new JRadioButton("Image");
	image.addActionListener(new ButtonListener());
	image.setSelected(false);
	group.add(line);
	group.add(rectangle);
	group.add(oval);
	group.add(image);
	drawPanel = new DrawPanel();
	drawPanel.setBackground(Color.WHITE);
	drawPane = new JScrollPane(drawPanel);
	drawPane.setPreferredSize(new Dimension(800, 280));
	drawPane.setBorder(BorderFactory.createLineBorder(Color.black, 3));
	centerPanel = new JPanel();
	centerPanel.setLayout(new BorderLayout());
	buttonPanel = new JPanel();
	buttonPanel.setLayout(new FlowLayout());
	buttonPanel.add(line);
	buttonPanel.add(rectangle);
	buttonPanel.add(oval);
	buttonPanel.add(image);
	centerPanel.add(text, BorderLayout.NORTH);
	centerPanel.add(buttonPanel, BorderLayout.SOUTH);
	
	jcc = new JColorChooser(Color.GREEN);
	jcc.getSelectionModel().addChangeListener(new ColorListener());
	jcc.setPreferredSize(new Dimension(800, 350));
	bottomPanel = new JPanel();
	bottomPanel.setPreferredSize(new Dimension(800, 75));
	bottomPanel.setLayout(new BorderLayout());
	this.add(drawPane, BorderLayout.NORTH);
	this.add(centerPanel, BorderLayout.CENTER);
	getContentPane().add(jcc, BorderLayout.SOUTH);
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
}
/**
 * This class listens to the radio buttons within the Netpaint frame.
 * It listens to the group of radio buttons and determines the shape that 
 * should be drawn based upon which button is selected. This class implements
 * ActionListener so it can be registered as a listener to the correct components.
 * @see ActionListener
 * 
 * 
 * @author JamesDeveuve
 * @author TannerKoch
 *
 */
private class ButtonListener implements ActionListener {

	/**
	 * This method takes the Action Event and determines the 
	 * source, in order to correctly assign an instance variable
	 * that will determine what {@link Shape} will be drawn.
	 * @param arg0 The ActionEvent from a clicked radio button
	 * @return void
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(line)) {
			shape = 0;
		} else if (arg0.getSource().equals(rectangle)) {
			shape = 1;
		} else if (arg0.getSource().equals(oval)) {
			shape = 2;
		} else if (arg0.getSource().equals(image)) {
			shape = 3;
		}
	}
	
}
public static String setText(String t) {
text.setText(t);
return t;
}

/**
 * This method returns the int value of the shape to be drawn. 
 * 0 Corresponds to a line, 1 is a rectangle, 2 produces an oval,
 * and 3 
 * @return  The int value containing which shape should be drawn.
 */
public int getTheShape(){
	
	return shape;
}
/**
 * This class listens to a {@link JColorChooser} object so that
 * an instance variable can be assigned whenever a new color is
 * chosen.
 * @author TannerKoch
 * @author JamesDeveuve
 *
 */
private class ColorListener implements ChangeListener {
	/**
	 * This method is called whenever a new color is 
	 * selected from the {@link JColorChooser}. It updates 
	 * an instance variable with the currently selected color.
	 */
	public void stateChanged(ChangeEvent arg0) {
		thisColor = jcc.getColor();
	}
	
}
/**
 * This class is used as a drawing panel for the Netpaint window.
 * @author TannerKoch
 * @author JamesDeveuve
 *
 */
private class DrawPanel extends JPanel {
	
    // To get etching to work, and turn it on and off, 
    // change mouseClicked and mouseMoved
    private int oldX, oldY, newX, newY;
    Shape oval = new Oval(oldX, oldY, newX, newY);
    Shape rectangle = new Rectangle(oldX, oldY, newX, newY);
    Shape line = new Line(oldX, oldY, newX, newY);
    BufferedImage pic;
    Picture p;
    
	
    private boolean isDrawing;
    private int lineCount;
    private int ovalCount;
    private int rectangleCount;
    
    
    private ArrayList<Integer> ImageoldxList;
    private ArrayList<Integer> ImageoldyList;
    private ArrayList<Integer> ImagenewxList;
    private ArrayList<Integer> ImagenewyList;
    

    public DrawPanel() {
      // The Panel listens to itself
      isDrawing = false;
      MouseListener listener = new ListenToMouse();
      MouseMotionListener motionListener = new ListenToMouse();

      try {
          pic = ImageIO.read(new File("doge.jpeg"));
      } catch (IOException e) {
    	  System.out.println("noneeeeee");
      }
      
      this.addMouseMotionListener(motionListener);
      this.addMouseListener(listener);
      lineCount = 0;
      ovalCount = 0;
      rectangleCount = 0;
      
      shapes = new ArrayList<Shape>();
      
      ImageoldxList = new ArrayList<Integer>();
      ImageoldyList = new ArrayList<Integer>();
      ImagenewxList = new ArrayList<Integer>();
      ImagenewyList = new ArrayList<Integer>();
    
    }

    private class ListenToMouse implements MouseMotionListener, MouseListener {
    	
 
      public void mouseClicked(MouseEvent evt) {
    	if(isDrawing == true){
    		newX = evt.getX();
            newY = evt.getY();
    	}
    	if(isDrawing == false){
    		oldX = evt.getX();
    		oldY = evt.getY();
    	}

       // System.out.println(newX + " Clicked " + newY);
        if (isDrawing) {

            // You can ask a JPanel for its graphics context
            g2 = (Graphics2D) getGraphics();
            int thisShape = getTheShape();
            g2.setColor(thisColor);
            if(thisShape == 0) {
            	Line line = new Line(oldX, oldY, newX, newY);
            	line.setColor(thisColor);
            	shapes.add(line);
            	try {
					output.writeObject(new AddObjectCommand(line));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	 g2.draw((java.awt.Shape) line.getShape(oldX, oldY, newX, newY));
					} else if (thisShape == 1) {
						Rectangle rectangle = new Rectangle(oldX, oldY,newX, newY);
						rectangle.setColor(thisColor);
						shapes.add(rectangle);
						try {
							output.writeObject(new AddObjectCommand(rectangle));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						g2.draw((java.awt.Shape) rectangle.getShape(oldX, oldY,
								newX, newY));
						g2.fill((java.awt.Shape) rectangle.getShape(oldX, oldY,
								newX, newY));
					} else if (thisShape == 2) {
						Oval oval = new Oval(oldX, oldY,newX, newY);
						oval.setColor(thisColor);
						
						shapes.add(oval);
						try {
							output.writeObject(new AddObjectCommand(oval));

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						g2.draw((java.awt.Shape) oval.getShape(oldX, oldY,
								newX, newY));
						g2.fill((java.awt.Shape) oval.getShape(oldX, oldY,
								newX, newY));
						
					} else if (thisShape == 3) {
						p = new Picture(oldX,oldY,newX,newY);
						g2.drawImage(pic, oldX, oldY, p.getW(),p.getH(), null);
						ImageoldxList.add(oldX);
						ImageoldyList.add(oldY);
						ImagenewxList.add(newX);
						ImagenewyList.add(newY);
						shapes.add(p);
		            	 lineCount++; 
		            	 try {
		 					output.writeObject(new AddObjectCommand(p));
		 				} catch (IOException e) {
		 					// TODO Auto-generated catch block
		 					e.printStackTrace();
		 				}
		            
					}
					g2.setBackground(Color.WHITE);

				}
        isDrawing = !isDrawing;
      }

      public void mouseMoved(MouseEvent evt) {
    	  if(isDrawing == true){
    		  Graphics2D g2 = (Graphics2D) getGraphics();
    		  for (int i = 0; i < lineCount; i++) {
					p = new Picture(ImageoldxList.get(i),ImageoldyList.get(i),ImagenewxList.get(i),ImagenewyList.get(i));
					//g2.setColor(shapes.get(i).getColor());
					g2.drawImage(pic, p.getOldX(), p.getOldY(), p.getW(),p.getH(), null);
				}
    		  for (Shape s: shapes) {
    		      g2.setColor(s.getColor());
    		      if (s instanceof Picture) {
    		    	  Picture p = new Picture(s.getOldX(), s.getOldY(), s.getNewX(), s.getNewY());
    				g2.drawImage(pic, s.getOldX(), s.getOldY(), p.getW(),p.getH(), null);
    		      } else if (!(s instanceof Picture)&& (s instanceof Line)) {
    		      g2.draw((java.awt.Shape) s.getShape(s.getOldX(), 
    		      s.getOldY(), s.getNewX(), s.getNewY()));
    		      }
    		      if (!(s instanceof Line) && !(s instanceof Picture)) {
    		    	 g2.fill((java.awt.Shape) s.getShape(s.getOldX(), 
    		         s.getOldY(), s.getNewX(), s.getNewY()));
    		      }
    		  }
               int thisShape = getTheShape();
               g2.setColor(Color.WHITE);
               
          	 if(thisShape == 0) {
              	 g2.draw((java.awt.Shape) line.getShape(oldX, oldY, newX, newY));
              }else if (thisShape == 1) {
              	 g2.draw((java.awt.Shape) rectangle.getShape(oldX, oldY, newX, newY));
              	 
              }else if(thisShape == 2){
              	 g2.draw((java.awt.Shape) oval.getShape(oldX, oldY, newX, newY));
              } else if (thisShape == 3) {
            	  p = new Picture(oldX,oldY,newX,newY);
					g2.drawImage(pic, oldX, oldY, p.getW(),p.getH(), null);
              }
              
          }
        newX = evt.getX();
        newY = evt.getY();
        if(isDrawing == true){
        	 Graphics2D g2 = (Graphics2D) getGraphics();
             int thisShape = getTheShape();
             g2.setColor(thisColor);
        	 if(thisShape == 0) {
            	 g2.draw((java.awt.Shape) line.getShape(oldX, oldY, newX, newY));
            }else if (thisShape == 1) {
            	 g2.draw((java.awt.Shape) rectangle.getShape(oldX, oldY, newX, newY));
            }else if(thisShape == 2){
            	 g2.draw((java.awt.Shape) oval.getShape(oldX, oldY, newX, newY));
            } else if (thisShape == 3) {
            }
            
        }
  
      }
      public void mousePressed(MouseEvent evt) {
        newX = evt.getX();
        newY = evt.getY();
        //System.out.println(newX + " Pressed " + newY);
      }

      public void mouseEntered(MouseEvent evt) {
        newX = evt.getX();
        newY = evt.getY();
        //System.out.println(newX + " Entered " + newY);
      }

      public void mouseReleased(MouseEvent evt) {
        newX = evt.getX();
        newY = evt.getY();
        //System.out.println(newX + " released " + newY);
      }

      public void mouseExited(MouseEvent evt) {
        newX = evt.getX();
        newY = evt.getY();
        //System.out.println(newX + " Exited " + newY);
      }

      public void mouseDragged(MouseEvent evt) {
        newX = evt.getX();
        newY = evt.getY();
 
      }
    } 
   
}

public void update(List<Shape> shapes) {
	repaint();
	BufferedImage pic = null;
	Graphics2D g2 = (Graphics2D) getGraphics();
	try {
        pic = ImageIO.read(new File("doge.jpeg"));
    } catch (IOException e) {
  	  System.out.println("noneeeeee");
    }
	
	for (Shape s: shapes) {
		g2.setColor(s.getColor());
		if (!(s instanceof Line) && !(s instanceof Picture)) {
	//		g2.draw((java.awt.Shape) s.getShape(s.getOldX()+4, 
	//				s.getOldY()+20, s.getNewX()+4, s.getNewY()+20));
			g2.fill((java.awt.Shape)s.getShape(s.getOldX()+3, s.getOldY()+25,
					s.getNewX()+3, s.getNewY()+25));
		} else if (!(s instanceof Picture)) {
			g2.draw((java.awt.Shape) s.getShape(s.getOldX()+3, 
					s.getOldY()+25, s.getNewX()+3, s.getNewY()+25));			
		} else {
			Picture p = new Picture(s.getOldX(), s.getOldY(), s.getNewX()+3, s.getNewY()+25);
			g2.drawImage(pic, s.getOldX()+3, s.getOldY()+25, p.getW(),p.getH(), null);
			
			
		}
	}
	
}
public void setList(ArrayList<Shape> s){
	this.shapes = s;
}
public ArrayList<Shape> getList() {
	return shapes;
}
}
