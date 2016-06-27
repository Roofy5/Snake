package controller;

import config.FixedControlFactory;
import drawable.Snake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
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
    @FXML private Canvas gameCanvas;

    public void drawOnCanvas(){
        gameView.drawOnCanvas();
    }

    @FXML
    public void switchToMenuMode(ActionEvent actionEvent) throws Exception{
        Stage mainStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("view.fxml"));
        Scene menuScene = new Scene(parent, 600, 400);
        mainStage.setScene(menuScene);
        mainStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int width_count = 30;
        int height_count = 15;
        int block_size = 15;
        Snake.cleanCount();
        gameLevel = new Level(width_count, height_count);
        gameLevel.addPlayers(4, 6, 1000, new FixedControlFactory());
        gameCanvas.setWidth(width_count * block_size);
        gameCanvas.setHeight(height_count * block_size);
        gameView = new GameView(gameLevel.getMap(), gameLevel.getSnakes(), gameCanvas, block_size);
    }
}
