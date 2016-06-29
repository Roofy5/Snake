package drawable;

import helper.Direction;
import helper.Position;

/**
 * Created by Rafal on 14.06.2016.
 */
public class Head extends DrawableObject{

    private Direction currentDirection;
    private boolean isDead;

    public Head(Position position){
        pos = position;
        currentDirection = Direction.NONE;
    }

    public Direction getDirection(){
        return currentDirection;
    }

    public void setDirection(Direction dir){
        currentDirection = dir;
    }

    public boolean isDead(){return isDead;}

    public void setDead(boolean dead){isDead = dead;}
}
