package helper;

import drawable.*;

/**
 * Created by Rafal on 15.06.2016.
 */
public enum GraphicType {
    APPLE, HEAD, DEAD, TAIL, PEAR, INVIS, BLOCK, TURN, NONE;

    public static GraphicType getGraphicType(DrawableObject ob){
        if(ob instanceof Apple)
            return APPLE;
        else if(ob instanceof Pear)
            return PEAR;
        else if(ob instanceof Invis)
            return INVIS;
        else if (ob instanceof Head) {
            if (((Head) ob).isDead())
                return DEAD;
            else
                return HEAD;
        }
        else if (ob instanceof Block) {
            Block block = (Block)ob;
            if(block.getPrevDirection() == Direction.NONE)
                return TAIL;
            else if(block.getCurrDirection() == block.getPrevDirection())
                return BLOCK;
            else
                return TURN;
        }
        else
            return NONE;
    }
}
