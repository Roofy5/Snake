package observator;

import drawable.DrawableObject;

/**
 * Created by Rafal on 28.06.2016.
 */
public interface Observable {
    void addObserver(Observer obs);
    void deleteObserver(Observer obs);
    void notifyObservers();
    void notifyObservers(DrawableObject ob);
}
