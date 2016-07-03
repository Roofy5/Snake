package controller;

import helper.GameSettings;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.factories.AbstractControlFactory;
import model.factories.FixedControlFactory;
import model.factories.JSONControlFactory;
import model.logic.SnakeControl;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import javafx.stage.Stage;
import javafx.util.Duration;
import model.logic.Level;
import view.GameView;

import java.io.FileNotFoundException;
import java.util.List;

public class GameController{

    private Level gameLevel;
    private GameView gameView;
    private Timeline[] timelines;
    private Timeline startTimeline;
    private GameSettings gameSettings;
    private AbstractControlFactory factory;
    @FXML private Text player1ControlText;
    @FXML private Text player2ControlText;
    @FXML private Text player3ControlText;
    @FXML private Text player4ControlText;
    @FXML private HBox player1Box;
    @FXML private HBox player2Box;
    @FXML private HBox player3Box;
    @FXML private HBox player4Box;
    @FXML private Label player1Score;
    @FXML private Label player2Score;
    @FXML private Label player3Score;
    @FXML private Label player4Score;
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
        return (int)(currDelay - (gameSettings.delayDiff/(size / 4.0)));
    }

    public void setupGame(Integer playerCount, Scene scene) {
        gameSettings = new GameSettings(playerCount);
        HBox[] playerBoxes = {player1Box, player2Box, player3Box, player4Box};
        Text[] playerControlText = {player1ControlText, player2ControlText,player3ControlText,player4ControlText};
        try{
            factory = new JSONControlFactory("settings.json");
        }
        catch(FileNotFoundException ex){
            factory = new FixedControlFactory();
        }
        initLevel();
        List<Snake> tempSnakes = gameLevel.getSnakes();
        for(int i = 0; i < 4; i++) {
            if(i < playerCount) {
                playerBoxes[i].setVisible(true);
                playerControlText[i].setText(tempSnakes.get(i).getSnakeControl().toString());
            }
            else {
                playerBoxes[i].setVisible(false);
                playerControlText[i].setText("");

            }
        }
        attachKeys(scene);
    }

    private void initLevel(){
        int widthCount = (int)(gameCanvas.getWidth()/ gameSettings.blockSize);
        int heightCount = (int)(gameCanvas.getHeight()/ gameSettings.blockSize);
        gameCanvas.setHeight(heightCount * gameSettings.blockSize);
        gameCanvas.setWidth(widthCount * gameSettings.blockSize);
        gameLevel = new Level(widthCount, heightCount);
        gameLevel.addPlayers(gameSettings.playerCount, gameSettings.startSize, factory);
        gameLevel.addFruits(4);
        gameView = new GameView(gameLevel.getMap(), gameLevel.getSnakes(), gameCanvas,
                gameSettings.blockSize, widthCount, heightCount);
        for(Snake snake : gameLevel.getSnakes())
            updateLabel(snake);
        initDraw(gameSettings.playerCount);
        initTimelines(gameSettings.playerCount, gameSettings.startDelay);
    }

    private void initTimelines(int player_count, int start_delay){
        timelines = new Timeline[player_count];
        for(int i = 0; i < player_count; i++) {
            timelines[i] = new Timeline(new KeyFrame(Duration.ZERO, new TimeHandler(i + 1, gameSettings.initDelay)),
                    new KeyFrame(Duration.millis(gameSettings.initDelay)));
            timelines[i].setCycleCount(Animation.INDEFINITE);
        }
        startTimeline = new Timeline(new KeyFrame(Duration.millis(start_delay), ev -> {
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
        gameView.clearCanvas();
        for(int i = 1; i < player_count + 1; i++)
            gameView.drawSnakeOnCanvas(i);
    }

    public void resetGame(ActionEvent actionEvent) {
        for(Timeline timeline : timelines)
            timeline.stop();
        startTimeline.stop();
        initLevel();
    }

    private void updateLabel(Snake snake){
        int snakeID = snake.getSnakeID();
        switch(snakeID){
            case 1:
                player1Score.setText(snake.getSnakeScore().toString());
                break;
            case 2:
                player2Score.setText(snake.getSnakeScore().toString());
                break;
            case 3:
                player3Score.setText(snake.getSnakeScore().toString());
                break;
            case 4:
                player4Score.setText(snake.getSnakeScore().toString());
                break;
        }
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
                    updateLabel(tempSnake);
                    if(tempSnake.getSnakeState() == SnakeState.INVISIBLE) {
                        Timeline invisibilityTimeline = new Timeline(new KeyFrame(Duration.millis(1000), ev -> {
                            if (tempSnake.getSnakeState() == SnakeState.INVISIBLE)
                                tempSnake.setSnakeState(SnakeState.NORMAL);
                            gameView.drawSnakeOnCanvas(version);
                        }));
                        invisibilityTimeline.play();
                    }
                    if(lastDelay > gameSettings.minDelay) {
                        timelines[version - 1].stop();
                        int currDelay = calculateDelay(lastDelay, tempSnake.getSize());
                        timelines[version - 1].getKeyFrames().setAll(new
                                KeyFrame(Duration.millis(currDelay),
                                new TimeHandler(version, currDelay)));
                        timelines[version - 1].play();
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
