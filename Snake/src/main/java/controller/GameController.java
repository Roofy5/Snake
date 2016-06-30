package controller;

import model.factories.AbstractControlFactory;
import model.factories.FixedControlFactory;
import model.factories.JSONControlFactory;
import model.factories.SnakeControl;
import model.logic.Snake;
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
import model.logic.Level;
import view.GameView;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private Level gameLevel;
    private GameView gameView;
    private Timeline[] timelines;
    private int initDelay;
    private int delayDiff;
    private int minDelay;
    @FXML private Canvas gameCanvas;

    @FXML
    public void switchToMenuMode(ActionEvent actionEvent) throws Exception{
        Stage mainStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("menuView.fxml"));
        Scene gameScene = new Scene(parent, 600, 400);
        mainStage.setScene(gameScene);
        mainStage.show();
    }

    private int calculateDelay(int currDelay, int size){
        return (int)(currDelay - (delayDiff/(size / 4.0)));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AbstractControlFactory factory;
        int start_delay = 5000;
        initDelay = 200;
        delayDiff = 10;
        minDelay = 20;
        int width_count;
        int height_count;
        int player_count = 4;
        int start_size = 1;
        int block_size = 15;

        try{
            factory = new JSONControlFactory("src/main/resources/settings.json");
        }
        catch(FileNotFoundException ex){
            factory = new FixedControlFactory();
        }
        Snake.cleanCount();
        width_count = (int)(gameCanvas.getWidth()/ block_size);
        height_count = (int)(gameCanvas.getHeight()/ block_size);
        gameCanvas.setHeight(height_count * block_size);
        gameLevel = new Level(width_count, height_count);
        gameLevel.addPlayers(player_count, start_size, factory);
        gameLevel.addFruits(4);

        gameView = new GameView(gameLevel.getMap(), gameLevel.getSnakes(), gameCanvas,
                block_size, width_count, height_count);
        initDraw(player_count);
        initTimelines(player_count, start_delay);
    }

    private void initTimelines(int player_count, int start_delay){
        timelines = new Timeline[player_count];
        for(int i = 0; i < player_count; i++) {
            timelines[i] = new Timeline(new KeyFrame(Duration.ZERO, new TimeHandler(i + 1, initDelay)),
                    new KeyFrame(Duration.millis(initDelay)));
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
                    if(snake.setCurrentDirection(tempDir))
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
        private int lastDelay;
        public TimeHandler(int version, int lastDelay){this.version = version; this.lastDelay = lastDelay;}
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
                            gameView.drawSnakeOnCanvas(version);
                        }));
                        invisibilityTimeline.play();
                    }
                    if(lastDelay > minDelay) {
                        timelines[version - 1].stop();
                        int currDelay = calculateDelay(lastDelay, tempSnake.getSize());
                        timelines[version - 1].getKeyFrames().setAll(new
                                KeyFrame(Duration.millis(currDelay),
                                new TimeHandler(version, currDelay)));
                        timelines[version - 1].play();
                        System.out.println(currDelay);
                    }
                    if(gameLevel.getEatenFruitsCount() > 50)
                        gameLevel.setMaxFruitCount(1);
                    else if(gameLevel.getEatenFruitsCount() > 30)
                        gameLevel.setMaxFruitCount(2);
                    else if(gameLevel.getEatenFruitsCount() > 10)
                        gameLevel.setMaxFruitCount(3);

                }
            }
            else {
                timelines[version - 1].stop();
            }
            gameView.drawSnakeOnCanvas(version);
        }
    }

}
