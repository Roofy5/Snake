package view;

import config.ImageFactory;
import drawable.Block;
import drawable.DrawableObject;
import drawable.Head;
import drawable.Snake;
import helper.Direction;
import helper.GraphicType;
import helper.Position;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Created by Rafal on 25.06.2016.
 */
public class GameView {
    private List<DrawableObject> levelMap;
    private List<Snake> snakeList;
    private Canvas gameCanvas;
    private int block_size;

    public GameView(List<DrawableObject> levelMap, List<Snake> snakeList, Canvas gameCanvas, int block_size){
        this.levelMap = levelMap;
        this.gameCanvas = gameCanvas;
        this.block_size = block_size;
        this.snakeList = snakeList;
    }

    public void drawOnCanvas(){
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        Position tempPos;
        int graphVersion = 1;
        for(DrawableObject ob : levelMap){
            for(Snake snake : snakeList)
                if(snake.hasPart(ob))
                    graphVersion = snake.getSnakeID();

            tempPos = ob.getPosition();
            GraphicType graphType = GraphicType.getGraphicType(ob);
            Image baseImage = ImageFactory.getImage(graphType, graphVersion);
            gc.drawImage(rotatedImage(ob, baseImage), tempPos.getX() * block_size, tempPos.getY() * block_size,
                    block_size, block_size);
        }
    }

    private Image rotatedImage(DrawableObject ob, Image img){
        ImageView iv = new ImageView(img);
        Direction prevDirection, currDirection;
        int rotation = 0;
        if(ob instanceof Block) {
            Block b = (Block)ob;
            prevDirection = b.getPrevDirection();
            currDirection = b.getCurrDirection();
        }
        else if(ob instanceof Head){
            Head h = (Head)ob;
            currDirection = h.getDirection();
            prevDirection = Direction.NONE;
        }
        else{
            return img;
        }

        if(prevDirection == Direction.NONE || currDirection == prevDirection){
            if(currDirection == Direction.NONE) {
                for(Snake snake : snakeList)
                    if(snake.hasPart(ob))
                        currDirection = snake.getStartDirection().getOppositeDirection();
            }
            switch(currDirection){
                case UP:
                    rotation = -90;
                    break;
                case DOWN:
                    rotation = 90;
                    break;
                case LEFT:
                    rotation = 180;
                    break;
                case RIGHT:
                    rotation = 0;
                    break;
            }
        }

        else{
            if(prevDirection == Direction.UP && currDirection == Direction.RIGHT ||
                    prevDirection == Direction.LEFT && currDirection == Direction.DOWN){
                rotation = 0;
            }
            else if(prevDirection == Direction.UP && currDirection == Direction.LEFT ||
                    prevDirection == Direction.RIGHT && currDirection == Direction.DOWN){
                rotation = 90;
            }
            else if(prevDirection == Direction.DOWN && currDirection == Direction.LEFT ||
                    prevDirection == Direction.RIGHT && currDirection == Direction.UP){
                rotation = 180;
            }
            else if(prevDirection == Direction.DOWN && currDirection == Direction.RIGHT ||
                    prevDirection == Direction.LEFT && currDirection == Direction.UP){
                rotation = -90;
            }
        }
        iv.setRotate(rotation);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return iv.snapshot(params, null);
    }
}
