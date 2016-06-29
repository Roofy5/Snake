package model.observator;

import model.drawable.DrawableObject;

public interface Observer {
    void update();
    void update(DrawableObject ob);
}
