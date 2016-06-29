package model.drawable;

import helper.Direction;
import helper.Position;

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
