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
                SettingsSnake settingsSnake_1 = new SettingsSnake(new Position(3, 4), Color.GREEN);
                SettingsControl controlSnake_1 = new SettingsControl('W', 'S', 'A', 'D');
                TailConfiguration tailConfig = new TailConfiguration(Direction.DOWN, Color.BLUE);
                AppleConfiguration appleConfig = new AppleConfiguration(Color.YELLOW);
                AppleConfiguration redAppleConfig = new AppleConfiguration(Color.RED);
                Snake snake1 = new Snake(new SnakeConfiguration
                		(settingsSnake_1, controlSnake_1),tailConfig, myLevel.GetMap());
                snake1.AddTail(5);
                board.boardLevel.AddToObjectList(snake1);   
              //Dodanie drugiego weza
                SettingsSnake settingsSnake_2 = new SettingsSnake(new Position(7, 4), Color.PINK);
                SettingsControl controlSnake_2 = new SettingsControl('I', 'K', 'J', 'L');
                TailConfiguration tailConfig2 = new TailConfiguration(Direction.RIGHT, Color.ORANGE);
                Snake snake2 = new Snake(new SnakeConfiguration
                		(settingsSnake_2, controlSnake_2),tailConfig2, myLevel.GetMap());
                snake2.AddTail(7);
                board.boardLevel.AddToObjectList(snake2);   
              //Dodanie jablek
                board.boardLevel.AddToObjectList(new Apple(new Position(5,2), appleConfig));
                board.boardLevel.AddToObjectList(new Apple(new Position(20,20), new AppleConfiguration(Color.GRAY)));
                board.boardLevel.AddToObjectList(new Apple(new Position(15,15), redAppleConfig));
                
                board.repaint();
            }
        });
	}

}
