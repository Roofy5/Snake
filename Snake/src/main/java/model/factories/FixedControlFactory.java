package model.factories;


public class FixedControlFactory extends AbstractControlFactory{
    public SnakeControl getPlayer1Control(){
        return new SnakeControl("W", "S", "A", "D");
    }

    public SnakeControl getPlayer2Control(){
        return new SnakeControl("UP", "DOWN", "LEFT", "RIGHT");
    }

    public SnakeControl getPlayer3Control(){
        return new SnakeControl("I", "K", "J", "L");
    }

    public SnakeControl getPlayer4Control(){
        return new SnakeControl("T", "G", "F", "H");
    }
}
