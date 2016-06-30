package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ApplicationController extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("My pretty form!!");
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
        Scene scene = new Scene(parent, 850, 600);
        GameController gameController = loader.getController();
        gameController.attachKeys(scene);
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
