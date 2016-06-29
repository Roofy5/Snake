package model.factories;

import helper.Position;

import java.util.HashMap;

public class PositionFactory {
    private static final HashMap<String, Position> positionMap = new HashMap<>();

    public static Position getPosition(int x, int y){
        if(!positionMap.containsKey(coordToString(x, y))) {
            positionMap.put(coordToString(x,y), new Position(x, y));
        }
        return positionMap.get(coordToString(x, y));
    }

    private static String coordToString(int x, int y){
        return x + "," + y;
    }
}
