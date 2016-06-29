package view;

import config.ImageFactory;
import drawable.Block;
import drawable.DrawableObject;
import drawable.Head;
import drawable.Snake;
import helper.Direction;
import helper.GraphicType;
import helper.Position;
import helper.SnakeState;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rafal on 25.06.2016.
 */
public class GameView{
    private List<DrawableObject> levelMap;
    private List<Snake> snakeList;
    private Canvas gameCanvas;
    private Map<Integer, List<Position>> previousImages;
    private int block_size, width_count, height_count;

    public GameView(List<DrawableObject> levelMap, List<Snake> snakeList, Canvas gameCanvas, int block_size,
                    int width_count, int height_count){
        this.levelMap = levelMap;
        this.gameCanvas = gameCanvas;
        this.block_size = block_size;
        this.snakeList = snakeList;
        this.width_count = width_count;
        this.height_count = height_count;
        this.previousImages = new HashMap<>();
    }

    public void drawSnakeOnCanvas(int snakeVersion){
        boolean isSnakePart, isInvisible;
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        Position tempPos;
        int graphVersion = 1;
        //!!
        if(!previousImages.containsKey(snakeVersion))
            previousImages.put(snakeVersion, new ArrayList<>());
        for(Position pos : previousImages.get(snakeVersion))
            gc.clearRect(pos.getX() * block_size, pos.getY() * block_size, block_size, block_size);
        previousImages.get(snakeVersion).clear();
        //!!
        for(DrawableObject ob : levelMap){
            isSnakePart = isInvisible = false;
            for(Snake snake : snakeList) {
                if (snake.hasPart(ob)) {
                    graphVersion = snake.getSnakeID();
                    isSnakePart = true;
                    if(snake.getSnakeState() == SnakeState.INVISIBLE)
                        isInvisible = true;
                    break;
                }
            }
            if(isSnakePart && (snakeVersion != graphVersion || isInvisible))
                continue;

            tempPos = ob.getPosition();
            GraphicType graphType = GraphicType.getGraphicType(ob);
            Image baseImage = ImageFactory.getImage(graphType, graphVersion);
            gc.drawImage(rotatedImage(ob, baseImage), tempPos.getX() * block_size, tempPos.getY() * block_size,
                    block_size, block_size);

            previousImages.get(snakeVersion).add(tempPos);

        }
        gc.strokeRect(0,0,width_count*block_size, height_count*block_size);
    }

    private Image rotatedImage(DrawableObject ob, Image img){
        ImageView iv = new ImageView(img);
        Direction prevDirection, currDirection;
        int rotation;
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
            rotation = getRotationByOneDirection(currDirection);
        }
        else{
            rotation = getRotationByTwoDirections(currDirection, prevDirection);
        }
        iv.setRotate(rotation);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return iv.snapshot(params, null);
    }

    private int getRotationByOneDirection(Direction dir){
        switch(dir){
            case UP:
                return -90;
            case DOWN:
                return 90;
            case LEFT:
                return 180;
        }
        return 0;
    }

    private int getRotationByTwoDirections(Direction currDir, Direction prevDir){
        if(prevDir == Direction.UP && currDir == Direction.LEFT ||
                prevDir == Direction.RIGHT && currDir == Direction.DOWN){
            return 90;
        }
        else if(prevDir == Direction.DOWN && currDir == Direction.LEFT ||
                prevDir == Direction.RIGHT && currDir == Direction.UP){
            return 180;
        }
        else if(prevDir == Direction.DOWN && currDir == Direction.RIGHT ||
                prevDir == Direction.LEFT && currDir == Direction.UP){
            return -90;
        }
        else
            return 0;
    }

}
