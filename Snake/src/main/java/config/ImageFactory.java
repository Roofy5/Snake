package config;

import helper.GraphicType;
import javafx.scene.image.Image;

/**
 * Created by Rafal on 25.06.2016.
 */
public class ImageFactory {
    private static final Image[] deadImage = {new Image("v1/dead.png"), new Image("v2/dead.png"), new Image("v3/dead.png"), new Image("v4/dead.png")};
    private static final Image[] headImage = {new Image("v1/head.png"), new Image("v2/head.png"), new Image("v3/head.png"), new Image("v4/head.png")};
    private static final Image[] blockImage = {new Image("v1/block.png"), new Image("v2/block.png"), new Image("v3/block.png"), new Image("v4/block.png")};
    private static final Image[] turnImage = {new Image("v1/turn.png"), new Image("v2/turn.png"), new Image("v3/turn.png"), new Image("v4/turn.png")};
    private static final Image[] tailImage = {new Image("v1/tail.png"), new Image("v2/tail.png"), new Image("v3/tail.png"), new Image("v4/tail.png")};
    private static final Image[] appleImage = {new Image("apple.png")};
    private static final Image[] pearImage = {new Image("pear.png")};
    private static final Image[] invisImage = {new Image("invis.png")};

    public static Image getImage(GraphicType graphicType, int graphicVersion){
        if(graphicVersion < 1)
            graphicVersion = 1;
        switch(graphicType){
            case APPLE:
                if(appleImage.length < graphicVersion)
                    graphicVersion = 1;
                return appleImage[graphicVersion - 1];
            case INVIS:
                if(invisImage.length < graphicVersion)
                    graphicVersion = 1;
                return invisImage[graphicVersion - 1];
            case BLOCK:
                if(blockImage.length < graphicVersion)
                    graphicVersion = 1;
                return blockImage[graphicVersion - 1];
            case HEAD:
                if(headImage.length < graphicVersion)
                    graphicVersion = 1;
                return headImage[graphicVersion - 1];
            case DEAD:
                if(deadImage.length < graphicVersion)
                    graphicVersion = 1;
                return deadImage[graphicVersion - 1];
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
