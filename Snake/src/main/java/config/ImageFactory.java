package config;

import helper.GraphicType;
import javafx.scene.image.Image;

/**
 * Created by Rafal on 25.06.2016.
 */
public class ImageFactory {
    private static final Image[] headImage = {new Image("v1/head.jpg"), new Image("v2/head.jpg"), new Image("v3/head.jpg"), new Image("v4/head.jpg")};
    private static final Image[] blockImage = {new Image("v1/block.jpg"), new Image("v2/block.jpg"), new Image("v3/block.jpg"), new Image("v4/block.jpg")};
    private static final Image[] turnImage = {new Image("v1/turn.jpg"), new Image("v2/turn.jpg"), new Image("v3/turn.jpg"), new Image("v4/turn.jpg")};
    private static final Image[] tailImage = {new Image("v1/tail.jpg"), new Image("v2/tail.jpg"), new Image("v3/tail.jpg"), new Image("v4/tail.jpg")};
    private static final Image[] appleImage = {new Image("apple.jpg")};
    private static final Image[] pearImage = {new Image("pear.jpg")};

    public static Image getImage(GraphicType graphicType, int graphicVersion){
        if(graphicVersion < 1)
            graphicVersion = 1;
        switch(graphicType){
            case APPLE:
                if(appleImage.length < graphicVersion)
                    graphicVersion = 1;
                return appleImage[graphicVersion - 1];
            case BLOCK:
                if(blockImage.length < graphicVersion)
                    graphicVersion = 1;
                return blockImage[graphicVersion - 1];
            case HEAD:
                if(headImage.length < graphicVersion)
                    graphicVersion = 1;
                return headImage[graphicVersion - 1];
            case PEAR:
                if(pearImage.length < graphicVersion)
                    graphicVersion = 1;
                return pearImage[graphicVersion - 1];
            case TAIL:
                if(tailImage.length < graphicVersion)
                    graphicVersion = 1;
                return tailImage[graphicVersion - 1];
            case TURN:
                if(turnImage.length < graphicVersion)
                    graphicVersion = 1;
                return turnImage[graphicVersion - 1];
            default:
                return null;
        }
    }
}
