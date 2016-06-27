package helper;

import drawable.Apple;
import drawable.Block;
import drawable.DrawableObject;
import drawable.Head;

/**
 * Created by Rafal on 15.06.2016.
 */
public enum GraphicType {
    APPLE, HEAD, TAIL, PEAR, BLOCK, TURN, NONE;

    public static GraphicType getGraphicType(DrawableObject ob){
        if (ob instanceof Apple)
            return APPLE;
        else if (ob instanceof Head)
            return HEAD;
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
