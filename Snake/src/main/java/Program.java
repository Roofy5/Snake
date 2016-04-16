import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame window = new JFrame();
                window.setSize(400, 200);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
                window.setLayout(new BorderLayout());
                SnakeBoard board = new SnakeBoard(50, 20, 40, 20, 5);
                window.add(board);
                window.add(new JButton(), BorderLayout.SOUTH);
                System.out.println(board.isFocusable());
                board.setFocusable(true);
                board.boardLevel.AddToObjectList(new Snake(3,4));
                board.boardLevel.AddToObjectList(new Tail(0,0));
                //board.boardLevel.AddToObjectList(new Tail(3,4));
                board.boardLevel.AddToObjectList(new Tail(1,1));
                board.boardLevel.AddToObjectList(new Tail(2,2));
                board.boardLevel.AddToObjectList(new Apple(5,2));
                board.boardLevel.AddToObjectList(new Tail(2,6));
                board.repaint();
            }
        });
	}

}
