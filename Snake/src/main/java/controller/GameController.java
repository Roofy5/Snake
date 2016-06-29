package controller;

import config.FixedControlFactory;
import config.SnakeControl;
import drawable.Snake;
import helper.Direction;
import helper.SnakeState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import javafx.stage.Stage;
import javafx.util.Duration;
import model.Level;
import view.GameView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rafal on 22.06.2016.
 */
public class GameController implements Initializable {

    private Level gameLevel;
    private GameView gameView;
    private Timeline[] timelines;
    private int init_delay;
    private int delay_diff;
    @FXML private Canvas gameCanvas;

    @FXML
    public void switchToMenuMode(ActionEvent actionEvent) throws Exception{
        Stage mainStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("view.fxml"));
        Scene gameScene = new Scene(parent, 600, 400);
        mainStage.setScene(gameScene);
        mainStage.show();
    }

    private int calculateDelay(int size){
        return init_delay - size * delay_diff;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int width_count = 40;
        int height_count = 20;
        int block_size = 15;
        int player_count = 4;
        int start_size = 1;
        int start_delay = 5000;
        init_delay = 275;
        delay_diff = 5;
        Snake.cleanCount();
        gameLevel = new Level(width_count, height_count);
        gameLevel.addPlayers(player_count, start_size, new FixedControlFactory());
        gameLevel.addFruits(3);
        gameCanvas.setWidth(width_count * block_size);
        gameCanvas.setHeight(height_count * block_size);
        gameView = new GameView(gameLevel.getMap(), gameLevel.getSnakes(), gameCanvas, block_size, width_count, height_count);
        initDraw(player_count);
        initTimelines(player_count, start_delay);
    }

    private void initTimelines(int player_count, int start_delay){
        timelines = new Timeline[player_count];
        for(int i = 0; i < player_count; i++) {
            Snake tempSnake = gameLevel.getSnakeByID(i+1);
            timelines[i] = new Timeline(new KeyFrame(Duration.ZERO, new TimeHandler(i + 1)),
                    new KeyFrame(Duration.millis(calculateDelay(tempSnake.getSize()))));
            timelines[i].setCycleCount(Animation.INDEFINITE);
        }
        Timeline startTimeline = new Timeline(new KeyFrame(Duration.millis(start_delay), ev -> {
            for(int i = 0; i < player_count; i++) {
                Snake tempSnake = gameLevel.getSnakeByID(i+1);
                if(!tempSnake.isRunning())
                    tempSnake.setDefaultDirection();
                timelines[i].play();
            }
        }));
        startTimeline.play();
    }

    public void attachKeys(Scene scene){
        scene.setOnKeyPressed(keyEvent -> {
            for (Snake snake : gameLevel.getAliveSnakes()) {
                Direction tempDir = Direction.NONE;
                SnakeControl snakeControl = snake.getSnakeControl();
                if (snakeControl.up.equals(keyEvent.getCode().toString()))
                    tempDir = Direction.UP;
                else if (snakeControl.down.equals(keyEvent.getCode().toString()))
                    tempDir = Direction.DOWN;
                else if (snakeControl.left.equals(keyEvent.getCode().toString()))
                    tempDir = Direction.LEFT;
                else if (snakeControl.right.equals(keyEvent.getCode().toString()))
                    tempDir = Direction.RIGHT;
                if(tempDir != Direction.NONE){
                    if(snake.setCurrentDirection(tempDir));
                        timelines[snake.getSnakeID()-1].play();
                }
            }
        });
    }

    private void initDraw(int player_count){
        for(int i = 1; i < player_count + 1; i++)
            gameView.drawSnakeOnCanvas(i);
    }

    private class TimeHandler implements EventHandler<ActionEvent>{

        private int version;
        public TimeHandler(int version){this.version = version;}
        @Override
        public void handle(ActionEvent event) {
            int prevSize;
            Snake tempSnake = gameLevel.getSnakeByID(version);
            if(tempSnake != null && !tempSnake.isDead()) {
                prevSize = tempSnake.getSize();
                tempSnake.move();
                if(tempSnake.getSize() > prevSize) {
                    if(tempSnake.getSnakeState() == SnakeState.INVISIBLE) {
                        Timeline invisibilityTimeline = new Timeline(new KeyFrame(Duration.millis(1000), ev -> {
                            if (tempSnake.getSnakeState() == SnakeState.INVISIBLE)
                                tempSnake.setSnakeState(SnakeState.NORMAL);
                        }));
                        invisibilityTimeline.play();
                    }
                    timelines[version-1].stop();
                    timelines[version-1].getKeyFrames().setAll(new
                            KeyFrame(Duration.millis(calculateDelay(tempSnake.getSize())), new TimeHandler(version)));
                    timelines[version-1].play();

                }
            }
            else {
                timelines[version - 1].stop();
            }
            gameView.drawSnakeOnCanvas(version);
        }
    }

}
