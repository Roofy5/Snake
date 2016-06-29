package observator;

import drawable.DrawableObject;

/**
 * Created by Rafal on 28.06.2016.
 */
public interface Observer {
    void update();
    void update(DrawableObject ob);
}
