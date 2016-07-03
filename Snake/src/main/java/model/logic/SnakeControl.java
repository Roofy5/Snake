package model.logic;

public class SnakeControl {
	public String up, down, left, right;

	public SnakeControl(String u, String d, String l, String r){
		up = u;
		down = d;
		left = l;
		right = r;
	}

	public void inverseControl(){
		String tempup = up;
		String tempdown = down;
		String templeft = left;
		String tempright = right;

		up = tempdown;
		down = tempup;
		left = tempright;
		right = templeft;
	}

	@Override
	public String toString(){
		return "UP: " + up + "\n" + "DOWN: " + down + "\n" + "LEFT: " + left + "\n" + "RIGHT: " + right + "\n";
	}
}
