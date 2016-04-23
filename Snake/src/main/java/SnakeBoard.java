import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeBoard extends JPanel{	
	private boolean running = false;
	private Direction direction;
	private Timer timer;
	private int start_x, start_y, width, height, size;
	//private int up_key, down_key, left_key, right_key;
	private DrawableObject[][] objects;
	public Level boardLevel;
	public SnakeBoard(int x, int y, int sizeX, int sizeY, int s){
		start_x = x;
		start_y = y;
		size = s;
		width = sizeX * size;
		height = sizeY * size;
		objects = new DrawableObject[sizeY][sizeX];
		boardLevel = new Level(sizeX, sizeY, objects);
		timer = new Timer(500, new TimerTick());
		timer.setInitialDelay(0);
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				
				for(int i = 0; i < Snake.noSnake; i++)
				{
					Snake snake = boardLevel.GetListOfSnakes().get(i);
					SettingsControl control = snake.config.GetControl();
		
					if(code == control.up)
					{
						System.out.print("UP");
						snake.SetDirection(Direction.UP);
						break;
					}
					if(code == control.down)
					{
						System.out.print("DWON");
						snake.SetDirection(Direction.DOWN);
						break;
					}
					if(code == control.left)
					{
						System.out.print("LEFT");
						snake.SetDirection(Direction.LEFT);
						break;
					}
					if(code == control.right)
					{
						System.out.print("RIGHT");
						snake.SetDirection(Direction.RIGHT);
						break;
					}
				}
				if(!running)
					timer.start();
				}
			});
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.drawRect(start_x, start_y, width, height);
		for(DrawableObject[] row : objects)
			for(DrawableObject ob : row){
				if(ob != null){
					Position pos = ob.GetPosition();
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
	}
	
	private class TimerTick implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			boardLevel.move();
			repaint();
		}
		
	}
}