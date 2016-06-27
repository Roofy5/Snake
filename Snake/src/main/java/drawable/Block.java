package drawable;

import helper.Direction;
import helper.Position;


public class Block extends DrawableObject {

	private Direction currDirection;
	private Direction prevDirection;

	public Block(Position position) {
		pos = position;
	}

	public Direction getCurrDirection(){
		return currDirection;
	}

	public void setCurrDirection(Direction dir){
		currDirection = dir;
	}

	public Direction getPrevDirection(){
		return prevDirection;
	}

	public void setPrevDirection(Direction dir){
		prevDirection = dir;
	}
	
}
