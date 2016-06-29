package controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Created by Rafal on 18.06.2016.
 */
public class ApplicationController extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("My pretty form!!");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view.fxml"));
        Parent parent = loader.load();
        Scene menuScene = new Scene(parent, 600, 400);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
}
