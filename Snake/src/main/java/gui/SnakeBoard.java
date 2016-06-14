package gui;

import config.AppleConfiguration;
import config.SnakeConfiguration;
import config.TailConfiguration;
import drawable.Apple;
import drawable.DrawableObject;
import drawable.Snake;
import helper.Direction;
import helper.Position;
import model.Level;
import config.SettingsFactory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class SnakeBoard extends JPanel{
	private static final int START_DELAY = 1000;
	private Timer timer;
	private int start_x, start_y, width, height, size;
	private int snakeNum;
	//private int up_key, down_key, left_key, right_key;
	private List <DrawableObject> objects;
	public Level boardLevel;
	public SnakeBoard(int x, int y, int size, Level level){
		start_x = x;
		start_y = y;
		this.size = size;
		boardLevel = level;
		width = level.getCountX() * size;
		height = level.getCountY() * size;
		objects = level.GetMap();
		snakeNum = 1;
		initBoard();
		timer = new Timer(10, new TimerTick());
		timer.start();
	}
	
	private void initBoard(){
		setFocusable(true);
        
        SnakeConfiguration config = SettingsFactory.GetSnakeConfiguration(new Position(3, 4), Color.GREEN, 'W', 'S', 'A', 'D');
        TailConfiguration tailConfig = SettingsFactory.GetTailConfiguration(Direction.DOWN, Color.BLUE);
        
        Snake snake1 = new Snake(config, tailConfig, boardLevel.GetMap(), size);
		boardLevel.addSnake(snake1, START_DELAY);
		snake1.addStartingTails(5);

        attachControl(snake1);
        
      //Dodanie drugiego weza
        SnakeConfiguration config2 = SettingsFactory.GetSnakeConfiguration(new Position(7, 4), Color.PINK, 'I', 'K', 'J', 'L');
        TailConfiguration tailConfig2 = SettingsFactory.GetTailConfiguration(Direction.RIGHT, Color.ORANGE);
        
        Snake snake2 = new Snake(config2, tailConfig2, boardLevel.GetMap(), size);
		boardLevel.addSnake(snake2, 100);
		snake2.addStartingTails(7);
        
        attachControl(snake2);

      //Dodanie jablek
        AppleConfiguration appleConfig = SettingsFactory.GetAppleConfiguration(Color.YELLOW);
        AppleConfiguration appleConfig2 = SettingsFactory.GetAppleConfiguration(Color.GRAY);
        AppleConfiguration redAppleConfig = SettingsFactory.GetAppleConfiguration(Color.RED);
        
        boardLevel.addToObjectList(new Apple(new Position(5,2), appleConfig));
        boardLevel.addToObjectList(new Apple(new Position(20,20), appleConfig2));
        boardLevel.addToObjectList(new Apple(new Position(15,15), redAppleConfig));

        repaint();
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.drawRect(start_x, start_y, width, height);
		int i = 0;
		for(DrawableObject ob : objects){
			Position pos = ob.getPosition();
			int upperLeftX = start_x + pos.getX() * size;
			int upperLeftY = start_y + pos.getY() * size;
			g2.setColor(ob.getColor()); //nowe ulepszone
			g2.fillRect(upperLeftX, upperLeftY, size, size);
		}
	}
	
	private void attachControl(final Snake snake){
		getInputMap().put(KeyStroke.getKeyStroke(snake.getSnakeControl().up, 0),"drawable.Snake" + snakeNum + "UP");
        getActionMap().put("drawable.Snake" + snakeNum + "UP",new AbstractAction(){
        	public void actionPerformed(ActionEvent e) {
               snake.setDirection(Direction.UP);
            }
        });
        //DOWN
        getInputMap().put(KeyStroke.getKeyStroke(snake.getSnakeControl().down, 0),"drawable.Snake" + snakeNum + "DOWN");
        getActionMap().put("drawable.Snake" + snakeNum + "DOWN",new AbstractAction(){
        	public void actionPerformed(ActionEvent e) {
               snake.setDirection(Direction.DOWN);
            }
        });
        //LEFT
        getInputMap().put(KeyStroke.getKeyStroke(snake.getSnakeControl().left, 0),"drawable.Snake" + snakeNum + "LEFT");
        getActionMap().put("drawable.Snake" + snakeNum + "LEFT",new AbstractAction(){
        	public void actionPerformed(ActionEvent e) {
               snake.setDirection(Direction.LEFT);
            }
        });
        //RIGHT
        getInputMap().put(KeyStroke.getKeyStroke(snake.getSnakeControl().right, 0),"drawable.Snake" + snakeNum + "RIGHT");
        getActionMap().put("drawable.Snake" + snakeNum + "RIGHT",new AbstractAction(){
        	public void actionPerformed(ActionEvent e) {
               snake.setDirection(Direction.RIGHT);
            }
        });
		snakeNum++;
	}
	
	private class TimerTick implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			//boardLevel.checkBoardState();
			repaint();
		}
		
	}
}