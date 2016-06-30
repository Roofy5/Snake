import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;
import model.logic.Snake;

import model.logic.Level;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michal on 2016-06-19.
 */
public class LevelTest {

    @Test
    public void Test1()
    {
        Level TestowyLevel = new Level(10, 10);
        Assert.assertEquals(TestowyLevel.getEatenFruitsCount(), 0);
    }

    @Test
    public void Test2()
    {
        Level TestowyLevel = new Level(10, 10);

        model.factories.AbstractControlFactory factory;
        try{
            factory = new model.factories.JSONControlFactory("src/main/resources/settings.json");
        }
        catch(FileNotFoundException ex){
            factory = new model.factories.FixedControlFactory();
        }

        TestowyLevel.addPlayers(2, 5, factory);
        Assert.assertNotEquals(TestowyLevel.getAliveSnakes(), 0);
    }

    @Test
    public void Test3()
    {
        Level TestowyLevel = new Level(10, 10);
        model.factories.AbstractControlFactory factory;
        try{
            factory = new model.factories.JSONControlFactory("src/main/resources/settings.json");
        }
        catch(FileNotFoundException ex){
            factory = new model.factories.FixedControlFactory();
        }
        TestowyLevel.addPlayers(2, 5, factory);

        Assert.assertNotEquals(TestowyLevel.getSnakeByID(1), 0);
    }

    @Test
    public void Test4() {
        Level TestowyLevel = new Level(10, 10);
        List<model.drawable.DrawableObject> mapa = new ArrayList<>();
        List<model.drawable.DrawableObject> mapa2;

        mapa2 = TestowyLevel.getMap();
        Assert.assertEquals(mapa, mapa2);
    }

    @Test
    public void Test5() {
        Level TestowyLevel = new Level(10, 10);
        List<model.drawable.DrawableObject> mapa = new ArrayList<>();
        List<model.drawable.DrawableObject> mapa2;

        TestowyLevel.addFruits(1);

        mapa2 = TestowyLevel.getMap();
        Assert.assertNotEquals(mapa, mapa2);
    }
}