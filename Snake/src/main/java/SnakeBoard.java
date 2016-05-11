import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class SnakeBoard extends JPanel{	
	private boolean running = false;
	private Direction direction;
	private Timer timer;
	private int start_x, start_y, width, height, size;
	//private int up_key, down_key, left_key, right_key;
	private List <DrawableObject> objects;
	public Level boardLevel;
	public SnakeBoard(int x, int y, int s, Level level){
		start_x = x;
		start_y = y;
		size = s;
		boardLevel = level;
		width = level.sizeX * size;
		height = level.sizeY * size;
		objects = level.GetMap();	
		timer = new Timer(250, new TimerTick());
		timer.setInitialDelay(0);
		timer.start();
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.drawRect(start_x, start_y, width, height);
		for(DrawableObject ob : objects){
			Position pos = ob.GetPosition();
			//System.out.println("X = " + pos.GetX() + " Y = " + pos.GetY());
			int upperLeftX = start_x + pos.GetX() * size;
			int upperLeftY = start_y + pos.GetY() * size;
			/*
			if(ob instanceof Snake)
				g2.setColor(Color.GREEN);
			else if(ob instanceof Tail)
				g2.setColor(Color.BLACK);
			else if(ob instanceof Apple)
				g2.setColor(Color.RED);
			*/
			g2.setColor(ob.GetColor()); //nowe ulepszone
			g2.fillRect(upperLeftX, upperLeftY, size, size);
		}
	}
	
	void attachControl(final Snake snake, String actionName){
		getInputMap().put(KeyStroke.getKeyStroke(snake.snakeConfig.GetControl().up, 0),actionName + "UP");
        getActionMap().put(actionName + "UP",new AbstractAction(){
        	public void actionPerformed(ActionEvent e) {
               snake.SetDirection(Direction.UP);
            }
        });
        //DOWN
        getInputMap().put(KeyStroke.getKeyStroke(snake.snakeConfig.GetControl().down, 0),actionName + "DOWN");
        getActionMap().put(actionName + "DOWN",new AbstractAction(){
        	public void actionPerformed(ActionEvent e) {
               snake.SetDirection(Direction.DOWN);
            }
        });
        //LEFT
        getInputMap().put(KeyStroke.getKeyStroke(snake.snakeConfig.GetControl().left, 0),actionName + "LEFT");
        getActionMap().put(actionName + "LEFT",new AbstractAction(){
        	public void actionPerformed(ActionEvent e) {
               snake.SetDirection(Direction.LEFT);
            }
        });
        //RIGHT
        getInputMap().put(KeyStroke.getKeyStroke(snake.snakeConfig.GetControl().right, 0),actionName + "RIGHT");
        getActionMap().put(actionName + "RIGHT",new AbstractAction(){
        	public void actionPerformed(ActionEvent e) {
               snake.SetDirection(Direction.RIGHT);
            }
        });
	}
	
	private class TimerTick implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			boardLevel.Move();
			repaint();
		}
		
	}
}