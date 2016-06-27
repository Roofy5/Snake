package helper;

public enum Direction {
		UP, DOWN, LEFT, RIGHT, NONE;
	
	private Direction opposite;

    static {
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;
    }

    public Direction getOppositeDirection() {
        return opposite;
    }
}
