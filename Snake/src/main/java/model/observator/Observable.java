package model.observator;

import model.drawable.DrawableObject;

public interface Observable {
    void addObserver(Observer obs);
    void deleteObserver(Observer obs);
    void notifyObservers();
    void notifyObservers(DrawableObject ob);
}
