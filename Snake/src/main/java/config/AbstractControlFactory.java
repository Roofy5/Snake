package config;

/**
 * Created by Rafal on 25.06.2016.
 */
public abstract class AbstractControlFactory {
    public abstract SnakeControl getPlayer1Control();
    public abstract SnakeControl getPlayer2Control();
    public abstract SnakeControl getPlayer3Control();
    public abstract SnakeControl getPlayer4Control();
}
