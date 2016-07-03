package model.factories;

import model.logic.SnakeControl;

public abstract class AbstractControlFactory {
    public abstract SnakeControl getPlayer1Control();
    public abstract SnakeControl getPlayer2Control();
    public abstract SnakeControl getPlayer3Control();
    public abstract SnakeControl getPlayer4Control();
}
