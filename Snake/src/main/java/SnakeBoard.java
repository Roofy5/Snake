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
				switch(code){ 				
					case KeyEvent.VK_UP:
						direction = Direction.UP;
						if(!running)
							timer.start();
						System.out.println("UP!");
						break;
					case KeyEvent.VK_DOWN:
						direction = Direction.DOWN;
						if(!running)
							timer.start();
						break;
					case KeyEvent.VK_RIGHT:
						direction = Direction.RIGHT;
						if(!running)
							timer.start();
						break;
					case KeyEvent.VK_LEFT:
						direction = Direction.LEFT;
						if(!running)
							timer.start();
						break;
				}
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
					if(ob instanceof Snake)
						g2.setColor(Color.GREEN);
					else if(ob instanceof Tail)
						g2.setColor(Color.BLACK);
					else if(ob instanceof Apple)
						g2.setColor(Color.RED);
					g2.fillRect(upperLeftX, upperLeftY, size, size);
				}
			}
	}
	
	private class TimerTick implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			switch(direction){
			case UP:
			case DOWN:
			case LEFT:
			case RIGHT:
				boardLevel.move(direction);
				break;
			}
			repaint();
		}
		
	}
}