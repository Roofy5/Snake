import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame window = new JFrame();
                window.setSize(800, 400);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
                window.setLayout(new BorderLayout());
                Level myLevel = new Level(60, 30); //WYMIARY POLA 60x30
                SnakeBoard board = new SnakeBoard(50, 20, 10, myLevel);//gorny lewy rog planszy 50px,20px; rozmiar pola 10x10px
                window.add(board);
                //window.add(new JButton(), BorderLayout.SOUTH);
                System.out.println(board.isFocusable());
                board.setFocusable(true);
                
                //Dodanie pierwszego weza
                /*SettingsSnake settingsSnake_1 = new SettingsSnake(new Position(3, 4), Color.GREEN);
                SettingsControl controlSnake_1 = new SettingsControl('W', 'S', 'A', 'D');
                TailConfiguration tailConfig = new TailConfiguration(Direction.DOWN, Color.BLUE);
                AppleConfiguration appleConfig = new AppleConfiguration(Color.YELLOW);
                AppleConfiguration redAppleConfig = new AppleConfiguration(Color.RED);
                Snake snake1 = new Snake(new SnakeConfiguration
                		(settingsSnake_1, controlSnake_1),tailConfig, myLevel.GetMap());
                */
                
                SnakeConfiguration config = SettingsFactory.GetSnakeConfiguration(new Position(3, 4), Color.GREEN, 'W', 'S', 'A', 'D');
                TailConfiguration tailConfig = SettingsFactory.GetTailConfiguration(Direction.DOWN, Color.BLUE);
                
                Snake snake1 = new Snake(config, tailConfig, myLevel.GetMap());
                snake1.AddTail(5);
                board.boardLevel.AddToObjectList(snake1);
                
              //Dodanie drugiego weza
                SnakeConfiguration config2 = SettingsFactory.GetSnakeConfiguration(new Position(7, 4), Color.PINK, 'I', 'K', 'J', 'L');
                TailConfiguration tailConfig2 = SettingsFactory.GetTailConfiguration(Direction.RIGHT, Color.ORANGE);
                
                Snake snake2 = new Snake(config2, tailConfig2, myLevel.GetMap());
                snake2.AddTail(7);
                board.boardLevel.AddToObjectList(snake2); 
                
              //Dodanie jablek
                AppleConfiguration appleConfig = SettingsFactory.GetAppleConfigration(Color.YELLOW);
                AppleConfiguration appleConfig2 = SettingsFactory.GetAppleConfigration(Color.GRAY);
                AppleConfiguration redAppleConfig = SettingsFactory.GetAppleConfigration(Color.RED);
                
                board.boardLevel.AddToObjectList(new Apple(new Position(5,2), appleConfig));
                board.boardLevel.AddToObjectList(new Apple(new Position(20,20), appleConfig2));
                board.boardLevel.AddToObjectList(new Apple(new Position(15,15), redAppleConfig));
                
                board.repaint();
            }
        });
	}

}
