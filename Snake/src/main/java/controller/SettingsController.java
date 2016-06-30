package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.factories.AbstractControlFactory;
import model.factories.FixedControlFactory;
import model.factories.JSONControlFactory;
import model.factories.SnakeControl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable{
    @FXML private TextField p1UP;
    @FXML private TextField p1DOWN;
    @FXML private TextField p1LEFT;
    @FXML private TextField p1RIGHT;
    @FXML private TextField p2UP;
    @FXML private TextField p2DOWN;
    @FXML private TextField p2LEFT;
    @FXML private TextField p2RIGHT;
    @FXML private TextField p3UP;
    @FXML private TextField p3DOWN;
    @FXML private TextField p3LEFT;
    @FXML private TextField p3RIGHT;
    @FXML private TextField p4UP;
    @FXML private TextField p4DOWN;
    @FXML private TextField p4LEFT;
    @FXML private TextField p4RIGHT;

    @FXML
    public void switchToMenuMode(ActionEvent actionEvent) throws Exception{
        Stage mainStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("menuView.fxml"));
        Parent parent = loader.load();
        Scene gameScene = new Scene(parent, 600, 400);
        mainStage.setScene(gameScene);
        mainStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextField[] controls = new TextField[]{p1UP, p1DOWN, p1LEFT, p1RIGHT,
                p2UP, p2DOWN, p2LEFT, p2RIGHT,
                p3UP, p3DOWN, p3LEFT, p3RIGHT,
                p4UP, p4DOWN, p4LEFT, p4RIGHT};

        for(TextField field : controls){
            field.setEditable(false);
            field.setOnKeyPressed(keyEvent -> field.setText(keyEvent.getCode().toString()));
        }

        AbstractControlFactory factory;
        try{
            factory = new JSONControlFactory("src/main/resources/settings.json");
        }
        catch(FileNotFoundException ex){
            factory = new FixedControlFactory();
        }
        SnakeControl[] defaultControls = new SnakeControl[]{factory.getPlayer1Control(), factory.getPlayer2Control(),
                factory.getPlayer3Control(), factory.getPlayer4Control()};
        for(int i = 0; i < defaultControls.length; i++){
            controls[i*4].setPromptText(defaultControls[i].up);
            controls[i*4 + 1].setPromptText(defaultControls[i].down);
            controls[i*4 + 2].setPromptText(defaultControls[i].left);
            controls[i*4 + 3].setPromptText(defaultControls[i].right);
        }
    }

    public void saveSettings(ActionEvent actionEvent) {
        TextField[] controls = new TextField[]{p1UP, p1DOWN, p1LEFT, p1RIGHT,
                p2UP, p2DOWN, p2LEFT, p2RIGHT,
                p3UP, p3DOWN, p3LEFT, p3RIGHT,
                p4UP, p4DOWN, p4LEFT, p4RIGHT};
        JSONObject obj = new JSONObject();
        JSONArray[] playerControls = new JSONArray[]{new JSONArray(), new JSONArray(), new JSONArray(), new JSONArray()};
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++) {
                if(controls[4 * i + j].getText().equals(""))
                    playerControls[i].put(controls[4 * i + j].getPromptText());
                else
                    playerControls[i].put(controls[4 * i + j].getText());
            }
        }
        obj.put("player1",playerControls[0]);
        obj.put("player2",playerControls[1]);
        obj.put("player3",playerControls[2]);
        obj.put("player4",playerControls[3]);

        try {
            FileWriter file = new FileWriter("src/main/resources/settings.json");
            file.write(obj.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("SAVED");
    }
}
