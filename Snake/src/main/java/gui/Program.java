package gui;

import model.Level;

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
            }
        });
	}
	
}

class SnakeFrame extends JFrame{
    private static final int START_X = 50;
    private static final int START_Y = 20;
    private static final int SIZE = 8;
    private static final int WIDTH_COUNT = 80;
    private static final int HEIGHT_COUNT = 30;

	SnakeFrame(){
		setSize(800, 400);
		setLayout(new BorderLayout());
		Level myLevel = new Level(WIDTH_COUNT, HEIGHT_COUNT); //WYMIARY POLA 60x30
        //SnakeBoard board = new SnakeBoard(START_X, START_Y, SIZE, myLevel);//gorny lewy rog planszy 50px,20px; rozmiar pola 10x10px
        //add(board);
	}
}
