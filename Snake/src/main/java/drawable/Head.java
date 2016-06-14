package drawable;

import config.SettingsSnake;
import config.SnakeConfiguration;

import java.awt.*;

/**
 * Created by Rafal on 14.06.2016.
 */
public class Head extends DrawableObject{

    private SettingsSnake snakeSettings;

    public Head(SettingsSnake settings){
        pos = settings.GetStartPosition();
        this.snakeSettings = settings;
    }

    public Color getColor()
    {
        return snakeSettings.GetColor();
    }
}
