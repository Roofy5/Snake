package config;

public class SnakeControl {
	public String up, down, left, right;

	public SnakeControl(String u, String d, String l, String r){
		up = u;
		down = d;
		left = l;
		right = r;
	}

	public void setControl(String u, String d, String l, String r){
		up = u;
		down = d;
		left = l;
		right = r;
	}
}
