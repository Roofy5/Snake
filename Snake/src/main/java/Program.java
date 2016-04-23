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
                SnakeBoard board = new SnakeBoard(50, 20, 60, 30, 10);
                window.add(board);
                window.add(new JButton(), BorderLayout.SOUTH);
                System.out.println(board.isFocusable());
                board.setFocusable(true);
                
                //Dodanie pierwszego weza
                SettingsSnake settingsSnake_1 = new SettingsSnake(new Position(3, 4), Color.GREEN);
                SettingsControl controlSnake_1 = new SettingsControl('W', 'S', 'A', 'D');
                board.boardLevel.AddToObjectList(new Snake(new SnakeConfiguration(settingsSnake_1, controlSnake_1) ));
                
                board.boardLevel.AddToObjectList(new Tail(new TailConfiguration(new Position(0,0), Color.BLACK)));
                board.boardLevel.AddToObjectList(new Tail(new TailConfiguration(new Position(1,1), Color.BLACK)));
                board.boardLevel.AddToObjectList(new Tail(new TailConfiguration(new Position(2,2), Color.BLACK)));
                board.boardLevel.AddToObjectList(new Apple(new AppleConfiguration(new Position(5,2), Color.RED)));
                board.boardLevel.AddToObjectList(new Tail(new TailConfiguration(new Position(2,6), Color.BLACK)));
                board.repaint();
            }
        });
	}

}
