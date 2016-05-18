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
		initBoard();
		timer.start();  	
	}
	
	private void initBoard(){
		setFocusable(true);
        
        SnakeConfiguration config = SettingsFactory.GetSnakeConfiguration(new Position(3, 4), Color.GREEN, 'W', 'S', 'A', 'D');
        TailConfiguration tailConfig = SettingsFactory.GetTailConfiguration(Direction.DOWN, Color.BLUE);
        
        Snake snake1 = new Snake(config, tailConfig, boardLevel.GetMap());
        snake1.AddTail(5);

        boardLevel.AddToObjectList(snake1);   
        
        attachControl(snake1, "Snake1");
        
      //Dodanie drugiego weza
        SnakeConfiguration config2 = SettingsFactory.GetSnakeConfiguration(new Position(7, 4), Color.PINK, 'I', 'K', 'J', 'L');
        TailConfiguration tailConfig2 = SettingsFactory.GetTailConfiguration(Direction.RIGHT, Color.ORANGE);
        
        Snake snake2 = new Snake(config2, tailConfig2, boardLevel.GetMap());
        snake2.AddTail(7);

        boardLevel.AddToObjectList(snake2);   
        
        attachControl(snake2, "Snake2");

      //Dodanie jablek
        AppleConfiguration appleConfig = SettingsFactory.GetAppleConfigration(Color.YELLOW);
        AppleConfiguration appleConfig2 = SettingsFactory.GetAppleConfigration(Color.GRAY);
        AppleConfiguration redAppleConfig = SettingsFactory.GetAppleConfigration(Color.RED);
        
        boardLevel.AddToObjectList(new Apple(new Position(5,2), appleConfig));
        boardLevel.AddToObjectList(new Apple(new Position(20,20), appleConfig2));
        boardLevel.AddToObjectList(new Apple(new Position(15,15), redAppleConfig));
        
        repaint();
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.drawRect(start_x, start_y, width, height);
		for(DrawableObject ob : objects){
			Position pos = ob.GetPosition();
			int upperLeftX = start_x + pos.GetX() * size;
			int upperLeftY = start_y + pos.GetY() * size;
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