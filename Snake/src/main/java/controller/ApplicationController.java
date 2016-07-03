package controller;

import helper.GraphicType;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;

import javafx.stage.Stage;
import view.ImageFactory;

public class ApplicationController extends Application{
    @FXML private ComboBox<Integer> playerCount;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setResizable(false);
        primaryStage.setTitle("SuperSnake");
        primaryStage.getIcons().add(ImageFactory.getImage(GraphicType.HEAD, 1));
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("menuView.fxml"));
        Parent parent = loader.load();
        Scene menuScene = new Scene(parent, 600, 400);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    @FXML
    public void switchToGameMode(ActionEvent actionEvent) throws Exception{
        Stage mainStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gameView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 850, 650);
        GameController gameController = loader.getController();
        gameController.setupGame(playerCount.getValue(), scene);
        mainStage.setScene(scene);
        mainStage.show();
    }

    @FXML
    public void switchToSettingsMode(ActionEvent actionEvent) throws Exception{
        Stage mainStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("settingsView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 550, 550);
        mainStage.setScene(scene);
        mainStage.show();
    }
}
