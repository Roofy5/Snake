package model.factories;

import model.logic.SnakeControl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONControlFactory extends AbstractControlFactory{
    private JSONObject jsonObject;
    public JSONControlFactory(String path) throws FileNotFoundException{
        String jsonData = "";
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                jsonData += line + "\n";
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        jsonObject = new JSONObject(jsonData);
    }
    @Override
    public SnakeControl getPlayer1Control() {
        JSONArray player1Config = jsonObject.getJSONArray("player1");
        return new SnakeControl(player1Config.getString(0), player1Config.getString(1),
                player1Config.getString(2), player1Config.getString(3));
    }

    @Override
    public SnakeControl getPlayer2Control() {
        JSONArray player2Config = jsonObject.getJSONArray("player2");
        return new SnakeControl(player2Config.getString(0), player2Config.getString(1),
                player2Config.getString(2), player2Config.getString(3));
    }

    @Override
    public SnakeControl getPlayer3Control() {
        JSONArray player3Config = jsonObject.getJSONArray("player3");
        return new SnakeControl(player3Config.getString(0), player3Config.getString(1),
                player3Config.getString(2), player3Config.getString(3));
    }

    @Override
    public SnakeControl getPlayer4Control() {
        JSONArray player4Config = jsonObject.getJSONArray("player4");
        return new SnakeControl(player4Config.getString(0), player4Config.getString(1),
                player4Config.getString(2), player4Config.getString(3));
    }
}
