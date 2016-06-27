package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Rafal on 22.06.2016.
 */
public class MenuController{
    @FXML
    public void switchToGameMode(ActionEvent actionEvent) throws Exception{
        Stage mainStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("gameView.fxml"));
        Scene scene = new Scene(parent, 800, 500);
        mainStage.setScene(scene);
        mainStage.show();
    }
}
