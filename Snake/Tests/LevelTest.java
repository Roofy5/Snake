import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;
import model.logic.Snake;

import model.logic.Level;

import java.io.FileNotFoundException;

/**
 * Created by Michal on 2016-06-19.
 */
public class LevelTest {

    @Test
    public void Test0()
    {
        Level TestowyLevel = new Level(10, 10);
        Assert.assertEquals(TestowyLevel.getEatenFruitsCount(), 0);
    }

    @Test
    public void Test1()
    {
        Level TestowyLevel = new Level(10, 10);
        TestowyLevel.addFruits(5);

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
}