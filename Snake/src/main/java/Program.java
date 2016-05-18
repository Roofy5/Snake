import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame window = new SnakeFrame();
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
                //Git/IntellJ test
            }
        });
	}
	
}

class SnakeFrame extends JFrame{
	SnakeFrame(){
		setSize(800, 400);
		setLayout(new BorderLayout());
		Level myLevel = new Level(60, 30); //WYMIARY POLA 60x30
        SnakeBoard board = new SnakeBoard(50, 20, 10, myLevel);//gorny lewy rog planszy 50px,20px; rozmiar pola 10x10px
        add(board);
	}
}
